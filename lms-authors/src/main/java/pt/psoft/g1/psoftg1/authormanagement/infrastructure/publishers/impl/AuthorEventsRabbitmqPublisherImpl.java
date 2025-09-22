package pt.psoft.g1.psoftg1.authormanagement.infrastructure.publishers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQPMapper;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.publishers.AuthorEventsPublisher;

import pt.psoft.g1.psoftg1.shared.model.AuthorEvents;
import pt.psoft.g1.psoftg1.shared.model.GenreEvents;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorEventsRabbitmqPublisherImpl implements AuthorEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange directAuthorExchange;
    private final AuthorViewAMQPMapper authorViewAMQPMapper;


    @Override
    public AuthorViewAMQP sendAuthorCreated(Author author) {
        return sendAuthorEvent(author, author.getVersion(), AuthorEvents.AUTHOR_CREATED);
    }

    @Override
    public void sendNewAuthorCreated(Author author) {
        sendAuthorEvent(author, author.getVersion(), AuthorEvents.NEWAUTHOR_CREATED);
    }

    @Override
    public AuthorViewAMQP sendAuthorUpdated(Author author, Long currentVersion) {
        return sendAuthorEvent(author, currentVersion, AuthorEvents.AUTHOR_UPDATED);
    }

    @Override
    public AuthorViewAMQP sendAuthorDeleted(Author author, Long currentVersion) {
        return sendAuthorEvent(author, currentVersion, AuthorEvents.AUTHOR_DELETED);
    }

    public AuthorViewAMQP sendAuthorEvent(Author author, Long currentVersion, String authorEventType) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            AuthorViewAMQP authorViewAMQP = authorViewAMQPMapper.toAuthorViewAMQP(author);
            authorViewAMQP.setVersion(currentVersion);

            String jsonString = objectMapper.writeValueAsString(authorViewAMQP);

            this.template.convertAndSend(directAuthorExchange.getName(), authorEventType, jsonString);

            System.out.println(" [x] Sent '" + jsonString + "'");
            return authorViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending author event: '" + ex.getMessage() + "'");
            return null;
        }
    }

    @Override
    public void sendGenreInfo(String genre, Long authorId) {
        try {

            Map<String, Object> genreInfo = new HashMap<>();
            genreInfo.put("genre", genre);
            genreInfo.put("authorId", authorId);

            String jsonString = new ObjectMapper().writeValueAsString(genreInfo);

            this.template.convertAndSend(directAuthorExchange.getName(), GenreEvents.GENRE_REQUEST, jsonString);

            System.out.println(" [x] Sent Genre Info: '" + jsonString + "'");
        } catch (Exception ex) {
            System.out.println(" [x] Exception sending genre event: '" + ex.getMessage() + "'");
        }
    }

}
