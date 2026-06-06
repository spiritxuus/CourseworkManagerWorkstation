package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.model.RentalContract;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным договоров аренды.
 */
public interface RentalContractDao {

    /**
     * Добавляет новый договор аренды.
     *
     * @param rentalContract объект договора.
     */
    void add(RentalContract rentalContract);

    /**
     * Находит договор по его идентификатору.
     *
     * @param id идентификатор договора.
     * @return {@code Optional}.
     */
    Optional<RentalContract> getById(Long id);

    /**
     * Возвращает список всех договоров.
     *
     * @return список договоров.
     */
    List<RentalContract> getAll();

    /**
     * Возвращает список договоров в виде DTO {@link RentalContractView} для таблицы.
     *
     * @return список представлений договоров.
     */
    List<RentalContractView> getAllViews();

    /**
     * Обновляет данные договора.
     *
     * @param rentalContract объект с новыми данными.
     */
    void update(RentalContract rentalContract);

    /**
     * Удаляет договор по идентификатору.
     *
     * @param id идентификатор договора.
     */
    void delete(Long id);
}
