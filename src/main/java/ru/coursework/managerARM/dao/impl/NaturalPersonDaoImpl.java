package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.NaturalPersonDao;
import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.dto.NaturalPersonView;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.NaturalPerson;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class NaturalPersonDaoImpl implements NaturalPersonDao {
    private static final String INSERT =
            "INSERT INTO my_schema.natural_person (name, surname, patronymic, birth_date, " +
                    "gender, passport_series, passport_number, phone, email, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT natural_person_id, name, surname, patronymic, birth_date, " +
                    "gender, passport_series, passport_number, phone, email, address " +
                    "FROM my_schema.natural_person WHERE natural_person_id = ?";

    private static final String SELECT =
            "SELECT natural_person_id, name, surname, patronymic, birth_date, " +
                    "gender, passport_series, passport_number, phone, email, address " +
                    "FROM my_schema.natural_person";

    private static final String UPDATE =
            "UPDATE my_schema.natural_person " +
                    "SET name = ?, surname = ?, patronymic = ?, birth_date = ?, " +
                    "gender = ?, passport_series = ?, passport_number = ?, " +
                    "phone = ?, email = ?, address = ? " +
                    "WHERE natural_person_id = ?";

    private static final String DELETE =
            "DELETE FROM my_schema.natural_person WHERE natural_person_id = ?";


    private static final String SELECT_VIEWS =
            "SELECT natural_person_id, name, surname, patronymic, phone, email " +
                    "FROM my_schema.natural_person " +
                    "ORDER BY surname, name, patronymic";

    private static final Logger logger = LoggerFactory.getLogger(NaturalPersonDaoImpl.class);


    protected List<NaturalPerson> mapper(ResultSet rs){
        List<NaturalPerson> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new NaturalPerson(rs.getLong("natural_person_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getDate( "birth_date").toLocalDate(),
                        rs.getBoolean("gender"),
                        rs.getString("passport_series"),
                        rs.getString("passport_number"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getLong("address")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(NaturalPerson naturalPerson) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setString(1, naturalPerson.getName());
            statement.setString(2, naturalPerson.getSurname());
            statement.setString(3, naturalPerson.getPatronymic());
            statement.setDate(4, Date.valueOf(naturalPerson.getBirthDate()));
            statement.setBoolean(5, naturalPerson.getGender());
            statement.setString(6, naturalPerson.getPassportSeries());
            statement.setString(7, naturalPerson.getPassportNumber());
            statement.setString(8, naturalPerson.getPhone());
            statement.setString(9, naturalPerson.getEmail());
            statement.setLong(10, naturalPerson.getAddress());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<NaturalPerson> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new NaturalPerson(rs.getLong("natural_person_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("patronymic"),
                            rs.getDate( "birth_date").toLocalDate(),
                            rs.getBoolean("gender"),
                            rs.getString("passport_series"),
                            rs.getString("passport_number"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("address")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }


    @Override
    public List<NaturalPerson> getAll() {
        List<NaturalPerson> list = new ArrayList<>();
        ResultSet rs = null;
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(SELECT)) {
            rs = statement.executeQuery();
            list = mapper(rs);
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public List<NaturalPersonView> getAllViews() {
        List<NaturalPersonView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                list.add(new NaturalPersonView(
                        rs.getLong("natural_person_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            logger.error("SQL error", e);
        }

        return list;
    }

    @Override
    public void update(NaturalPerson naturalPerson) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setString(1, naturalPerson.getName());
            statement.setString(2, naturalPerson.getSurname());
            statement.setString(3, naturalPerson.getPatronymic());
            statement.setDate(4, Date.valueOf(naturalPerson.getBirthDate()));
            statement.setBoolean(5, naturalPerson.getGender());
            statement.setString(6, naturalPerson.getPassportSeries());
            statement.setString(7, naturalPerson.getPassportNumber());
            statement.setString(8, naturalPerson.getPhone());
            statement.setString(9, naturalPerson.getEmail());
            statement.setLong(10, naturalPerson.getAddress());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
    }
}
