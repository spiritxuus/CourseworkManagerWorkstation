package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.dto.ReturnView;
import ru.coursework.managerARM.model.ReturnOfEquipment;

import java.util.List;
import java.util.Optional;

public interface ReturnOfEquipmentDao {
    void add(ReturnOfEquipment returnOfEquipment);
    Optional<ReturnOfEquipment> getById(Long id);
    List<ReturnOfEquipment> getAll();
    List<RentalContractViewCb> getContract();
    List<ReturnView> getAllViews();
    void update(ReturnOfEquipment returnOfEquipment);
    void delete(Long id);
}
