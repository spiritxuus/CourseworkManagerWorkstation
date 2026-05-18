package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.ReservationDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {
    private static final String INSERT =
            "INSERT INTO my_schema.reservation (client, equipment, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT reservation_id, client, equipment, start_date, end_date, status FROM my_schema.reservation WHERE reservation_id = ?";

    private static final String SELECT =
            "SELECT reservation_id, client, equipment, start_date, end_date, status FROM my_schema.reservation";

    private static final String UPDATE =
            "UPDATE my_schema.reservation " +
                    "SET client = ?, equipment = ?, start_date = ?, end_date = ?, status = ? " +
                    "WHERE reservation_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.reservation WHERE reservation_id = ?";

    protected List<Reservation> mapper(ResultSet rs){
        List<Reservation> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Reservation(rs.getLong("reservation_id"),
                        rs.getLong("client"),
                        rs.getLong("equipment"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")));
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
            statement.setString(5, reservation.getStatus());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
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
                            rs.getDate("end_date").toLocalDate(),
                            rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            statement.setString(5, reservation.getStatus());
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
