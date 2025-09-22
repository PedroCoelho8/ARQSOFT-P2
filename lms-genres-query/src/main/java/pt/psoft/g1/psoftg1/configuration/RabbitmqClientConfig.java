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

    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_GenreQuery_Created")
        public Queue autoDeleteQueue_GenreQuery_Created() {
            System.out.println("autoDeleteQueue_GenreQuery_Created created!");
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingGenre(DirectExchange directGenreExchange,
                                    @Qualifier("autoDeleteQueue_GenreQuery_Created") Queue autoDeleteQueue_GenreQuery_Created) {
            return BindingBuilder.bind(autoDeleteQueue_GenreQuery_Created)
                    .to(directGenreExchange)
                    .with(GenreEvents.GENRE_CREATED);
        }

    }
}