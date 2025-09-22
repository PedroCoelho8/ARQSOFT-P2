package pt.psoft.g1.psoftg1.authormanagement.services;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorRequest {

    @Size(max = 150)
    private String name;

}
