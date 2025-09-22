package pt.psoft.g1.psoftg1.lendingmanagement.publishers;

import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

public interface LendingEventsPublisher {

    LendingViewAMQP sendLendingCreated(Lending lending);

}
