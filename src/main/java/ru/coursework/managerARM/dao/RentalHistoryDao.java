package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.RentalHistory;

import java.util.List;
import java.util.Optional;

public interface RentalHistoryDao {
    void add(RentalHistory rentalHistory);
    Optional<RentalHistory> getById(Long id);
    List<RentalHistory> getAll();
    void update(RentalHistory rentalHistory);
    void delete(Long id);
}
