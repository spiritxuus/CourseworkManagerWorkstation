package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.ClientDao;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.model.Client;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {
    private static final String INSERT = SqlProvider.get("sql.client_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.client_selectById");

    private static final String SELECT = SqlProvider.get("sql.client_select");

    private static final String UPDATE = SqlProvider.get("sql.client_update");

    private static final String DELETE = SqlProvider.get("sql.client_delete");

    private static final String SELECT_VIEWS = SqlProvider.get("sql.client_selectViews");

    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);

    protected List<Client> mapper(ResultSet rs){
        List<Client> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Client(rs.getLong("client_id"),
                        rs.getLong("natural_person_id"),
                        rs.getLong("legal_person_id")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
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
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<Client> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(rs.getLong("client_id"),
                            rs.getLong("natural_person_id"),
                            rs.getLong("legal_person_id")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
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
            logger.error("SQL error", e);
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
                        rs.getLong("natural_person_id"),
                        rs.getLong("legal_person_id"),
                        rs.getString("client_type"),
                        rs.getString("client_name"),
                        rs.getString("client_phone"),
                        rs.getString("client_email"),
                        rs.getString("client_address")
                ));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
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
