package ru.coursework.managerARM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaturalPersonView {
    private Long naturalPersonId;
    private String name;
    private String surname;
    private String patronymic;
    private String phone;
    private String email;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (name != null && !name.isBlank()) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(name);
        }

        if (surname != null && !surname.isBlank()) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(surname);
        }

        if (patronymic != null && !patronymic.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(patronymic);
        }
        else{
            sb.append(", ");
        }

        if (phone != null && !phone.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(phone);
        }

        if (email != null && !email.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(email);
        }
        else{
            sb.append(", ");
        }

        return sb.toString();
    }
}
