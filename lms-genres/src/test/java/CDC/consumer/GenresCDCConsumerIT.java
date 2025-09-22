package CDC.consumer;

import au.com.dius.pact.core.model.DefaultPactReader;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.core.model.PactReader;
import au.com.dius.pact.core.model.messaging.Message;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreRabbitmqController;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {GenreRabbitmqController.class, GenreService.class}
)
public class GenresCDCConsumerIT {

    @MockBean
    GenreService genreService;

    @Autowired
    GenreRabbitmqController listener;

    @Test
    void testMessageProcessing() throws Exception {

        // Use PactReader to load the Pact file
        File pactFile = new File("target/pacts/genre_created-consumer-genre_event-producer.json");
        PactReader pactReader = DefaultPactReader.INSTANCE;

        Pact pact = pactReader.loadPact(pactFile);

        List<Message> messagesGeneratedByPact = pact.asMessagePact().get().getMessages();
        for (Message messageGeneratedByPact : messagesGeneratedByPact) {
            // Convert the Pact message to a String (JSON payload)
            String jsonReceived = messageGeneratedByPact.contentsAsString();

            // prepare message properties
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("application/json");

            // Create a Spring AMQP Message with the JSON payload and optional headers
            org.springframework.amqp.core.Message messageToBeSentByRabbit = new org.springframework.amqp.core.Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

            // Simulate receiving the message in the RabbitMQ listener
            assertDoesNotThrow(() -> {
                listener.receiveGenreCreatedMsg(messageToBeSentByRabbit);
            });

            // somehow optional: verify interactions with the mocked service
            verify(genreService, times(1)).create(any(GenreViewAMQP.class));
        }
    }
}
