package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    void add(Reservation reservation);
    Optional<Reservation> getById(Long id);
    List<Reservation> getAll();
    void update(Reservation reservation);
    void delete(Long id);
}
