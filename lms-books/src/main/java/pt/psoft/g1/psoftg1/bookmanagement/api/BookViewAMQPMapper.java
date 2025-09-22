package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BookViewAMQPMapper extends MapperInterface {

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "authorIds", expression = "java(mapAuthors(book.getAuthors()))")
    @Mapping(target = "version", source = "version")

    public abstract BookViewAMQP toBookViewAMQP(Book book);

    public abstract List<BookViewAMQP> toBookViewAMQP(List<Book> bookList);

    protected List<Long> mapAuthors(List<Author> authors) {
        return authors.stream().map(Author::getAuthorNumber).collect(Collectors.toList());
    }
}
