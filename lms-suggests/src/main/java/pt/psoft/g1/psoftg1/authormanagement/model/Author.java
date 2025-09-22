package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    @Getter
    private long authorNumber;

    @Version
    private long version;

    @Embedded
    private Name name;

    public void setName(String name) {
        this.name = new Name(name);
    }


    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return authorNumber;
    }

    public Author(String name) {
        setName(name);
    }

    protected Author() {
        // got ORM only
    }


    public String getName() {
        return this.name.toString();
    }

}