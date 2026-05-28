package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.ClientDao;
import ru.coursework.managerARM.dto.ClientView;
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
            "INSERT INTO my_schema.client (natural_person_id, legal_person_id) VALUES (?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT client_id, natural_person_id, legal_person_id FROM my_schema.client WHERE client_id = ?";

    private static final String SELECT =
            "SELECT client_id, natural_person_id, legal_person_id FROM my_schema.client";

    private static final String UPDATE =
            "UPDATE my_schema.client " +
                    "SET natural_person_id = ?, legal_person_id = ? " +
                    "WHERE client_id = ?";

    private static final String DELETE =
            "DELETE FROM my_schema.client WHERE client_id = ?";

    private static final String SELECT_VIEWS =
            "SELECT " +
                    "c.client_id, " +
                    "c.natural_person_id, " +
                    "c.legal_person_id, " +
                    "CASE " +
                    "    WHEN c.natural_person_id IS NOT NULL THEN 'Физическое лицо' " +
                    "    ELSE 'Юридическое лицо' " +
                    "END AS client_type, " +
                    "CASE " +
                    "    WHEN c.natural_person_id IS NOT NULL THEN CONCAT_WS(' ', np.surname, np.name, np.patronymic) " +
                    "    ELSE lp.company_name " +
                    "END AS client_name, " +
                    "COALESCE(np.phone, lp.phone) AS client_phone, " +
                    "COALESCE(np.email, lp.email) AS client_email, " +
                    "CONCAT_WS(', ', a.country, a.region, a.city, a.street, a.house, a.apartment) AS client_address " +
                    "FROM my_schema.client c " +
                    "LEFT JOIN my_schema.natural_person np ON c.natural_person_id = np.natural_person_id " +
                    "LEFT JOIN my_schema.legal_person lp ON c.legal_person_id = lp.legal_person_id " +
                    "LEFT JOIN my_schema.address a ON a.address_id = COALESCE(np.address, lp.address)";

    protected List<Client> mapper(ResultSet rs){
        List<Client> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Client(rs.getLong("client_id"),
                        rs.getObject("natural_person_id", Long.class),
                        rs.getObject("legal_person_id", Long.class)));
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
            if (client.getNaturalPersonId() != null) {
                statement.setObject(1, client.getNaturalPersonId());
            } else {
                statement.setNull(1, java.sql.Types.BIGINT);
            }

            if (client.getLegalPersonId() != null) {
                statement.setObject(2, client.getLegalPersonId());
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }
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
                            rs.getObject("legal_person_id", Long.class)));
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
    public List<ClientView> getAllViews() {
        List<ClientView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                list.add(new ClientView(
                        rs.getLong("client_id"),
                        rs.getObject("natural_person_id", Long.class),
                        rs.getObject("legal_person_id", Long.class),
                        rs.getString("client_type"),
                        rs.getString("client_name"),
                        rs.getString("client_phone"),
                        rs.getString("client_email"),
                        rs.getString("client_address")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    @Override
    public void update(Client client) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            if (client.getNaturalPersonId() != null) {
                statement.setObject(1, client.getNaturalPersonId());
            } else {
                statement.setNull(1, java.sql.Types.BIGINT);
            }

            if (client.getLegalPersonId() != null) {
                statement.setObject(2, client.getLegalPersonId());
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }
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
