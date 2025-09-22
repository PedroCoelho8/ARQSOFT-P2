package pt.psoft.g1.psoftg1.suggestsmanagement.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;
import pt.psoft.g1.psoftg1.suggestsmanagement.services.SuggestRequest;
import pt.psoft.g1.psoftg1.suggestsmanagement.services.SuggestService;

@Tag(name = "Genre", description = "Endpoints for managing Genres")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suggest")
public class SuggestController {

    private final SuggestService suggestService;

    @PostMapping("/isbn")
    public ResponseEntity<Suggest> suggestBookByIsbn(@RequestBody SuggestRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String readerId = authentication.getName();

        Suggest suggestedBook = suggestService.suggestBook(
                request.getIsbn(),
                readerId,
                request.getNotes(),
                request.getTitle(),
                request.getName(),
                request.getGenre(),
                false
        );

        return ResponseEntity.ok(suggestedBook);

    }

    @PutMapping("/{suggestId}/approve")
    public ResponseEntity<Suggest> updateApproved(@PathVariable Long suggestId, Boolean approved) {
        Suggest updatedSuggest = suggestService.updateApproved(suggestId, approved);
        return ResponseEntity.ok(updatedSuggest);
    }
}
