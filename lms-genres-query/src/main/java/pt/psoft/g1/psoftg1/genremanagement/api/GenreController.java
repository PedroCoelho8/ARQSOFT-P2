package pt.psoft.g1.psoftg1.genremanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Tag(name = "Genre", description = "Endpoints for managing Genres Query")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreViewMapper genreViewMapper;

    public GenreController(GenreService genreService, GenreViewMapper genreViewMapper) {
        this.genreService = genreService;
        this.genreViewMapper = genreViewMapper;
    }

    @Operation(summary = "Gets a specific Genre by name")
    @GetMapping(value = "/{name}")
    public ResponseEntity<GenreView> findByName(@PathVariable final String name) {
        return genreService.findByString(name)
                .map(genre -> ResponseEntity.ok(genreViewMapper.toGenreView(genre)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets all Genres")
    @GetMapping
    public ResponseEntity<List<GenreView>> findAll() {
        List<GenreView> genres = StreamSupport.stream(genreService.findAll().spliterator(), false)
                .map(genreViewMapper::toGenreView)
                .collect(Collectors.toList());

        return ResponseEntity.ok(genres);
    }

}