package ru.coursework.managerARM.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repair {
    private Long repairId;
    private Long equipment;
    private Long contract;
    private LocalDate dateCreated;
    private String repairReason;
    private String repairStatus;
    private BigDecimal repairCost;
}
