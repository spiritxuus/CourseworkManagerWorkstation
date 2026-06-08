package ru.coursework.managerARM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientView {
    private Long clientId;
    private Long naturalPersonId;
    private Long legalPersonId;
    private String clientType;
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private String clientAddress;
}
