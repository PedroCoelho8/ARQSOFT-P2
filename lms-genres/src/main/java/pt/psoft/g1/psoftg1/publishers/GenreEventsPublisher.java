package pt.psoft.g1.psoftg1.publishers;

import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public interface GenreEventsPublisher {

    GenreViewAMQP sendGenreCreated(Genre genre);

    void sendRemoveAuthorCreated(Long authorId);

}