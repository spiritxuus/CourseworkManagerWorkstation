package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.RentalContract;

import java.util.List;
import java.util.Optional;

public interface RentalContractDao {
    void add(RentalContract rentalContract);
    Optional<RentalContract> getById(Long id);
    List<RentalContract> getAll();
    void update(RentalContract rentalContract);
    void delete(Long id);
}
