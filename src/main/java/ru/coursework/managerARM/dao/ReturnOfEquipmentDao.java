package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.dto.ReturnView;
import ru.coursework.managerARM.model.ReturnOfEquipment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с возвратом оборудования.
 */
public interface ReturnOfEquipmentDao {

    /**
     * Добавляет новое бронирование.
     *
     * @param returnOfEquipment объект возврата.
     */
    void add(ReturnOfEquipment returnOfEquipment);

    /**
     * Находит возврат по его идентификатору.
     *
     * @param id идентификатор возврата.
     * @return {@code Optional}.
     */
    Optional<ReturnOfEquipment> getById(Long id);

    /**
     * Возвращает список всех возвратов оборудования.
     *
     * @return список.
     */
    List<ReturnOfEquipment> getAll();

    /**
     * Возвращает список возвратов оборудования в виде DTO {@link RentalContractViewCb} для отображения в Combo Box.
     *
     * @return список представлений.
     */
    List<RentalContractViewCb> getContract();

    /**
     * Возвращает список возвратов оборудования в виде DTO {@link ReturnView} для отображения в таблицах.
     *
     * @return список представлений.
     */
    List<ReturnView> getAllViews();

    /**
     * Обновляет данные возврата.
     *
     * @param returnOfEquipment объект с изменениями.
     */
    void update(ReturnOfEquipment returnOfEquipment);

    /**
     * Удаляет возврат по идентификатору.
     *
     * @param id идентификатор возврата.
     */
    void delete(Long id);
}
