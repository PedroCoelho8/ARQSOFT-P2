package pt.psoft.g1.psoftg1.authormanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "A DTO for creating a Book with Author and Genre data")
public class CreateBookWithDependenciesRequest {

    @NotBlank
    private String title;

    @Nullable
    private String description;

    @Nullable
    private MultipartFile photo;

    @Nullable
    private String photoURI;

    @NotBlank
    private String genre; // Genre name

    @NotNull
    private List<Long> authors;

    @Size(min = 1, max = 150)
    private String name;

    @Size(min = 1, max = 4096)
    private String bio;

    @Nullable
    @Getter
    @Setter
    private MultipartFile authorPhoto;

    @Nullable
    @Getter
    @Setter
    private String authorPhotoURI;

}
