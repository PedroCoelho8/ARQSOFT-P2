package pt.psoft.g1.psoftg1.genremanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request body for creating a new Genre")
public class CreateGenreRequest {

    @NotNull(message = "Genre name is required")
    @Size(min = 1, max = 100, message = "Genre name must be between 1 and 100 characters")
    @Schema(description = "The name of the Genre", example = "Fantasy")
    private String genre;

    public CreateGenreRequest(String genre) {
        this.genre = genre;
    }
}
