package pt.psoft.g1.psoftg1.genremanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

import java.nio.charset.StandardCharsets;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenreRabbitmqController {

    @Autowired
    final private GenreService genreService;

    @RabbitListener(queues = "#{autoDeleteQueue_Genre_Created.name}")
    public void receiveGenreCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            GenreViewAMQP genreViewAMQP = objectMapper.readValue(jsonReceived, GenreViewAMQP.class);

            System.out.println(" [x] Received Genre Created by AMQP: " + msg + ".");
            try {
                genreService.create(genreViewAMQP);
                System.out.println(" [x] New Genre inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Genre already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving genre event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Genre_Request.name}")
    public void receiveGenreRequestMsg(String msg) {
            System.out.println(" [x] Received Genre Created by AMQP: " + msg + ".");
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                System.out.println("1");
                Map<String, Object> genreInfo = objectMapper.readValue(msg, Map.class);
                System.out.println("2");

                String genre = (String) genreInfo.get("genre");

                Long authorId = ((Number) genreInfo.get("authorId")).longValue();


                genreService.createGenre(genre, authorId);
                System.out.println(" [x] New Genre inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Genre already exists. No need to store it.");
            }
        }

}