package pt.psoft.g1.psoftg1.authormanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.publishers.AuthorEventsPublisher;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateBookWithDependenciesRequest;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AuthorRabbitmqController {

    private final AuthorService authorService;
    private final AuthorEventsPublisher authorEventsPublisher;
    private final AuthorRepository authorRepository;

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Created.name}")
    public void receiveAuthorCreated(Message msg) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            AuthorViewAMQP authorViewAMQP = objectMapper.readValue(jsonReceived, AuthorViewAMQP.class);

            System.out.println(" [x] Received Author Created by AMQP: " + msg + ".");
            try {
                authorService.create(authorViewAMQP);
                System.out.println(" [x] New author inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving author event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Updated.name}")
    public void receiveAuthorUpdated(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            AuthorViewAMQP authorViewAMQP = objectMapper.readValue(jsonReceived, AuthorViewAMQP.class);

            System.out.println(" [x] Received Author Updated by AMQP: " + msg + ".");
            try {
                authorService.update(authorViewAMQP);
                System.out.println(" [x] Author updated from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author does not exists or wrong version. Nothing stored.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving author event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Deleted.name}")
    public void receiveAuthorDeleted(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            AuthorViewAMQP authorViewAMQP = objectMapper.readValue(jsonReceived, AuthorViewAMQP.class);
            Long authorNumber = authorViewAMQP.getAuthorNumber();

            System.out.println(" [x] Received Author Removed by AMQP: " + msg + ".");
            try {
                authorService.deleteByAuthorNumber(authorNumber);
                System.out.println(" [x] New author removed from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Author dont exists. No need to remove it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving author event from AMQP: '" + ex.getMessage() + "'");
        }    }

    @RabbitListener(queues = "#{autoDeleteQueue_Author_Request.name}")
    public void receiveAuthorRequest(String jsonMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateBookWithDependenciesRequest resource = objectMapper.readValue(jsonMessage, CreateBookWithDependenciesRequest.class);

            Author newAuthor = new Author(
                    resource.getName(),
                    resource.getBio(),
                    resource.getPhotoURI()
            );

            Author savedAuthor = authorRepository.save(newAuthor);
            Long authorId = newAuthor.getId();

            if (savedAuthor != null) {
                authorEventsPublisher.sendAuthorCreated(savedAuthor);

                String genreName = resource.getGenre();
                authorEventsPublisher.sendGenreInfo(genreName, authorId);
            } else {
                System.out.println("Error! Author not created, reverting !!!!");
            }

        } catch (Exception e) {
            System.out.println(" [x] Exception receiving author request: '" + e.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_AuthorRemove_Request.name}")
    public void receiveAuthorRemoveRequest(Long authorId) {

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
}
