package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Payment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с платежами.
 */
public interface PaymentDao {

    /**
     * Добавляет запись о платеже.
     *
     * @param payment объект платежа.
     */
    void add(Payment payment);

    /**
     * Находит платеж по его идентификатору.
     *
     * @param id идентификатор платежа.
     * @return {@code Optional}.
     */
    Optional<Payment> getById(Long id);

    /**
     * Находит платеж по идентификатору договора аренды.
     *
     * @param id идентификатор договора (внешний ключ).
     * @return {@code Optional} с платежом, если он существует для указанного договора.
     */
    Optional<Payment> getByContract(Long id);

    /**
     * Возвращает список всех платежей.
     *
     * @return список.
     */
    List<Payment> getAll();

    /**
     * Обновляет данные платежа.
     *
     * @param payment объект с обновлениями.
     */
    void update(Payment payment);

    /**
     * Удаляет платеж по идентификатору.
     *
     * @param id идентификатор платежа.
     */
    void delete(Long id);
}
