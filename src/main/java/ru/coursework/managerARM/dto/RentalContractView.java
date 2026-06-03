package ru.coursework.managerARM.dto;

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
public class RentalContractView {
    private long contractId;
    private long reservationId;
    private String clientName;
    private Long legalPersonId;
    private LocalDate issueDate;
    private LocalDate plannedReturnDate;
    private LocalDate actualReturnDate;
    private BigDecimal depositAmount;
    private BigDecimal totalAmount;
    private String status;
    private String issueConditionDesc;
    private String issueConditionPhoto;
}
