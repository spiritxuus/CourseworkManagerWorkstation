package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.Reservation;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с бронированиями оборудования.
 */
public interface ReservationDao {

    /**
     * Добавляет новое бронирование.
     *
     * @param reservation объект бронирования.
     */
    void add(Reservation reservation);

    /**
     * Находит бронирование по его идентификатору.
     *
     * @param id идентификатор бронирования.
     * @return {@code Optional}.
     */
    Optional<Reservation> getById(Long id);

    /**
     * Возвращает список всех бронирований.
     *
     * @return список.
     */
    List<Reservation> getAll();

    /**
     * Возвращает список бронирований в виде DTO {@link ReservationView} для отображения в таблицах.
     *
     * @return список представлений.
     */
    List<ReservationView> getAllViews();

    /**
     * Обновляет данные бронирования.
     *
     * @param reservation объект с изменениями.
     */
    void update(Reservation reservation);

    /**
     * Удаляет бронирование по идентификатору.
     *
     * @param id идентификатор бронирования.
     */
    void delete(Long id);
}
