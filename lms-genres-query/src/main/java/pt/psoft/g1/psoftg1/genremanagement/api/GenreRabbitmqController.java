package pt.psoft.g1.psoftg1.genremanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GenreRabbitmqController {

    @Autowired
    private final GenreRepository genreRepository;

    @RabbitListener(queues = "#{autoDeleteQueue_GenreQuery_Created.name}")
    public void receiveGenreCreatedMsg(Message msg) {
        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            GenreViewAMQP genreViewAMQP = objectMapper.readValue(jsonReceived, GenreViewAMQP.class);
            String genreName = genreViewAMQP.getGenre();

            System.out.println(" [x] Received Genre Created by AMQP: " + msg + ".");
            try {
                Genre genre = new Genre(genreName);
                genreRepository.save(genre);
                System.out.println(" [x] New Genre inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Genre already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving genre event from AMQP: '" + ex.getMessage() + "'");
        }
    }
}
