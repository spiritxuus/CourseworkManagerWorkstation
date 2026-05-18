package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.ClientDao;
import ru.coursework.managerARM.model.Client;
import ru.coursework.managerARM.util.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {
    private static final String INSERT =
            "INSERT INTO my_schema.client (natural_person_id, legal_person_id, status) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT client_id, natural_person_id, legal_person_id, status FROM my_schema.client WHERE client_id = ?";

    private static final String SELECT =
            "SELECT client_id, natural_person_id, legal_person_id, status FROM my_schema.client";

    private static final String UPDATE =
            "UPDATE my_schema.client " +
                    "SET natural_person_id = ?, legal_person_id = ?, status = ? " +
                    "WHERE client_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.client WHERE client_id = ?";

    protected List<Client> mapper(ResultSet rs){
        List<Client> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Client(rs.getLong("client_id"),
                        rs.getObject("natural_person_id", Long.class),
                        rs.getObject("legal_person_id", Long.class),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(Client client) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, client.getNaturalPersonId());
            statement.setLong(2, client.getLegalPersonId());
            statement.setString(3, client.getStatus());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Client> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(rs.getLong("client_id"),
                            rs.getObject("natural_person_id", Long.class),
                            rs.getObject("legal_person_id", Long.class),
                            rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
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
    public void update(Client client) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, client.getNaturalPersonId());
            statement.setLong(2, client.getLegalPersonId());
            statement.setString(3, client.getStatus());
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
