package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.ReservationDao;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Reservation;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {
    private static final String INSERT = SqlProvider.get("sql.reservation_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.reservation_selectById");

    private static final String SELECT = SqlProvider.get("sql.reservation_select");

    private static final String UPDATE = SqlProvider.get("sql.reservation_update");

    private final static String DELETE = SqlProvider.get("sql.reservation_delete");

    private static final String SELECT_VIEWS = SqlProvider.get("sql.reservation_selectViews");

    private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);


    protected List<Reservation> mapper(ResultSet rs){
        List<Reservation> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Reservation(rs.getLong("reservation_id"),
                        rs.getLong("client"),
                        rs.getLong("equipment"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(Reservation reservation) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, reservation.getClient());
            statement.setLong(2, reservation.getEquipment());
            statement.setDate(3, Date.valueOf(reservation.getStartDate()));
            statement.setDate(4, Date.valueOf(reservation.getEndDate()));
            statement.execute();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<Reservation> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Reservation(rs.getLong("reservation_id"),
                            rs.getLong("client"),
                            rs.getLong("equipment"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate()));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> list = new ArrayList<>();
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
    public List<ReservationView> getAllViews() {
        List<ReservationView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                list.add(new ReservationView(
                        rs.getLong("reservation_id"),
                        rs.getLong("client"),
                        rs.getLong("natural_person_id"),
                        rs.getLong("legal_person_id"),
                        rs.getString("client_name"),
                        rs.getString("equipment_name"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }

        return list;
    }

    @Override
    public void update(Reservation reservation) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, reservation.getClient());
            statement.setLong(2, reservation.getEquipment());
            statement.setDate(3, Date.valueOf(reservation.getStartDate()));
            statement.setDate(4, Date.valueOf(reservation.getEndDate()));
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
