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
public class RentalContractViewCb {
    private Long contractId;
    private Long reservationId;
    private String clientName;
    private LocalDate issueDate;

    @Override
    public String toString() {
        return clientName + " | " + issueDate;
    }
}
