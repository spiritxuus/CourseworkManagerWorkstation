package ru.coursework.managerARM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long clientId;
    private Long naturalPersonId;
    private Long legalPersonId;
    private String status;
}
