package CDC.producer;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import pt.psoft.g1.psoftg1.TestConfig;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQPMapperImpl;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.publishers.impl.AuthorEventsRabbitmqPublisherImpl;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.publishers.AuthorEventsPublisher;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Import(TestConfig.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {AuthorEventsRabbitmqPublisherImpl.class, AuthorService.class, AuthorViewAMQPMapperImpl.class}
        , properties = {
        "stubrunner.amqp.mockConnection=true",
        "spring.profiles.active=test"
}
)
@Provider("author_event-producer")
@PactFolder("target/pacts")
public class AuthorsProducerCDCIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorsProducerCDCIT.class);

    @Autowired
    AuthorEventsPublisher authorEventsPublisher;

    @Autowired
    AuthorViewAMQPMapperImpl authorViewAMQPMapper;

    @MockBean
    RabbitTemplate template;

    @MockBean
    DirectExchange directAuthorExchange;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("a author created event")
    public MessageAndMetadata authorCreated() throws JsonProcessingException {

        Author author = new Author(
                "author",
                "author bio",
                "description");

        AuthorViewAMQP authorViewAMQP = authorEventsPublisher.sendAuthorCreated(author);

        Message<String> message = new AuthorMessageBuilder().withAuthor(authorViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    @PactVerifyProvider("a author updated event")
    public MessageAndMetadata authorUpdated() throws JsonProcessingException {

        Author author = new Author(
                "Author updated",
                "updated bio",
                "null");

        AuthorViewAMQP authorViewAMQP = authorEventsPublisher.sendAuthorUpdated(author, 1L);

        Message<String> message = new AuthorMessageBuilder().withAuthor(authorViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));

        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }

}