package pt.psoft.g1.psoftg1.genremanagement.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.publishers.GenreEventsPublisher;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreEventsPublisher genreEventsPublisher;

    public Optional<Genre> findByString(String name) {
        return genreRepository.findByString(name);
    }

    @Override
    public Genre create(CreateGenreRequest request) {

        final String genre = request.getGenre();

        Genre savedGenre = create(genre);

        if(savedGenre!=null) {
            genreEventsPublisher.sendGenreCreated(savedGenre);
        }

        return savedGenre;
    }

    @Override
    public Genre create(GenreViewAMQP genreViewAMQP) {

        final String genre = genreViewAMQP.getGenre();

        Genre createdGenre = create(genre);

        return createdGenre;
    }

    private Genre create(String genre) {
        Genre newGenre = new Genre(genre);

        Genre savedGenre = genreRepository.save(newGenre);

        if(savedGenre!=null){
            genreEventsPublisher.sendGenreCreated(newGenre);
        }

        return savedGenre;
    }

    @Override
    public CreateGenreRequest createGenre(String genreName, Long authorId) {

        if (genreName == null || genreName.isBlank()) {
            genreEventsPublisher.sendRemoveAuthorCreated(authorId);
            throw new IllegalArgumentException("Genre name cannot be null or blank.");
        }

        CreateGenreRequest newGenre = new CreateGenreRequest(genreName);
        this.create(newGenre.getGenre());

        return newGenre;
    }


    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

//    @Override
//    public List<GenreBookCountDTO> findTopGenreByBooks() {
//        Pageable pageableRules = PageRequest.of(0, 5);
//        return this.genreRepository.findTop5GenreByBookCount(pageableRules).getContent();
//    }

    @Override
    public Genre save(Genre genre) {
        return this.genreRepository.save(genre);
    }

//    @Override
//    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
//        return genreRepository.getLendingsPerMonthLastYearByGenre();
//    }

//    @Override
//    public List<GenreLendingsDTO> getAverageLendings(GetAverageLendingsQuery query, Page page) {
//        if (page == null)
//            page = new Page(1, 10);
//
//        final var month = LocalDate.of(query.getYear(), query.getMonth(), 1);
//
//        return genreRepository.getAverageLendingsInMonth(month, page);
//    }

//    @Override
//    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(String start, String end) {
//        LocalDate startDate;
//        LocalDate endDate;
//
//        try {
//            startDate = LocalDate.parse(start);
//            endDate = LocalDate.parse(end);
//        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("Expected format is YYYY-MM-DD");
//        }
//
//        if (startDate.isAfter(endDate))
//            throw new IllegalArgumentException("Start date cannot be after end date");
//
//        final var list = genreRepository.getLendingsAverageDurationPerMonth(startDate, endDate);
//
//        if (list.isEmpty())
//            throw new NotFoundException("No objects match the provided criteria");
//
//        return list;
//    }
}