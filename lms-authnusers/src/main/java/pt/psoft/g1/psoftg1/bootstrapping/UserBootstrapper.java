package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class UserBootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private List<String> queriesToExecute = new ArrayList<>();

    @Override
    @Transactional
    public void run(final String... args) {
        createReaders();
        createLibrarian();
        executeQueries();
    }

    private void createReaders() {
        // Reader1 - Manuel
        if (userRepository.findByUsername("manuel@gmail.com").isEmpty()) {
            final Reader manuel = Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
            userRepository.save(manuel);
        }
    }

    private void createLibrarian() {
        // Maria
        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Mariaroberta!123", "Maria Roberta");
            userRepository.save(maria);
        }
    }

    private void executeQueries() {
        for (String query : queriesToExecute) {
            jdbcTemplate.update(query);
        }
    }
}
