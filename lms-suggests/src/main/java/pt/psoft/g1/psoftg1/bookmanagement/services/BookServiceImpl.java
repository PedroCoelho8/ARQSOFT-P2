package pt.psoft.g1.psoftg1.bookmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource({ "classpath:config/library.properties" })
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PhotoRepository photoRepository;

    @Value("${suggestionsLimitPerGenre}")
    private long suggestionsLimitPerGenre;

    @Override
    public Book create(CreateBookRequest request, String isbn) {

        final String title = request.getTitle();
        final String description = request.getDescription();
        final String photoURI = request.getPhotoURI();
        final String genre = request.getGenre();
        final List<Long> authorIds = request.getAuthors();

        Book savedBook = create(isbn, title, description, photoURI, genre, authorIds);

        return savedBook;
    }

    @Override
    public Book create(BookViewAMQP bookViewAMQP) {

        final String isbn = bookViewAMQP.getIsbn();
        final String description = bookViewAMQP.getDescription();
        final String title = bookViewAMQP.getTitle();
        final String photoURI = null;
        final String genre = bookViewAMQP.getGenre();
        final List<Long> authorIds = bookViewAMQP.getAuthorIds();

        Book bookCreated = create(isbn, title, description, photoURI, genre, authorIds);

        return bookCreated;
    }

    private Book create( String isbn,
                            String title,
                            String description,
                            String photoURI,
                            String genreName,
                            List<Long> authorIds) {

        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new ConflictException("Book with ISBN " + isbn + " already exists");
        }

        List<Author> authors = getAuthors(authorIds);

        final Genre genre = genreRepository.findByString(String.valueOf(genreName))
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        Book newBook = new Book(isbn, title, description, genre, authors, photoURI);

        Book savedBook = bookRepository.save(newBook);

        return savedBook;
    }


    private Book update( Book book,
                         Long currentVersion,
                         String title,
                         String description,
                         String photoURI,
                         String genreId,
                         List<Long> authorsId) {

        Genre genreObj = null;
        if (genreId != null) {
            Optional<Genre> genre = genreRepository.findByString(genreId);
            if (genre.isEmpty()) {
                throw new NotFoundException("Genre not found");
            }
            genreObj = genre.get();
        }

        List<Author> authors = new ArrayList<>();
        if (authorsId != null) {
            for (Long authorNumber : authorsId) {
                Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
                if (temp.isEmpty()) {
                    continue;
                }
                Author author = temp.get();
                authors.add(author);
            }
        }
        else
            authors = null;

        book.applyPatch(currentVersion, title, description, photoURI, genreObj, authors);

        Book updatedBook = bookRepository.save(book);

        return updatedBook;
    }


    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }


    public Book findByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException(Book.class, isbn));
    }


    private List<Author> getAuthors(List<Long> authorNumbers) {

        List<Author> authors = new ArrayList<>();
        for (Long authorNumber : authorNumbers) {

            Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
            if (temp.isEmpty()) {
                continue;
            }

            Author author = temp.get();
            authors.add(author);
        }

        return authors;
    }

}
