package pt.psoft.g1.psoftg1.authormanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper mapper;
    private final PhotoRepository photoRepository;

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findByAuthorNumber(final Long authorNumber) {
        return authorRepository.findByAuthorNumber(authorNumber);
    }

    @Override
    public List<Author> findByName(String name) {
        return authorRepository.searchByNameNameStartsWith(name);
    }

    @Override
    public Author create(final CreateAuthorRequest request) {

        final String name = request.getName();

        Author author = create(name);

        /*
        if(savedAuthor!=null){
            authorEventsPublisher.sendAuthorCreated(savedAuthor);
        }
        */

        return author;

    }

    @Override
    public Author create(AuthorViewAMQP authorViewAMQP){
        final String name = authorViewAMQP.getName();

        Author authorCreated = create(name);

        return authorCreated;

    }

    private Author create(String name){
        Author newAuthor = new Author(name);

        Author savedAuthor = authorRepository.save(newAuthor);

        return savedAuthor;
    }

    @Override
    public Author partialUpdate(final Long authorNumber, final UpdateAuthorRequest request, final long desiredVersion) {
        // first let's check if the object exists so we don't create a new object with
        // save
        final var author = findByAuthorNumber(authorNumber)
                .orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));
        /*
         * Since photos can be null (no photo uploaded) that means the URI can be null as well. To avoid the client
         * sending false data, photoURI has to be set to any value / null according to the MultipartFile photo object
         *
         * That means: - photo = null && photoURI = null -> photo is removed - photo = null && photoURI = validString ->
         * ignored - photo = validFile && photoURI = null -> ignored - photo = validFile && photoURI = validString ->
         * photo is set
         */

        // since we got the object from the database we can check the version in memory
        // and apply the patch
        author.applyPatch(desiredVersion, request);

        // in the meantime some other user might have changed this object on the
        // database, so concurrency control will still be applied when we try to save
        // this updated object
        return authorRepository.save(author);
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        return bookRepository.findBooksByAuthorNumber(authorNumber);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return authorRepository.findCoAuthorsByAuthorNumber(authorNumber);
    }

    @Override
    public void deleteByAuthorNumber(Long authorNumber) {
        Optional<Author> optionalAuthor = authorRepository.findByAuthorNumber(authorNumber);

        if (!optionalAuthor.isPresent()) {
            throw new NotFoundException("Author with number " + authorNumber + " not found.");
        }

        Author author = optionalAuthor.get();
        authorRepository.delete(author);


        System.out.println("Author with number " + authorNumber + " deleted successfully.");
    }

}