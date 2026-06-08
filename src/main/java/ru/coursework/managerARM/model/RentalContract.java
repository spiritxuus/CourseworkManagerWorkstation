package ru.coursework.managerARM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalContract {
    private Long contractId;
    private Long reservationId;
    private Long clientId;
    private LocalDate issueDate;
    private LocalDate plannedReturnDate;
    private LocalDate actualReturnDate;
    private BigDecimal depositAmount;
    private BigDecimal totalAmount;
    private Integer status;
    private String issueConditionDesc;
    private String issueConditionPhoto;
}
