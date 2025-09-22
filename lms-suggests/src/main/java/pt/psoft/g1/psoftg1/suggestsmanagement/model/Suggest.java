package pt.psoft.g1.psoftg1.suggestsmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Suggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String authors;
    private String genre;
    private String readerId;
    private String notes;
    private String isbn;
    private Boolean approved;

    // Constructor with all parameters
    public Suggest(String title, String authors, String genre, String readerId, String notes, String isbn, Boolean approved) {
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.readerId = readerId;
        this.notes = notes;
        this.isbn = isbn;
        this.approved = approved;
    }


    public Suggest() {
    }

    // Getter for authors
    public String getAuthors() {
        return authors;
    }

    public Long getVersion() {
        return 1L;
    }
}