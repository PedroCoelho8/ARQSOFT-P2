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
    public Optional<Author> findByAuthorNumber(final Long authorNumber) {
        return authorRepository.findByAuthorNumber(authorNumber);
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



}