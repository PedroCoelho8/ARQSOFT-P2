package pt.psoft.g1.psoftg1.suggestsmanagement.api;

import lombok.Data;

@Data
public class SuggestViewAMQP {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String readerId;
    private String notes;
    private Long version;
    private String isbn;
    private Boolean approved;

    public String getIsbn() {
        return isbn;
    }
}