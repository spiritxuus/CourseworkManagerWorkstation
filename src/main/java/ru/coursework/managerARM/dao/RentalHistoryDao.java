package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.RentalHistory;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с историей аренды.
 */
public interface RentalHistoryDao {

    /**
     * Добавляет запись в историю аренды.
     *
     * @param rentalHistory объект записи истории.
     */
    void add(RentalHistory rentalHistory);

    /**
     * Находит запись истории по её идентификатору.
     *
     * @param id идентификатор записи.
     * @return {@code Optional}.
     */
    Optional<RentalHistory> getById(Long id);

    /**
     * Возвращает всю историю аренды.
     *
     * @return список записей.
     */
    List<RentalHistory> getAll();

    /**
     * Обновляет запись истории.
     *
     * @param rentalHistory объект с изменениями.
     */
    void update(RentalHistory rentalHistory);

    /**
     * Удаляет запись из истории по идентификатору.
     *
     * @param id идентификатор записи.
     */
    void delete(Long id);
}
