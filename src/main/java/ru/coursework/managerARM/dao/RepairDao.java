package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Repair;

import java.util.List;
import java.util.Optional;

public interface RepairDao {
    void add(Repair repair);
    Optional<Repair> getById(Long id);
    List<Repair> getAll();
    void update(Repair repair);
    void delete(Long id);
}
