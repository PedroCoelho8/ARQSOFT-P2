package pt.psoft.g1.psoftg1.authormanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.publishers.AuthorEventsPublisher;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
//import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
//import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
  //  private final BookRepository bookRepository;
    private final AuthorMapper mapper;
    private final PhotoRepository photoRepository;

    private final AuthorEventsPublisher authorEventsPublisher;

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
        final String bio = request.getBio();
        final String photoURI = request.getPhotoURI();

        Author savedAuthor = create(name, bio, photoURI);

        if(savedAuthor!=null){
            authorEventsPublisher.sendAuthorCreated(savedAuthor);
        }

        return savedAuthor;

    }

    @Override
    public Author create(AuthorViewAMQP authorViewAMQP){
        final String name = authorViewAMQP.getName();
        final String bio = authorViewAMQP.getBio();
        final String photoURI = null;
        final Long authorNumber = authorViewAMQP.getAuthorNumber();

        if(authorRepository.findByAuthorNumber(authorNumber).isPresent()){
            throw new ConflictException("Author already exists");
        }

        Author authorCreated = create(name, bio, photoURI);

        return authorCreated;

    }

    private Author create(String name,
                          String bio,
                          String photoURI){
        Author newAuthor = new Author(name, bio, photoURI);

        Author savedAuthor = authorRepository.save(newAuthor);

        return savedAuthor;
    }

    /*
    @Override
    public Author createWithBookInfo(CreateAuthorWithBookRequest request) {
        final String name = request.getName();
        final String bio = request.getBio();
        final String photoURI = request.getPhotoURI();

        final String title = request.getTitle();
        final String description = request.getDescription();
        final String isbn = request.getIsbn();
        final String genre = request.getGenre();

        Author savedAuthor = create(name, bio, photoURI);

        if (savedAuthor != null) {
            // Aqui você pode adicionar a lógica para associar o livro ao autor
            // bookService.createBook(title, description, isbn, genre, savedAuthor);
            authorEventsPublisher.sendAuthorCreated(savedAuthor);
            authorEventsPublisher.sendGenreInfo(genre);
        }

        return savedAuthor;
    }
    */

    @Override
    public Author createAuthorSaga(){

        final String name = "Autor Novo";
        final String bio = "Bio do Autor Novo";
        final String photoURI = null;

        Author newAuthor = new Author(name, bio, photoURI);

        Author savedAuthor = authorRepository.save(newAuthor);

        if(savedAuthor!=null){
            authorEventsPublisher.sendNewAuthorCreated(savedAuthor);
        }

        return savedAuthor;
    }

    @Override
    public Author partialUpdate(UpdateAuthorRequest request, Long currentVersion) {

        Optional<Author> optionalAuthor = findByAuthorNumber(request.getAuthorNumber());

        if (!optionalAuthor.isPresent()) {
            throw new NotFoundException("Author with number " + request.getAuthorNumber() + " not found.");
        }

        Author author = optionalAuthor.get();

        String name = request.getName();
        String bio = request.getBio();
        MultipartFile photo = request.getPhoto();
        String photoURI = request.getPhotoURI();

        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            request.setPhoto(null);
            request.setPhotoURI(null);
        }

        Author updatedAuthor = update(author, currentVersion, name, bio, request.getPhotoURI());

        if (updatedAuthor != null) {
            authorEventsPublisher.sendAuthorUpdated(updatedAuthor, currentVersion);
        }

        return updatedAuthor;
    }


    @Override
    public Author update(AuthorViewAMQP authorViewAMQP){

        final Long version = authorViewAMQP.getVersion();
        final Long authorNumber = authorViewAMQP.getAuthorNumber();
        final String name = authorViewAMQP.getName();
        final String bio = authorViewAMQP.getBio();
        final String photoURI = null;

        Optional<Author> optionalAuthor = findByAuthorNumber(authorNumber);

        if (!optionalAuthor.isPresent()) {
            throw new NotFoundException("Author with number " + authorNumber + " not found.");
        }

        Author author = optionalAuthor.get();

        Author authorUpdated = update(author, version, name, bio, photoURI);

        return authorUpdated;
    }


    private Author update(Author author,
                          Long currentVersion,
                          String name,
                          String bio,
                          String photoURI) {

        author.applyPatch(currentVersion, name, bio, photoURI);

        Author updatedAuthor = authorRepository.save(author);

        return updatedAuthor;

    }

/*
    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        return bookRepository.findBooksByAuthorNumber(authorNumber);
    }

 */


    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return authorRepository.findCoAuthorsByAuthorNumber(authorNumber);
    }

    @Override
    public Optional<Author> removeAuthorPhoto(Long authorNumber, long desiredVersion) {
        Author author = authorRepository.findByAuthorNumber(authorNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find reader"));

        String photoFile = author.getPhoto().getPhotoFile();
        author.removePhoto(desiredVersion);

        Optional<Author> deletedAuthor = Optional.of(authorRepository.save(author));

        Author author1 = deletedAuthor.get();


        if(deletedAuthor != null){
            photoRepository.deleteByPhotoFile(photoFile);
            authorEventsPublisher.sendAuthorDeleted(author1, desiredVersion);
        }

        return deletedAuthor;
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
