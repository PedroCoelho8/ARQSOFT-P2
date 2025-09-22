package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.publishers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQPMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.publishers.LendingEventsPublisher;
import pt.psoft.g1.psoftg1.shared.model.LendingEvents;

@Service
@RequiredArgsConstructor
public class LendingEventsRabbitmqPublisherImpl implements LendingEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange direct;
    @Autowired
    private final LendingViewAMQPMapper lendingViewAMQPMapper;


    @Override
    public LendingViewAMQP sendLendingCreated(Lending lending) {
        return null;
    //    return sendLendingEvent(lending, lending.getLendingNumber(), LendingEvents.LENDING_CREATED);
    }

    /*
    private LendingViewAMQP sendLendingEvent(Lending lending, String lendingNumber, String lendingEventType) {

        System.out.println("Send Lending event to AMQP Broker: " + lending.getLendingNumber());

        try {
            LendingViewAMQP lendingViewAMQP = lendingViewAMQPMapper.toLendingViewAMQP(lending);
            lendingViewAMQP.setLendingNumber(lendingNumber);

            ObjectMapper objectMapper = new ObjectMapper();
            String lendingViewAMQPinString = objectMapper.writeValueAsString(lendingViewAMQP);

            this.template.convertAndSend(direct.getName(), lendingEventType, lendingViewAMQPinString);

            return lendingViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending lending event: '" + ex.getMessage() + "'");

            return null;
        }
    }

     */
}
