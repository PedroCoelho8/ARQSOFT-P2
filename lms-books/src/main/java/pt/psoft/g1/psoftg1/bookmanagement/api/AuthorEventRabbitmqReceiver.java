package pt.psoft.g1.psoftg1.bookmanagement.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewAMQP;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AuthorEventRabbitmqReceiver {

    private final AuthorService authorService;



}