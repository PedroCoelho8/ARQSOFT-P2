package pt.psoft.g1.psoftg1.suggestsmanagement.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import pt.psoft.g1.psoftg1.suggestsmanagement.services.SuggestService;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SuggestRabbitmqController {

    private final SuggestService suggestService;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @RabbitListener(queues = "#{autoDeleteQueue_Suggest_Created.name}")
    public void receiveSuggestedBookCreatedMsg(Message msg) {
        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            SuggestViewAMQP suggestViewAMQP = objectMapper.readValue(jsonReceived, SuggestViewAMQP.class);

            System.out.println(" [x] Received Suggested Book Created by AMQP: " + msg + ".");
            try {
                suggestService.create(suggestViewAMQP);
                System.out.println(" [x] New suggested book inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Suggested book already exists. No need to store it.");
            }
        } catch (Exception ex) {
            System.out.println(" [x] Exception receiving suggested book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Book_Suggest_Created.name}")
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

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Suggest_Created.name}")
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

    @RabbitListener(queues = "#{autoDeleteQueue_Genre_Suggest_Created.name}")
    public void receiveGenreCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            GenreViewAMQP genreViewAMQP = objectMapper.readValue(jsonReceived, GenreViewAMQP.class);

            System.out.println(" [x] Received Genre Created by AMQP: " + msg + ".");
            try {
                genreService.create(genreViewAMQP);
                System.out.println(" [x] New Genre inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Genre already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving genre event from AMQP: '" + ex.getMessage() + "'");
        }
    }
}