package pt.psoft.g1.psoftg1.authormanagement.publishers;


import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

public interface AuthorEventsPublisher {
    AuthorViewAMQP sendAuthorCreated(Author author);

    AuthorViewAMQP sendAuthorUpdated(Author author, Long currentVersion);

    AuthorViewAMQP sendAuthorDeleted(Author author, Long currentVersion);

    void sendNewAuthorCreated(Author author);

//    void sendBookInfo();

    void sendGenreInfo(String genre, Long authorId);

}
