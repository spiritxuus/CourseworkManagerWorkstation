package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.RepairDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Repair;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairDaoImpl implements RepairDao {
    private static final String INSERT =
            "INSERT INTO my_schema.repair (equipment, repair_reason, repair_cost) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT repair_id, equipment, date_created, repair_reason, repair_status, repair_cost FROM my_schema.repair WHERE repair_id = ?";

    private static final String SELECT =
            "SELECT repair_id, equipment, date_created, repair_reason, repair_status, repair_cost FROM my_schema.repair";

    private static final String UPDATE =
            "UPDATE my_schema.repair " +
                    "SET equipment = ?, repair_reason = ?, repair_status = ?, repair_cost = ? " +
                    "WHERE repair_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.repair WHERE repair_id = ?";

    private static final Logger logger = LoggerFactory.getLogger(RepairDaoImpl.class);


    protected List<Repair> mapper(ResultSet rs){
        List<Repair> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Repair(rs.getLong("repair_id"),
                        rs.getLong("equipment"),
                        rs.getDate("date_created").toLocalDate(),
                        rs.getString("repair_reason"),
                        rs.getString("repair_status"),
                        rs.getBigDecimal("repair_cost")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(Repair repair) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, repair.getEquipment());
            statement.setString(2, repair.getRepairReason());
            statement.setBigDecimal(3, repair.getRepairCost());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<Repair> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Repair(rs.getLong("repair_id"),
                            rs.getLong("equipment"),
                            rs.getDate("date_created").toLocalDate(),
                            rs.getString("repair_reason"),
                            rs.getString("repair_status"),
                            rs.getBigDecimal("repair_cost")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Repair> getAll() {
        List<Repair> list = new ArrayList<>();
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
    public void update(Repair repair) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, repair.getEquipment());
            statement.setString(2, repair.getRepairReason());
            statement.setString(3, repair.getRepairStatus());
            statement.setBigDecimal(4, repair.getRepairCost());
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
