package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.model.RentalContract;

import java.util.List;
import java.util.Optional;

public interface RentalContractDao {
    void add(RentalContract rentalContract);
    Optional<RentalContract> getById(Long id);
    List<RentalContract> getAll();
    List<RentalContractView> getAllViews();
    void update(RentalContract rentalContract);
    void delete(Long id);
}
