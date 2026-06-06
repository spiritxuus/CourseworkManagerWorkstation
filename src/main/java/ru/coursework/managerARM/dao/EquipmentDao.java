package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.model.Equipment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным об оборудовании.
 * Позволяет управлять единицами прокатного инвентаря.
 */
public interface EquipmentDao {

    /**
     * Добавляет новую единицу оборудования.
     *
     * @param equipment объект оборудования (без заполненного {@code equipmentId}).
     */
    void add(Equipment equipment);

    /**
     * Находит оборудование по его идентификатору.
     *
     * @param id идентификатор оборудования.
     * @return {@code Optional} с найденным оборудованием.
     */
    Optional<Equipment> getById(Long id);

    /**
     * Возвращает список всего оборудования.
     *
     * @return список всех единиц оборудования.
     */
    List<Equipment> getAll();

    /**
     * Возвращает список категорий оборудования для выпадающих списков.
     *
     * @return список представлений категорий (например, для ComboBox).
     */
    List<EquipmentCategoryView> getCategory();

    /**
     * Обновляет данные об оборудовании.
     *
     * @param equipment объект с обновлённой информацией (должен содержать {@code equipmentId}).
     */
    void update(Equipment equipment);

    /**
     * Удаляет оборудование по идентификатору.
     *
     * @param id идентификатор оборудования.
     */
    void delete(Long id);
}
