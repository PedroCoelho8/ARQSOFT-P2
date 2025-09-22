package pt.psoft.g1.psoftg1.bookmanagement.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import org.springframework.amqp.core.Message;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookRabbitmqController {

    @Autowired
    private final BookService bookService;

    @Autowired
    private final GenreService genreService;

    @Autowired
    private final AuthorService authorService;

    @RabbitListener(queues = "#{autoDeleteQueue_Book_Created.name}")
    public void receiveBookCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            BookViewAMQP bookViewAMQP = objectMapper.readValue(jsonReceived, BookViewAMQP.class);

            System.out.println(" [x] Received Book Created by AMQP: " + msg + ".");
            try {
                bookService.create(bookViewAMQP);
                System.out.println(" [x] New book inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Book already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Book_Updated.name}")
    public void receiveBookUpdated(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            BookViewAMQP bookViewAMQP = objectMapper.readValue(jsonReceived, BookViewAMQP.class);

            System.out.println(" [x] Received Book Updated by AMQP: " + msg + ".");
            try {
                bookService.update(bookViewAMQP);
                System.out.println(" [x] Book updated from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Book does not exists or wrong version. Nothing stored.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Genre_Book_Created.name}")
    public void receiveGenreCreatedMsg(Message msg) {
        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            GenreViewAMQP genreViewAMQP = objectMapper.readValue(jsonReceived, GenreViewAMQP.class);

            String genreName = genreViewAMQP.getGenre(); //.replace("\"", "").trim();

            System.out.println(" [x] Received Genre Created by AMQP: " + genreName);

            try {
                Genre newGenre = new Genre(genreName);
                genreService.save(newGenre);
                System.out.println(" [x] New Genre inserted from AMQP: " + genreName);
            } catch (Exception e) {
                System.out.println(" [x] Genre already exists or could not be saved: " + e.getMessage());
            }
        } catch (Exception ex) {
            System.out.println(" [x] Exception receiving genre event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_AuthorRemove_Books_Request.name}")
    public void receiveAuthorRemoveInBookRequest(Long authorId) {

        try {

            System.out.println(" [x] Received remove Author requeste with ID: " + authorId + ".");
            try {
                authorService.deleteByAuthorNumber(authorId);
                System.out.println(" [x] Author removed: " + authorId + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author not removed.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving author remove event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Books_Created.name}")
    public void receiveAuthorCreated(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(jsonReceived);

            String name = jsonNode.get("name").asText();

            System.out.println(" [x] Received Author Created by AMQP: " + name + ".");
            try {
                authorService.create(new AuthorViewAMQP(name));
                System.out.println(" [x] New author inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author already exists. No need to store it.");
            }
        } catch (Exception ex) {
            System.out.println(" [x] Exception receiving author event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Suggestion_Created.name}")
    public void receiveAuthorSuggestionCreated(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(jsonReceived);

            String name = jsonNode.get("name").asText();

            System.out.println(" [x] Received Author Created by AMQP: " + name + ".");
            try {
                authorService.create(new AuthorViewAMQP(name));
                System.out.println(" [x] New author inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author already exists. No need to store it.");
            }
        } catch (Exception ex) {
            System.out.println(" [x] Exception receiving author event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Book_Suggestion_Created.name}")
    public void receiveBookSuggestionCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            BookViewAMQP bookViewAMQP = objectMapper.readValue(jsonReceived, BookViewAMQP.class);

            System.out.println(" [x] Received Book Created by AMQP: " + msg + ".");
            try {
                bookService.create(bookViewAMQP);
                System.out.println(" [x] New book inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Book already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

}