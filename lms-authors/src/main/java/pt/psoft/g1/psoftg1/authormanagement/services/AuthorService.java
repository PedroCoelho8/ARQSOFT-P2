package pt.psoft.g1.psoftg1.authormanagement.services;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
// import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
//import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Iterable<Author> findAll();

    Optional<Author> findByAuthorNumber(Long authorNumber);

    List<Author> findByName(String name);

    Author create(CreateAuthorRequest request); // REST request

    Author create(AuthorViewAMQP authorViewAMQP); // AMQP request

    Author partialUpdate(UpdateAuthorRequest request, Long currentVersion);

    Author update(AuthorViewAMQP authorViewAMQP);

//    List<Book> findBooksByAuthorNumber(Long authorNumber);

    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);

    Optional<Author> removeAuthorPhoto(Long authorNumber, long desiredVersion);

//    List<Book> findBooksByAuthorNumber(Long authorNumber);

    public Author createAuthorSaga();

  //  Author createWithBookInfo(CreateAuthorWithBookRequest request);

    void deleteByAuthorNumber(Long authorNumber);
}
