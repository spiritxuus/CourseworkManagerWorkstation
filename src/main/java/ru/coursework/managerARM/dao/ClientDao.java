package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.model.Client;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным сущности {@link Client}.
 * Обеспечивает выполнение операций с клиентами (физическими и юридическими лицами).
 */
public interface ClientDao {

    /**
     * Добавляет нового клиента в систему.
     *
     * @param client объект клиента (поля {@code naturalPersonId} и {@code legalPersonId}
     *               должны ссылаться на существующие записи в соответствующих таблицах).
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса.
     */
    Long add(Client client);

    /**
     * Находит клиента по его идентификатору.
     *
     * @param id уникальный идентификатор клиента.
     * @return {@code Optional} с клиентом, либо пустой {@code Optional}, если клиент не найден.
     */
    Optional<Client> getById(Long id);

    /**
     * Возвращает список всех клиентов.
     *
     * @return список клиентов (может быть пустым).
     */
    List<Client> getAll();

    /**
     * Возвращает список всех клиентов в виде DTO {@link ClientView} для отображения в интерфейсе,
     * объединяя данные из связанных таблиц (физическое/юридическое лицо).
     *
     * @return список представлений клиентов.
     */
    List<ClientView> getAllViews();

    /**
     * Обновляет информацию о клиенте.
     *
     * @param client объект с обновлёнными данными (должен содержать корректный {@code clientId}).
     */
    void update(Client client);

    /**
     * Удаляет клиента по идентификатору.
     *
     * @param id идентификатор клиента.
     */
    void delete(Long id);
}
