package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDao {
    void add(Address address);
    Optional<Address> getById(Long id);
    List<Address> getAll();
    List<String> getAllConcat();
    void update(Address address);
    void delete(Long id);
}
