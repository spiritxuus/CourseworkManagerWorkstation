package ru.coursework.managerARM.dao;


import ru.coursework.managerARM.model.LegalPerson;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LegalPersonDao {
    void add(LegalPerson client);
    Optional<LegalPerson> getById(Long id);
    List<LegalPerson> getAll();
    void update(LegalPerson client);
    void delete(Long id);
    // todo Дополнительные методы
}
