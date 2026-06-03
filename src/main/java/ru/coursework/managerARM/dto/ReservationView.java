package ru.coursework.managerARM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationView {
    private Long reservationId;
    private Long clientId;
    private String clientName;
    private String equipmentName;
    private LocalDate startDate;
    private LocalDate endDate;
}
