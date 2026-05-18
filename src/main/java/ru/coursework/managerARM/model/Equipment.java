package ru.coursework.managerARM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    private Long equipmentId;
    private Long category;
    private String name;
    private String manufacturer;
    private String model;
    private String inventoryNumber;
    private String serialNumber;
    private BigDecimal rentalPricePerDay;
    private BigDecimal depositAmount;
    private String conditionStatus;
    private Boolean requiresRepair;
    private String photo;
    private String description;
}
