package CDC.consumer;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreRabbitmqController;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {GenreRabbitmqController.class, GenreService.class}
)
@PactConsumerTest
@PactTestFor(providerName = "genre_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class GenresCDCDefinitionTest {

    @MockBean
    GenreService genreService;

    @Autowired
    GenreRabbitmqController listener;

    @Pact(consumer = "genre_created-consumer")
    V4Pact createGenreCreatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("genre", "Distopia");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a genre created event").withMetadata(metadata).withContent(body).toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createGenreCreatedPact")
    void testGenreCreated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {

//  // Convert the Pact message to a String (JSON payload)
    String jsonReceived = messages.get(0).contentsAsString();
//
//    // Create a Spring AMQP Message with the JSON payload and optional headers
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("application/json");
    Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);
//
//    // Simulate receiving the message in the listener
    assertDoesNotThrow(() -> {
      listener.receiveGenreCreatedMsg(message);
    });
//
//    // Verify interactions with the mocked service
    verify(genreService, times(1)).create(any(GenreViewAMQP.class));
    }
}
