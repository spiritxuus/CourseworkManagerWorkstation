package ru.coursework.managerARM.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long addressId;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String apartment;
}
