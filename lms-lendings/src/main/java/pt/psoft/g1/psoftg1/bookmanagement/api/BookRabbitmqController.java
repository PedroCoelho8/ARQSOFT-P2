package pt.psoft.g1.psoftg1.bookmanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class BookRabbitmqController {

    @Autowired
    private final BookService bookService;

    @RabbitListener(queues = "#{autoDeleteQueue_Books_Lendings_Created.name}")
    public void receiveBookCreatedMsg(Message msg) {

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

    /*
    @RabbitListener(queues = "#{autoDeleteQueue_Book_Updated.name}")
    public void receiveBookUpdated(Message msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            BookViewAMQP bookViewAMQP = objectMapper.readValue(jsonReceived, BookViewAMQP.class);

            System.out.println(" [x] Received Book Updated by AMQP: " + msg + ".");
            try {
                bookService.update(bookViewAMQP);
                System.out.println(" [x] Book updated from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Book does not exists or wrong version. Nothing stored.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving book event from AMQP: '" + ex.getMessage() + "'");
        }
    }

     */
}