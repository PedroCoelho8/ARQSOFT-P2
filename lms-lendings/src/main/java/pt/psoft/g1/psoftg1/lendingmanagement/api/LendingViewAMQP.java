package pt.psoft.g1.psoftg1.lendingmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Schema(description = "A Lending form AMQP communication")
@NoArgsConstructor
public class LendingViewAMQP {

    @NotNull
    private String lendingNumber;

    @NotNull
    private String bookTitle;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate limitDate;

    private LocalDate returnedDate;

    private Integer daysUntilReturn;

    private Integer daysOverdue;

    private Integer fineValueInCents;

    public LendingViewAMQP(String lendingNumber, String bookTitle, LocalDate startDate, LocalDate returnedDate, LocalDate limitDate, Integer daysUntilReturn, Integer daysOverdue, Integer fineValueInCents) {
        this.lendingNumber = lendingNumber;
        this.bookTitle = bookTitle;
        this.startDate = startDate;
        this.returnedDate = returnedDate;
        this.limitDate = limitDate;
        this.daysUntilReturn = daysUntilReturn;
        this.daysOverdue = daysOverdue;
        this.fineValueInCents = fineValueInCents;
    }
}
