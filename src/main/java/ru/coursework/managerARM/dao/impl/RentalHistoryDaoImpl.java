package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.RentalHistoryDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.RentalHistory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalHistoryDaoImpl implements RentalHistoryDao {
    private static final String INSERT =
            "INSERT INTO my_schema.rental_history (contract, event_date, event_type, details) VALUES (?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT history_id, contract, event_date, event_type, details FROM my_schema.rental_history WHERE history_id = ?";

    private static final String SELECT =
            "SELECT history_id, contract, event_date, event_type, details FROM my_schema.rental_history";

    private static final String UPDATE =
            "UPDATE my_schema.rental_history " +
                    "SET contract = ?, event_date = ?, event_type = ?, details = ? " +
                    "WHERE history_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.rental_history WHERE history_id = ?";

    private static final Logger logger = LoggerFactory.getLogger(RentalHistoryDaoImpl.class);


    protected List<RentalHistory> mapper(ResultSet rs){
        List<RentalHistory> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new RentalHistory(rs.getLong("history_id"),
                        rs.getLong("contract"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getString("event_type"),
                        rs.getString("details")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(RentalHistory history) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, history.getContract());
            statement.setDate(2, Date.valueOf(history.getEventDate()));
            statement.setString(3, history.getEventType());
            statement.setString(4, history.getDetails());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<RentalHistory> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new RentalHistory(rs.getLong("history_id"),
                            rs.getLong("contract"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getString("event_type"),
                            rs.getString("details")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<RentalHistory> getAll() {
        List<RentalHistory> list = new ArrayList<>();
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
    public void update(RentalHistory history) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, history.getContract());
            statement.setDate(2, Date.valueOf(history.getEventDate()));
            statement.setString(3, history.getEventType());
            statement.setString(4, history.getDetails());
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
