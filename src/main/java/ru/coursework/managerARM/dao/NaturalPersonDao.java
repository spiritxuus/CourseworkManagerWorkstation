package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.dto.NaturalPersonView;
import ru.coursework.managerARM.model.NaturalPerson;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NaturalPersonDao {
    void add(NaturalPerson client);
    Optional<NaturalPerson> getById(Long id);
    List<NaturalPerson> getAll();
    List<NaturalPersonView> getAllViews();
    void update(NaturalPerson client);
    void delete(Long id);
}
