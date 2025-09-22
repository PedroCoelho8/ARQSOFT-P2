package pt.psoft.g1.psoftg1.authormanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A DTO for creating a Author")
public class CreateAuthorRequest {
    @Size(min = 1, max = 150)
    private String name;

}
