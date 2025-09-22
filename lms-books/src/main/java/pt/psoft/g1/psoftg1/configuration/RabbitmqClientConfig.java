package pt.psoft.g1.psoftg1.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookRabbitmqController;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import pt.psoft.g1.psoftg1.shared.model.AuthorEvents;
import pt.psoft.g1.psoftg1.shared.model.BookEvents;
import pt.psoft.g1.psoftg1.shared.model.GenreEvents;

@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("LMS.books");
    }

    @Bean
    public DirectExchange directAuthorExchange() {
        return new DirectExchange("LMS.authors");
    }

    @Bean
    public DirectExchange directSuggestExchange() {
        return new DirectExchange("LMS.suggests");
    }

    @Bean
    public DirectExchange directGenreExchange() {
        return new DirectExchange("LMS.genres");
    }

    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_Author_Books_Created")
        public Queue autoDeleteQueue_Author_Books_Created() {
            System.out.println("autoDeleteQueue_Author_Books_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Author_Suggestion_Created")
        public Queue autoDeleteQueue_Author_Suggestion_Created() {
            System.out.println("autoDeleteQueue_Author_Suggestion_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Author_Books_Request")
        public Queue autoDeleteQueue_Author_Books_Request() {
            System.out.println("autoDeleteQueue_Author_Books_Request created!");
            return new AnonymousQueue();
        }


        @Bean(name = "autoDeleteQueue_AuthorRemove_Books_Request")
        public Queue autoDeleteQueue_AuthorRemove_Books_Request() {
            System.out.println("autoDeleteQueue_AuthorRemove_Books_Request created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Book_Created")
        public Queue autoDeleteQueue_Book_Created() {

            System.out.println("autoDeleteQueue_Book_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Book_Suggestion_Created")
        public Queue autoDeleteQueue_Book_Suggestion_Created() {
            System.out.println("autoDeleteQueue_Book_Suggestion_Created created!");
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue_Book_Updated() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue_Book_Deleted() {
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Genre_Book_Created")
        public Queue autoDeleteQueue_Genre_Book_Created() {
            System.out.println("autoDeleteQueue_Genre_Book_Created created!");
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingAuthor(DirectExchange directAuthorExchange,
                                     @Qualifier("autoDeleteQueue_Author_Books_Created") Queue autoDeleteQueue_Author_Books_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Author_Books_Created)
                    .to(directAuthorExchange)
                    .with(AuthorEvents.AUTHOR_CREATED);
        }

        @Bean
        public Binding bindingAuthorRequest(DirectExchange directAuthorExchange,
                                     @Qualifier("autoDeleteQueue_Author_Books_Request") Queue autoDeleteQueue_Author_Books_Request) {
            return BindingBuilder.bind(autoDeleteQueue_Author_Books_Request)
                    .to(directAuthorExchange)
                    .with(AuthorEvents.NEWAUTHOR_CREATED);
        }

        @Bean
        public Binding bindingAuthorSuggestion(DirectExchange directSuggestExchange,
                                            @Qualifier("autoDeleteQueue_Author_Suggestion_Created") Queue autoDeleteQueue_Author_Suggestion_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Author_Suggestion_Created)
                    .to(directSuggestExchange)
                    .with(AuthorEvents.AUTHOR_SUGGEST_CREATED);
        }

        @Bean
        public Binding binding1(DirectExchange direct,
                                @Qualifier("autoDeleteQueue_Book_Created") Queue autoDeleteQueue_Book_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Book_Created)
                    .to(direct)
                    .with(BookEvents.BOOK_CREATED);
        }

        @Bean
        public Binding binding2(DirectExchange direct,
                                Queue autoDeleteQueue_Book_Updated) {
            return BindingBuilder.bind(autoDeleteQueue_Book_Updated)
                    .to(direct)
                    .with(BookEvents.BOOK_UPDATED);
        }

        @Bean
        public Binding binding3(DirectExchange direct,
                                Queue autoDeleteQueue_Book_Deleted) {
            return BindingBuilder.bind(autoDeleteQueue_Book_Deleted)
                    .to(direct)
                    .with(BookEvents.BOOK_DELETED);
        }

        @Bean
        public Binding bindingGenre(DirectExchange directGenreExchange,
                                    @Qualifier("autoDeleteQueue_Genre_Book_Created") Queue autoDeleteQueue_Genre_Book_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Genre_Book_Created)
                    .to(directGenreExchange)
                    .with(GenreEvents.GENRE_CREATED);
        }

        @Bean
        public Binding bindingAuthorRemoveInBookRequest(DirectExchange directGenreExchange,
                                    @Qualifier("autoDeleteQueue_AuthorRemove_Books_Request") Queue autoDeleteQueue_AuthorRemove_Books_Request) {
            return BindingBuilder.bind(autoDeleteQueue_AuthorRemove_Books_Request)
                    .to(directGenreExchange)
                    .with(GenreEvents.AUTHOR_REMOVE_REQUEST);
        }

        @Bean
        public Binding bindingSuggestion(DirectExchange directSuggestExchange,
                                    @Qualifier("autoDeleteQueue_Book_Suggestion_Created") Queue autoDeleteQueue_Book_Suggestion_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Book_Suggestion_Created)
                    .to(directSuggestExchange)
                    .with(BookEvents.BOOKSUGGESTED_CREATED);
        }

        @Bean
        public BookRabbitmqController receiver(BookService bookService, GenreService genreService, AuthorService authorService, @Qualifier("autoDeleteQueue_Book_Created") Queue autoDeleteQueue_Book_Created) {
            return new BookRabbitmqController(bookService, genreService, authorService);
        }
    }
}
