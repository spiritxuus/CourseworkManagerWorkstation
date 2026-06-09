package ru.coursework.managerARM.dao;


import ru.coursework.managerARM.model.LegalPerson;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным о юридических лицах.
 * Содержит базовые методы для работы с записями юридических лиц.
 */
public interface LegalPersonDao {

    /**
     * Добавляет новое юридическое лицо.
     *
     * @param client объект {@link LegalPerson}.
     */
    Long add(LegalPerson client);

    /**
     * Находит юридическое лицо по идентификатору.
     *
     * @param id идентификатор.
     * @return {@code Optional} с найденным объектом.
     */
    Optional<LegalPerson> getById(Long id);

    /**
     * Возвращает список всех юридических лиц.
     *
     * @return список.
     */
    List<LegalPerson> getAll();

    /**
     * Обновляет данные юридического лица.
     *
     * @param client объект с обновлёнными полями.
     */
    void update(LegalPerson client);

    /**
     * Удаляет юридическое лицо по идентификатору.
     *
     * @param id идентификатор.
     */
    void delete(Long id);
}
