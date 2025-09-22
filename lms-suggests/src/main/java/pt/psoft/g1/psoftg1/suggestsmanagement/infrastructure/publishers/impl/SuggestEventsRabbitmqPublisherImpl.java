package pt.psoft.g1.psoftg1.suggestsmanagement.infrastructure.publishers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQPMapper;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQPMapper;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.AuthorEvents;
import pt.psoft.g1.psoftg1.shared.model.BookEvents;
import pt.psoft.g1.psoftg1.shared.model.SuggestEvents;
import pt.psoft.g1.psoftg1.suggestsmanagement.api.SuggestViewAMQP;
import pt.psoft.g1.psoftg1.suggestsmanagement.api.SuggestViewAMQPMapper;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;
import pt.psoft.g1.psoftg1.suggestsmanagement.publishers.SuggestEventsPublisher;

@Service
@RequiredArgsConstructor
public class SuggestEventsRabbitmqPublisherImpl implements SuggestEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange directSuggestExchange;
    @Autowired
    private final SuggestViewAMQPMapper suggestViewAMQPMapper;
    @Autowired
    private final BookViewAMQPMapper bookViewAMQPMapper;
    @Autowired
    private final AuthorViewAMQPMapper authorViewAMQPMapper;

    @Override
    public SuggestViewAMQP sendSuggestBookCreated(Suggest suggest) {
        return sendSuggestedBookEvent(suggest, 1L, SuggestEvents.SUGGESTION_CREATED);
    }

    @Override
    public BookViewAMQP sendBookCreated(Book book) {
        return sendBookEvent(book, 1L, BookEvents.BOOKSUGGESTED_CREATED);
    }

    @Override
    public AuthorViewAMQP sendAuthorCreated(Author author) {
        return sendAuthorEvent(author, author.getVersion(), AuthorEvents.AUTHOR_SUGGEST_CREATED);
    }

    @Override
    public void sendGenreCreatedInSuggest(Genre genre) {
    }

    private SuggestViewAMQP sendSuggestedBookEvent(Suggest suggest, Long currentVersion, String suggestedEventType) {

        System.out.println("Send Suggested Book event to AMQP Broker: " + suggest.getTitle());

        try {
            SuggestViewAMQP suggestViewAMQP = suggestViewAMQPMapper.toSuggestViewAMQP(suggest);
            suggestViewAMQP.setVersion(currentVersion);

            ObjectMapper objectMapper = new ObjectMapper();
            String suggestViewAMQPinString = objectMapper.writeValueAsString(suggestViewAMQP);

            this.template.convertAndSend(directSuggestExchange.getName(), suggestedEventType, suggestViewAMQPinString);

            return suggestViewAMQP;
        } catch (Exception ex) {
            System.out.println(" [x] Exception sending suggested book event: '" + ex.getMessage() + "'");

            return null;
        }
    }

    public AuthorViewAMQP sendAuthorEvent(Author author, Long currentVersion, String authorEventType) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            AuthorViewAMQP authorViewAMQP = authorViewAMQPMapper.toAuthorViewAMQP(author);
            authorViewAMQP.setVersion(currentVersion);

            String jsonString = objectMapper.writeValueAsString(authorViewAMQP);

            this.template.convertAndSend(directSuggestExchange.getName(), authorEventType, jsonString);

            System.out.println(" [x] Sent '" + jsonString + "'");
            return authorViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending author event: '" + ex.getMessage() + "'");
            return null;
        }
    }

    private BookViewAMQP sendBookEvent(Book book, Long currentVersion, String bookEventType) {

        System.out.println("Send Book event to AMQP Broker: " + book.getTitle());

        try {
            BookViewAMQP bookViewAMQP = bookViewAMQPMapper.toBookViewAMQP(book);
            bookViewAMQP.setVersion(currentVersion);

            ObjectMapper objectMapper = new ObjectMapper();
            String bookViewAMQPinString = objectMapper.writeValueAsString(bookViewAMQP);

            this.template.convertAndSend(directSuggestExchange.getName(), bookEventType, bookViewAMQPinString);

            return bookViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending book event: '" + ex.getMessage() + "'");

            return null;
        }
    }
}
