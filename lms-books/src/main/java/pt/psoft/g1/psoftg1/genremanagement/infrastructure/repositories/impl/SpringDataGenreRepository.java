package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.util.*;

public interface SpringDataGenreRepository extends GenreRepository, GenreRepoCustom, CrudRepository<Genre, Integer> {

    @Query("SELECT g FROM Genre g")
    List<Genre> findAllGenres();

    @Override
    @Query("SELECT g FROM Genre g WHERE g.genre = :genreName")
    Optional<Genre> findByString(@Param("genreName") @NotNull String genre);

//    @Override
//    @Query("SELECT new pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO(g.genre, COUNT(b))"
//            + "FROM Genre g " + "JOIN Book b ON b.genre.pk = g.pk " + "GROUP BY g " + "ORDER BY COUNT(b) DESC")
//    Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable);
}

interface GenreRepoCustom {
}

@RequiredArgsConstructor
class GenreRepoCustomImpl implements GenreRepoCustom {

    private final EntityManager entityManager;

    @NotNull
    private List<GenreLendingsPerMonthDTO> getGenreLendingsPerMonthDtos(
            Map<Integer, Map<Integer, List<GenreLendingsDTO>>> groupedResults) {
        List<GenreLendingsPerMonthDTO> lendingsPerMonth = new ArrayList<>();
        for (Map.Entry<Integer, Map<Integer, List<GenreLendingsDTO>>> yearEntry : groupedResults.entrySet()) {
            int yearValue = yearEntry.getKey();
            for (Map.Entry<Integer, List<GenreLendingsDTO>> monthEntry : yearEntry.getValue().entrySet()) {
                int monthValue = monthEntry.getKey();
                List<GenreLendingsDTO> values = monthEntry.getValue();
                lendingsPerMonth.add(new GenreLendingsPerMonthDTO(yearValue, monthValue, values));
            }
        }

        return lendingsPerMonth;
    }
}