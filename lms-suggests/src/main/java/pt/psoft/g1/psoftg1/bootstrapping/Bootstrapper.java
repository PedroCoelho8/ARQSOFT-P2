package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@PropertySource({ "classpath:config/library.properties" })
@Order(2)
public class Bootstrapper implements CommandLineRunner {
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    private final AuthorRepository authorRepository;
//    private final BookRepository bookRepository;
    private final PhotoRepository photoRepository;

    private final ForbiddenNameService forbiddenNameService;

    @Override
    @Transactional
    public void run(final String... args) {
        createAuthors();
        loadForbiddenNames();
        createPhotos();
    }

    private void createAuthors() {
        if (authorRepository.searchByNameName("Manuel Antonio Pina").isEmpty()) {
            final Author author = new Author("Manuel Antonio Pina");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Antoine de Saint Exupéry").isEmpty()) {
            final Author author = new Author("Antoine de Saint Exupéry");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Alexandre Pereira").isEmpty()) {
            final Author author = new Author("Alexandre Pereira");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Filipe Portela").isEmpty()) {
            final Author author = new Author("Filipe Portela");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Ricardo Queirós").isEmpty()) {
            final Author author = new Author("Ricardo Queirós");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Freida Mcfadden").isEmpty()) {
            final Author author = new Author("Freida Mcfadden");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("J R R Tolkien").isEmpty()) {
            final Author author = new Author("J R R Tolkien");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Gardner Dozois").isEmpty()) {
            final Author author = new Author("Gardner Dozois");
            authorRepository.save(author);
        }
        if (authorRepository.searchByNameName("Lisa Tuttle").isEmpty()) {
            final Author author = new Author("Lisa Tuttle");
            authorRepository.save(author);
        }
    }

    protected void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }

    private void createPhotos() {
        /*
         * Optional<Photo> photoJoao = photoRepository.findByPhotoFile("foto-joao.jpg"); if(photoJoao.isEmpty()) { Photo
         * photo = new Photo(Paths.get("")) }
         */
    }
}
