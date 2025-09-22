package pt.psoft.g1.psoftg1.authormanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A DTO for creating a new Author with book information")
public class CreateAuthorWithBookRequest {

    @Size(min = 1, max = 150)
    private String name;

    @Size(min = 1, max = 4096)
    private String bio;

    @Nullable
    @Getter
    @Setter
    private MultipartFile photo;

    @Nullable
    @Getter
    @Setter
    private String photoURI;

    @Size(min = 1, max = 255)
    private String title; // Title of the book (required for this request)

    @Size(min = 1, max = 1024)
    private String description; // Description of the book (required for this request)

    @Size(min = 10, max = 13)
    private String isbn; // ISBN of the book (required for this request)

    @Size(min = 1, max = 100)
    private String genre; // Genre of the book (required for this request)
}
