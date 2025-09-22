package pt.psoft.g1.psoftg1.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pt.psoft.g1.psoftg1.shared.model.GenreEvents;

@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {

    @Bean
    public DirectExchange directGenreExchange() {
        return new DirectExchange("LMS.genres");
    }

    @Bean
    public DirectExchange directAuthorExchange() {
        return new DirectExchange("LMS.authors");
    }

    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_Genre_Created")
        public Queue autoDeleteQueue_Genre_Created() {
            System.out.println("autoDeleteQueue_Genre_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Genre_Request")
        public Queue autoDeleteQueue_Genre_Request() {
            System.out.println("autoDeleteQueue_Genre_Request created!");
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingGenre(DirectExchange directGenreExchange,
                                    @Qualifier("autoDeleteQueue_Genre_Created") Queue autoDeleteQueue_Genre_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Genre_Created)
                    .to(directGenreExchange)
                    .with(GenreEvents.GENRE_CREATED);
        }

        @Bean
        public Binding bindingGenreRequest(DirectExchange directAuthorExchange,
                                    @Qualifier("autoDeleteQueue_Genre_Request") Queue autoDeleteQueue_Genre_Request) {
            return BindingBuilder.bind(autoDeleteQueue_Genre_Request)
                    .to(directAuthorExchange)
                    .with(GenreEvents.GENRE_REQUEST);
        }

    }
}