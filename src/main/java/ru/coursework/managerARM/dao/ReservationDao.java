package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    void add(Reservation reservation);
    Optional<Reservation> getById(Long id);
    List<Reservation> getAll();
    List<ReservationView> getAllViews();
    void update(Reservation reservation);
    void delete(Long id);
}
