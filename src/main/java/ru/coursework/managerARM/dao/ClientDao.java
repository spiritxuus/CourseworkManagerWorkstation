package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientDao {
    void add(Client client);
    Optional<Client> getById(Long id);
    List<Client> getAll();
    List<ClientView> getAllViews();
    void update(Client client);
    void delete(Long id);
}
