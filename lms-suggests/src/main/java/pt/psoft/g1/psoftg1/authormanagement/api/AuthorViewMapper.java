package pt.psoft.g1.psoftg1.authormanagement.api;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookShortView;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper extends MapperInterface {

    @Mapping(target = "_links", expression = "java(mapLinks(author))")
    public abstract AuthorView toAuthorView(Author author);

    public abstract List<AuthorView> toAuthorView(List<Author> authors);

    @Named(value = "mapAuthorLinks")
    public Map<String, Object> mapLinks(final Author author) {
        String authorUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authors/")
                .path(author.getId().toString()).toUriString();

        String booksByAuthorUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authors/")
                .path(author.getId().toString()).path("/books").toUriString();

        Map<String, Object> links = new HashMap<>();

        links.put("author", authorUri);
        links.put("booksByAuthor", booksByAuthorUri);

        return links;
    }


    @Named(value = "mapBookShortLink")
    public String mapShortBookLink(final Book book) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/books/").path(book.getIsbn())
                .toUriString();
    }

}
