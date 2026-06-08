package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.RepairDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Repair;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairDaoImpl implements RepairDao {
    private static final String INSERT = SqlProvider.get("sql.repair_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.repair_selectById");

    private static final String SELECT = SqlProvider.get("sql.repair_select");

    private static final String UPDATE = SqlProvider.get("sql.repair_update");

    private final static String DELETE = SqlProvider.get("sql.repair_delete");

    private static final Logger logger = LoggerFactory.getLogger(RepairDaoImpl.class);


    protected List<Repair> mapper(ResultSet rs){
        List<Repair> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Repair(rs.getLong("repair_id"),
                        rs.getLong("equipment"),
                        rs.getDate("date_created").toLocalDate(),
                        rs.getString("repair_reason"),
                        rs.getInt("repair_status"),
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
                            rs.getInt("repair_status"),
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
            statement.setInt(3, repair.getRepairStatus());
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
