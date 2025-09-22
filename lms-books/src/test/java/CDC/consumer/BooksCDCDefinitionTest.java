package CDC.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookRabbitmqController;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;


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
        ,classes = {BookRabbitmqController.class, BookService.class, GenreService.class, AuthorService.class}
)
@PactConsumerTest
@PactTestFor(providerName = "book_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class BooksCDCDefinitionTest {

  @MockBean
  BookService bookService;

  @MockBean
  GenreService genreService;

  @MockBean
  AuthorService authorService;

  @Autowired
  BookRabbitmqController listener;

  @Pact(consumer = "book_created-consumer")
  V4Pact createBookCreatedPact(MessagePactBuilder builder) {
    PactDslJsonBody body = new PactDslJsonBody();
    body.stringType("isbn", "6475803429671");
    body.stringType("title", "title");
    body.stringType("description", "description");
    body.stringType("genre", "Infantil");
    body.array("authorIds")
            .integerType(1)
            .closeArray();
    body.stringMatcher("version", "[0-9]+", "1");

    Map<String, Object> metadata = new HashMap<>();
    metadata.put("Content-Type", "application/json");

    return builder.expectsToReceive("a book created event").withMetadata(metadata).withContent(body).toPact();
  }

  @Pact(consumer = "book_updated-consumer")
  V4Pact createBookUpdatedPact(MessagePactBuilder builder) {
    PactDslJsonBody body = new PactDslJsonBody()
            .stringType("isbn", "6475803429671")
            .stringType("title", "updated title")
            .stringType("description", "description")
            .stringType("genre", "Infantil");
        body.array("authorIds")
            .integerType(1)
            .closeArray();

    Map<String, Object> metadata = new HashMap<>();
    metadata.put("Content-Type", "application/json");

    return builder.expectsToReceive("a book updated event")
            .withMetadata(metadata)
            .withContent(body)
            .toPact();
  }
//
// The following tests are now defined as IT tests, so that the definition of contract and the tests are decoupled.
// Yet, while the body of the tests can be elsewhere, the method signature must be defined here so the contract is generated.
//

  @Test
  @PactTestFor(pactMethod = "createBookCreatedPact")
  void testBookCreated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
//
  // Convert the Pact message to a String (JSON payload)
    String jsonReceived = messages.get(0).contentsAsString();

    // Create a Spring AMQP Message with the JSON payload and optional headers
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("application/json");
    Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

    // Simulate receiving the message in the listener
    assertDoesNotThrow(() -> {
      listener.receiveBookCreatedMsg(message);
    });

    // Verify interactions with the mocked service
    verify(bookService, times(1)).create(any(BookViewAMQP.class));
  }

  @Test
  @PactTestFor(pactMethod = "createBookUpdatedPact")
  void testBookUpdated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
    String jsonReceived = messages.get(0).contentsAsString();
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("application/json");
    Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);
//
    assertDoesNotThrow(() -> {
      listener.receiveBookUpdated(message);
    });
//
//    // Verify interactions with the mocked service
    verify(bookService, times(1)).update(any(BookViewAMQP.class));
  }

}