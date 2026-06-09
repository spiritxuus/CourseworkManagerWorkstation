package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.model.Address;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным сущности {@link Address}.
 * Определяет стандартные CRUD-операции и дополнительные методы выборки.
 */
public interface AddressDao {

    /**
     * Добавляет новый адрес в базу данных.
     *
     * @param address объект адреса, который необходимо сохранить (без заполненного {@code addressId}).
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    Long add(Address address);

    /**
     * Находит адрес по его уникальному идентификатору.
     *
     * @param id идентификатор адреса.
     * @return {@code Optional}, содержащий найденный адрес, или пустой {@code Optional}, если адрес не найден.
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    Optional<Address> getById(Long id);

    /**
     * Возвращает список всех адресов.
     *
     * @return список всех адресов (может быть пустым, если ни одного адреса не добавлено).
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    List<Address> getAll();

    /**
     * Возвращает список всех адресов в виде представления {@link AddressView} для удобного отображения в таблицах.
     *
     * @return список представлений адресов.
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    List<AddressView> getAllViews();

    /**
     * Обновляет данные существующего адреса.
     *
     * @param address объект с обновлёнными данными (должен содержать корректный {@code addressId}).
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    void update(Address address);

    /**
     * Удаляет адрес по его идентификатору.
     *
     * @param id идентификатор адреса, подлежащего удалению.
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    void delete(Long id);
}

