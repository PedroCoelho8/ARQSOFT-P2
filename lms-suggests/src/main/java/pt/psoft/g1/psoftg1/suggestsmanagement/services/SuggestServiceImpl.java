package pt.psoft.g1.psoftg1.suggestsmanagement.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import pt.psoft.g1.psoftg1.suggestsmanagement.api.SuggestViewAMQP;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;
import pt.psoft.g1.psoftg1.suggestsmanagement.publishers.SuggestEventsPublisher;
import pt.psoft.g1.psoftg1.suggestsmanagement.repositories.SuggestRepository;


import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestServiceImpl implements SuggestService {

    private final SuggestRepository suggestRepository;
    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final SuggestEventsPublisher suggestEventsPublisher;

    @Transactional
    @Override
    public Suggest suggestBook(String isbn, String readerId, String notes, String title, String name, String genre, Boolean approved) {

        Genre newGenre = new Genre(genre);
        genreRepository.save(newGenre);

        Author newAuthor = new Author(name);
        authorRepository.save(newAuthor);

        final Genre genreExists = genreRepository.findByString(String.valueOf(newGenre))
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        List<Author> authorsList = new ArrayList<>();
        authorsList.add(newAuthor);

        Book newBook = new Book(isbn, title, null, genreExists, authorsList, null);
        bookRepository.save(newBook);

        final String bookISBN = newBook.getIsbn();

        Book bookSearched = bookService.findByIsbn(bookISBN);
        Suggest suggestBook = new Suggest(
                bookSearched.getTitle().toString(),
                bookSearched.getAuthors().stream().map(author -> author.getName()).collect(Collectors.joining(", ")),
                bookSearched.getGenre().getName(),
                readerId,
                notes,
                isbn,
                approved
                );

        Suggest savedSuggestedBook = suggestRepository.save(suggestBook);
        if(savedSuggestedBook!=null){
            suggestEventsPublisher.sendSuggestBookCreated(savedSuggestedBook);
        }


        return savedSuggestedBook;
    }

    @Override
    public Suggest create(SuggestViewAMQP suggestViewAMQP) {
        String isbn = suggestViewAMQP.getIsbn();
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        Optional<Suggest> existingSuggestedBook = suggestRepository.findByIsbn(isbn);
        if (existingSuggestedBook.isPresent()) {
            return existingSuggestedBook.get();
        }

        Suggest suggestedBook = new Suggest(
                suggestViewAMQP.getTitle(),
                suggestViewAMQP.getAuthor(),
                suggestViewAMQP.getGenre(),
                suggestViewAMQP.getReaderId(),
                suggestViewAMQP.getNotes(),
                isbn,
                suggestViewAMQP.getApproved()
        );
        return suggestRepository.save(suggestedBook);
    }

    @Transactional
    @Override
    public Suggest updateApproved(Long suggestId, Boolean approved) {
        Suggest suggest = suggestRepository.findById(suggestId)
                .orElseThrow(() -> new NotFoundException("Suggest not found"));

        final String isbn = suggest.getIsbn();
        Book book = bookService.findByIsbn(isbn);

        final String nameAuthor = suggest.getAuthors();
        List<Author> authorList = authorRepository.searchByNameName(nameAuthor);
        Author author = authorList.get(0);

        if(approved == true) {
            suggestEventsPublisher.sendAuthorCreated(author);
            suggestEventsPublisher.sendBookCreated(book);
            suggest.setApproved(approved);
        }
        return suggestRepository.save(suggest);
    }
}
