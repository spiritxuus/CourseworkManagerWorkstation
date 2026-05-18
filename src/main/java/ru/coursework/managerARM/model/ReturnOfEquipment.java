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
public class ReturnOfEquipment  {
    private Long returnId;
    private Long contract;
    private LocalDate returnDate;
    private String conditionDesc;
    private String conditionPhoto;
    private BigDecimal damageAmount;
    private BigDecimal deductionAmount;
    private Boolean repairRequired;
}
