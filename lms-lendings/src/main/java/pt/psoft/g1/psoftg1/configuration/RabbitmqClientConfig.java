package pt.psoft.g1.psoftg1.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import pt.psoft.g1.psoftg1.shared.model.BookEvents;


@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("LMS.lendings");
    }

    @Bean
    public DirectExchange directBookExchange() {
        return new DirectExchange("LMS.books");
    }

    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_Books_Lendings_Created")
        public Queue autoDeleteQueue_Book_Created() {

            System.out.println("autoDeleteQueue_Books_Lendings_Created created!");
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


        @Bean
        public Binding bindingBook(DirectExchange directBookExchange,
                                @Qualifier("autoDeleteQueue_Books_Lendings_Created") Queue autoDeleteQueue_Book_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Book_Created)
                    .to(directBookExchange)
                    .with(BookEvents.BOOK_CREATED);
        }

/*
        @Bean
        public LendingRabbitmqController receiver(BookService bookService, @Qualifier("autoDeleteQueue_Book_Created") Queue autoDeleteQueue_Book_Created) {
            return new BookRabbitmqController(bookService);
        }

 */
    }
}
