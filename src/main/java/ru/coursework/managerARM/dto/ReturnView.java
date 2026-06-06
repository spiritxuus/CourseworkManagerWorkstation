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
public class ReturnView {
    private Long returnId;
    private Long contractId;
    private String contractClientName;
    private String contractEquipmentName;
    private LocalDate returnDate;
    private String conditionDesc;
    private String conditionPhoto;
    private BigDecimal damageAmount;
    private BigDecimal deductionAmount;
    private Boolean repairRequired;
}
