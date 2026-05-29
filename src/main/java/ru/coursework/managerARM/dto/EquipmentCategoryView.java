package ru.coursework.managerARM.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentCategoryView {
    private Long categoryId;
    private String categoryName;

    @Override
    public String toString() {
        return categoryName;
    }
}
