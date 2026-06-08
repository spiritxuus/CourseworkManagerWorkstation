package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.LegalPersonDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class LegalPersonDaoImpl implements LegalPersonDao {
    private static final String INSERT = SqlProvider.get("sql.legalPerson_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.legalPerson_selectById");

    private static final String SELECT = SqlProvider.get("sql.legalPerson_select");

    private static final String UPDATE = SqlProvider.get("sql.legalPerson_update");

    private final static String DELETE = SqlProvider.get("sql.legalPerson_delete");

    private static final Logger logger = LoggerFactory.getLogger(LegalPersonDaoImpl.class);

    protected List<LegalPerson> mapper(ResultSet rs){
        List<LegalPerson> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new LegalPerson(rs.getLong("legal_person_id"),
                        rs.getString("company_name"),
                        rs.getString("inn"),
                        rs.getString("kpp"),
                        rs.getString( "ogrn"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getLong("address"),
                        rs.getLong("contact_person")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(LegalPerson legalPerson) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setString(1, legalPerson.getCompanyName());
            statement.setString(2, legalPerson.getInn());
            statement.setString(3, legalPerson.getKpp());
            statement.setString(4, legalPerson.getOgrn());
            statement.setString(5, legalPerson.getPhone());
            statement.setString(6, legalPerson.getEmail());
            statement.setLong(7, legalPerson.getAddress());
            statement.setLong(8, legalPerson.getContactPerson());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<LegalPerson> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new LegalPerson(rs.getLong("legal_person_id"),
                            rs.getString("company_name"),
                            rs.getString("inn"),
                            rs.getString("kpp"),
                            rs.getString( "ogrn"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("address"),
                            rs.getLong("contact_person")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<LegalPerson> getAll() {
        List<LegalPerson> list = new ArrayList<>();
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
    public void update(LegalPerson legalPerson) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setString(1, legalPerson.getCompanyName());
            statement.setString(2, legalPerson.getInn());
            statement.setString(3, legalPerson.getKpp());
            statement.setString(4, legalPerson.getOgrn());
            statement.setString(5, legalPerson.getPhone());
            statement.setString(6, legalPerson.getEmail());
            statement.setLong(7, legalPerson.getAddress());
            statement.setLong(8, legalPerson.getContactPerson());
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
