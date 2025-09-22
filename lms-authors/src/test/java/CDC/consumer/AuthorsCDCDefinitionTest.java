package CDC.consumer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorRabbitmqController;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.publishers.AuthorEventsPublisher;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {AuthorRabbitmqController.class, AuthorService.class, AuthorEventsPublisher.class, AuthorRepository.class}
)
@PactConsumerTest
@PactTestFor(providerName = "author_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class AuthorsCDCDefinitionTest {

    @MockBean
    AuthorService authorService;

    @MockBean
    AuthorEventsPublisher authorEventsPublisher;

    @MockBean
    AuthorRepository authorRepository;

    @Autowired
    AuthorRabbitmqController listener;

    @Pact(consumer = "author_created-consumer")
    V4Pact createAuthorCreatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("name", "author");
        body.stringType("bio", "author bio");
        body.stringMatcher("version", "[0-9]+", "1");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a author created event").withMetadata(metadata).withContent(body).toPact();
    }

    @Pact(consumer = "author_updated-consumer")
    V4Pact createAuthorUpdatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name", "Author updated")
                .stringType("bio", "updated bio");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a author updated event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    /*
    @Pact(consumer = "author_delete-consumer")
    V4Pact createAuthorDeletedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("name", "author pt");
        body.stringType("bio", "author pt bio");
        body.stringType("authorNumber", "12345");
        body.stringMatcher("version", "[0-9]+", "1");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a author deleted event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }
     */


    @Test
    @PactTestFor(pactMethod = "createAuthorCreatedPact")
    void testAuthorCreated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
//
        // Convert the Pact message to a String (JSON payload)
        String jsonReceived = messages.get(0).contentsAsString();

        // Create a Spring AMQP Message with the JSON payload and optional headers
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

        // Simulate receiving the message in the listener
        assertDoesNotThrow(() -> {
            listener.receiveAuthorCreated(message);
        });

        // Verify interactions with the mocked service
        verify(authorService, times(1)).create(any(AuthorViewAMQP.class));
    }

    @Test
    @PactTestFor(pactMethod = "createAuthorUpdatedPact")
    void testAuthorUpdated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        String jsonReceived = messages.get(0).contentsAsString();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);
//
        assertDoesNotThrow(() -> {
            listener.receiveAuthorUpdated(message);
        });
//
//    // Verify interactions with the mocked service
        verify(authorService, times(1)).update(any(AuthorViewAMQP.class));
    }

    /*
    @Test
    @PactTestFor(pactMethod = "createAuthorDeletedPact")
    void testAuthorDeleted(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        String jsonReceived = messages.get(0).contentsAsString();

        System.out.println("Received Message: " + jsonReceived);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

        assertDoesNotThrow(() -> {
            listener.receiveAuthorDeleted(message);
        });

        verify(authorService, times(1)).deleteByAuthorNumber(any(Long.class));
    }
     */

}
