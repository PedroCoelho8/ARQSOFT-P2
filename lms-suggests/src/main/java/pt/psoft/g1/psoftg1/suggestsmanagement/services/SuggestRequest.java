package pt.psoft.g1.psoftg1.suggestsmanagement.services;

import lombok.Getter;
import lombok.Setter;

public class SuggestRequest {

    @Getter
    @Setter
    private String isbn;
    //private Long readerId;

    @Getter
    @Setter
    private String notes;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String authors;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String genre;

    @Getter
    @Setter
    private String readerId;

    @Getter
    @Setter
    private Boolean approved;


}