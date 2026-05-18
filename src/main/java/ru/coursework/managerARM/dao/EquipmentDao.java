package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentDao {
    void add(Equipment equipment);
    Optional<Equipment> getById(Long id);
    List<Equipment> getAll();
    void update(Equipment equipment);
    void delete(Long id);
}
