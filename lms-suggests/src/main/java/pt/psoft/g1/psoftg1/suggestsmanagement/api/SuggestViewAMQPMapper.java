package pt.psoft.g1.psoftg1.suggestsmanagement.api;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;

@Component
public class SuggestViewAMQPMapper {

    public SuggestViewAMQP toSuggestViewAMQP(Suggest suggest) {
        SuggestViewAMQP suggestViewAMQP = new SuggestViewAMQP();
        suggestViewAMQP.setId(suggest.getId());
        suggestViewAMQP.setTitle(suggest.getTitle());
        suggestViewAMQP.setAuthor(suggest.getAuthors());
        suggestViewAMQP.setGenre(suggest.getGenre());
        suggestViewAMQP.setReaderId(suggest.getReaderId());
        suggestViewAMQP.setNotes(suggest.getNotes());
        suggestViewAMQP.setIsbn(suggest.getIsbn());
        suggestViewAMQP.setVersion(suggest.getVersion());
        suggestViewAMQP.setApproved(suggest.getApproved());
        return suggestViewAMQP;
    }
}