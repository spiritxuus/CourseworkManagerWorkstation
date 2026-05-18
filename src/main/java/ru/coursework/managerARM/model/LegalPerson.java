package ru.coursework.managerARM.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegalPerson {
    private Long legalPersonId;
    private String companyName;
    private String inn;
    private String kpp;
    private String ogrn;
    private String phone;
    private String email;
    private Long address;
    private Long contactPerson;
}
