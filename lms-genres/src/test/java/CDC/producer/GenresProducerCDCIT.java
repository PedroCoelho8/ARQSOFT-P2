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
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQPMapperImpl;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.publishers.impl.GenreEventsRabbitmqPublisherImpl;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import pt.psoft.g1.psoftg1.publishers.GenreEventsPublisher;

import java.util.HashMap;


@Import(TestConfig.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {GenreEventsRabbitmqPublisherImpl.class, GenreService.class, GenreViewAMQPMapperImpl.class}
        , properties = {
        "stubrunner.amqp.mockConnection=true",
        "spring.profiles.active=test"
}
)
@Provider("genre_event-producer")
@PactFolder("target/pacts")
public class GenresProducerCDCIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenresProducerCDCIT.class);

    @Autowired
    GenreEventsPublisher genreEventsPublisher;

    @Autowired
    GenreViewAMQPMapperImpl genreViewAMQPMapper;

    @MockBean
    RabbitTemplate template;

    @MockBean
    DirectExchange directGenreExchange;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("a genre created event")
    public MessageAndMetadata genreCreated() throws JsonProcessingException {

        Genre genre = new Genre("Distopia");


        GenreViewAMQP genreViewAMQP = genreEventsPublisher.sendGenreCreated(genre);

        Message<String> message = new GenreMessageBuilder().withGenre(genreViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));

        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }
}
