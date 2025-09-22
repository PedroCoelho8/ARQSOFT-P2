package pt.psoft.g1.psoftg1.suggestsmanagement.services;

import pt.psoft.g1.psoftg1.suggestsmanagement.api.SuggestViewAMQP;
import pt.psoft.g1.psoftg1.suggestsmanagement.model.Suggest;

public interface SuggestService {

    Suggest suggestBook(String isbn, String readerId, String notes, String title, String name, String genre, Boolean approved);

    Suggest create(SuggestViewAMQP suggestViewAMQP);

    Suggest updateApproved(Long suggestId, Boolean approved);

}
