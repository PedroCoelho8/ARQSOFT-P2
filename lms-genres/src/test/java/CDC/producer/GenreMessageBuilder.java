package CDC.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pt.psoft.g1.psoftg1.genremanagement.api.GenreViewAMQP;

public class GenreMessageBuilder {

    private ObjectMapper mapper = new ObjectMapper();
    private GenreViewAMQP genreViewAMQP;

    public GenreMessageBuilder withGenre(GenreViewAMQP genreViewAMQP) {
        this.genreViewAMQP = genreViewAMQP;
        return this;
    }

    public Message<String> build() throws JsonProcessingException {
        return MessageBuilder.withPayload(this.mapper.writeValueAsString(this.genreViewAMQP))
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }

}
