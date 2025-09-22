package pt.psoft.g1.psoftg1.suggestsmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;

import java.util.Optional;

public interface SuggestRepository extends JpaRepository<Suggest, Long> {

    Optional<Suggest> findByIsbn(String isbn);

}