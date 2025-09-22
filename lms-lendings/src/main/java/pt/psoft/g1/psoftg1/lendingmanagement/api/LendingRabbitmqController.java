package pt.psoft.g1.psoftg1.lendingmanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewAMQP;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class LendingRabbitmqController {

    @Autowired
    private final LendingService lendingService;

    @Autowired
    private final BookService bookService;

    @RabbitListener(queues = "#{autoDeleteQueue_Lendings_Created.name}")
    public void receiveLendingCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            BookViewAMQP bookViewAMQP = objectMapper.readValue(jsonReceived, BookViewAMQP.class);

            System.out.println(" [x] Received Book Created by AMQP: " + msg + ".");
            try {
                bookService.create(bookViewAMQP);
                System.out.println(" [x] New book inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Book already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

}

