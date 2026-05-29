package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.model.EquipmentCategory;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface EquipmentDao {
    void add(Equipment equipment);
    Optional<Equipment> getById(Long id);
    List<Equipment> getAll();
    List<EquipmentCategoryView> getCategory();
    void update(Equipment equipment);
    void delete(Long id);
}
