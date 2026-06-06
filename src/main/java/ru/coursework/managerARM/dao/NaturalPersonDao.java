package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.dto.NaturalPersonView;
import ru.coursework.managerARM.model.NaturalPerson;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для доступа к данным о физических лицах.
 */
public interface NaturalPersonDao {

    /**
     * Добавляет новое физическое лицо.
     *
     * @param client объект {@link NaturalPerson}.
     */
    void add(NaturalPerson client);

    /**
     * Находит физическое лицо по идентификатору.
     *
     * @param id идентификатор.
     * @return {@code Optional}.
     */
    Optional<NaturalPerson> getById(Long id);

    /**
     * Возвращает список всех физических лиц.
     *
     * @return список.
     */
    List<NaturalPerson> getAll();

    /**
     * Возвращает список физических лиц в виде DTO {@link NaturalPersonView} для таблиц.
     *
     * @return список представлений.
     */
    List<NaturalPersonView> getAllViews();

    /**
     * Обновляет данные физического лица.
     *
     * @param client объект с новыми данными.
     */
    void update(NaturalPerson client);

    /**
     * Удаляет физическое лицо.
     *
     * @param id идентификатор.
     */
    void delete(Long id);
}
