package ru.coursework.managerARM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressView {
    private Long addressId;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String apartment;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (country != null && !country.isBlank()){
            sb.append(country);
        }

        if (region != null && !region.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(region);
        }

        if (city != null && !city.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(city);
        }

        if (street != null && !street.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(street);
        }

        if (house != null && !house.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(house);
        }

        if (apartment != null && !apartment.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(apartment);
        }
        else{
            sb.append(", ");
        }

        return sb.toString();
    }
}
