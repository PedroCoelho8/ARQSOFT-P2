package CDC.producer;

import au.com.dius.pact.core.support.Auth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;

public class AuthorMessageBuilder {

    private ObjectMapper mapper = new ObjectMapper();
    private AuthorViewAMQP authorViewAMQP;

    public AuthorMessageBuilder withAuthor(AuthorViewAMQP authorViewAMQP) {
        this.authorViewAMQP = authorViewAMQP;
        return this;
    }

    public Message<String> build() throws JsonProcessingException {
        return MessageBuilder.withPayload(this.mapper.writeValueAsString(this.authorViewAMQP))
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }
}
