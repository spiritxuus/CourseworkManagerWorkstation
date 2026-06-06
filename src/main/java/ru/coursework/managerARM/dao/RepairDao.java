package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Repair;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным о ремонте оборудования.
 */
public interface RepairDao {

    /**
     * Добавляет запись о ремонте.
     *
     * @param repair объект ремонта.
     */
    void add(Repair repair);

    /**
     * Находит запись о ремонте по идентификатору.
     *
     * @param id идентификатор записи.
     * @return {@code Optional}.
     */
    Optional<Repair> getById(Long id);

    /**
     * Возвращает список всех записей о ремонтах.
     *
     * @return список.
     */
    List<Repair> getAll();

    /**
     * Обновляет информацию о ремонте.
     *
     * @param repair объект с обновлёнными данными.
     */
    void update(Repair repair);

    /**
     * Удаляет запись о ремонте.
     *
     * @param id идентификатор записи.
     */
    void delete(Long id);
}
