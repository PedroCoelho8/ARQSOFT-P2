package pt.psoft.g1.psoftg1.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pt.psoft.g1.psoftg1.shared.model.AuthorEvents;
import pt.psoft.g1.psoftg1.shared.model.BookEvents;
import pt.psoft.g1.psoftg1.shared.model.GenreEvents;
import pt.psoft.g1.psoftg1.shared.model.SuggestEvents;

@Profile("!test")
@Configuration
public  class RabbitmqClientConfig {


    @Bean
    public DirectExchange directSuggestExchange() {
        return new DirectExchange("LMS.suggests");
    }


    private static class ReceiverConfig {

        @Bean(name = "autoDeleteQueue_Suggest_Created")
        public Queue autoDeleteQueue_Suggest_Created() {
            System.out.println("autoDeleteQueue_Suggest_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Book_Suggest_Created")
        public Queue autoDeleteQueue_Book_Suggest_Created() {
            System.out.println("autoDeleteQueue_Book_Suggest_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Author_Suggest_Created")
        public Queue autoDeleteQueue_Author_Suggest_Created() {
            System.out.println("autoDeleteQueue_Author_Suggest_Created created!");
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue_Genre_Suggest_Created")
        public Queue autoDeleteQueue_Genre_Suggest_Created() {
            System.out.println("autoDeleteQueue_Genre_Suggest_Created created!");
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingSuggest(DirectExchange directSuggestExchange,
                                    @Qualifier("autoDeleteQueue_Suggest_Created") Queue autoDeleteQueue_Suggest_Created) {
            return BindingBuilder.bind(autoDeleteQueue_Suggest_Created)
                    .to(directSuggestExchange)
                    .with(SuggestEvents.SUGGESTION_CREATED);
        }

    }
}