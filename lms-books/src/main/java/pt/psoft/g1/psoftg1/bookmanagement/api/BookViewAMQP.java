package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "A Book form AMQP communication")
@NoArgsConstructor
public class BookViewAMQP {
    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private List<Long> authorIds;

    @NotNull
    private String genre;

    @NotNull
    private Long version;

    @Setter
    @Getter
    private Map<String, Object> _links = new HashMap<>();

    public BookViewAMQP(String isbn, String title, String description, List<Long> authorIds, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.authorIds = authorIds;
        this.genre = genre;
    }
}
