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
public class Payment {
    private Long paymentId;
    private Long contract;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private Integer paymentMethod;
}
