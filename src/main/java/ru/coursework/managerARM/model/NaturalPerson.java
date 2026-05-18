package ru.coursework.managerARM.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaturalPerson {
    private Long naturalPersonId;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthDate;
    private String gender;
    private String passportSeries;
    private String passportNumber;
    private String phone;
    private String email;
    private Long address;
}
