package pt.psoft.g1.psoftg1.suggestsmanagement.publishers;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.suggestsmanagement.api.SuggestViewAMQP;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;

public interface SuggestEventsPublisher {

    SuggestViewAMQP sendSuggestBookCreated(Suggest suggest);

    BookViewAMQP sendBookCreated(Book book);

    AuthorViewAMQP sendAuthorCreated(Author author);

    void sendGenreCreatedInSuggest(Genre genre);
}