package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends AuthorRepository, CrudRepository<Author, Long> {
    @Override
    Optional<Author> findByAuthorNumber(Long authorNumber);

    @Query("SELECT DISTINCT coAuthor FROM Book b " + "JOIN b.authors coAuthor "
            + "WHERE b IN (SELECT b FROM Book b JOIN b.authors a WHERE a.authorNumber = :authorNumber) "
            + "AND coAuthor.authorNumber <> :authorNumber")
    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);
}
