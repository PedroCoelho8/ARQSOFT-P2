package pt.psoft.g1.psoftg1.bookmanagement.services;

import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;

/**
 *
 */
public interface BookService {

    Book create(CreateBookRequest request, String isbn); // REST request

    Book create(BookViewAMQP bookViewAMQP); // AMQP request

    Book findByIsbn(String isbn);

}
