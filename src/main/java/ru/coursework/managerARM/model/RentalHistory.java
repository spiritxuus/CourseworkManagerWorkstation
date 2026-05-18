package ru.coursework.managerARM.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalHistory {
    private Long historyId;
    private Long contract;
    private LocalDate eventDate;
    private String eventType;
    private String details;
}
