package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.LegalPersonDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.LegalPerson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LegalPersonDaoImpl implements LegalPersonDao {
    private static final String INSERT =
            "INSERT INTO my_schema.legal_person (company_name, inn, kpp, ogrn, " +
                    "phone, email, address, contact_person) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT legal_person_id, company_name, inn, kpp, ogrn, " +
                    "phone, email, address, contact_person " +
                    "FROM my_schema.legal_person WHERE legal_person_id = ?";

    private static final String SELECT =
            "SELECT legal_person_id, company_name, inn, kpp, ogrn, " +
                    "phone, email, address, contact_person " +
                    "FROM my_schema.legal_person";

    private static final String UPDATE =
            "UPDATE my_schema.legal_person " +
                    "SET company_name = ?, inn = ?, kpp = ?, ogrn = ?, " +
                    "phone = ?, email = ?, address = ?, contact_person = ? " +
                    "WHERE legal_person_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.legal_person WHERE legal_person_id = ?";

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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
