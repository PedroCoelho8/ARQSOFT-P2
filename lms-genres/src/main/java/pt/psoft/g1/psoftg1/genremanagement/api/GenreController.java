package pt.psoft.g1.psoftg1.genremanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.CreateGenreRequest;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;


@Tag(name = "Genre", description = "Endpoints for managing Genres")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreViewMapper genreViewMapper;

    public GenreController(GenreService genreService, GenreViewMapper genreViewMapper) {
        this.genreService = genreService;
        this.genreViewMapper = genreViewMapper;
    }

    @Operation(summary = "Creates a new Genre",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Genre created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreView.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Genre data")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenreView> create(CreateGenreRequest resource) {
        // Create the genre
        Genre genre = genreService.create(resource);

        // Convert to GenreView for the response
        GenreView genreView = genreViewMapper.toGenreView(genre);

        return ResponseEntity.status(HttpStatus.CREATED).body(genreView);
    }
}