package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.ReturnOfEquipmentDao;
import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.dto.ReturnView;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.ReturnOfEquipment;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnOfEquipmentDaoImpl implements ReturnOfEquipmentDao {
    private static final String INSERT = SqlProvider.get("sql.returnOfEquipment_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.returnOfEquipment_selectById");

    private static final String SELECT_CONTRACT_VIEWS = SqlProvider.get("sql.returnOfEquipment_selectContractViews");

    private static final String SELECT_VIEWS = SqlProvider.get("sql.returnOfEquipment_selectViews");

    private static final String SELECT = SqlProvider.get("sql.returnOfEquipment_select");

    private static final String UPDATE = SqlProvider.get("sql.returnOfEquipment_update");

    private final static String DELETE = SqlProvider.get("sql.returnOfEquipment_delete");

    private static final Logger logger = LoggerFactory.getLogger(ReturnOfEquipmentDaoImpl.class);

    protected List<ReturnOfEquipment> mapper(ResultSet rs){
        List<ReturnOfEquipment> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new ReturnOfEquipment(rs.getLong("return_id"),
                        rs.getLong("contract"),
                        rs.getDate("return_date").toLocalDate(),
                        rs.getString("condition_desc"),
                        rs.getString("condition_photo"),
                        rs.getBigDecimal("damage_amount"),
                        rs.getBigDecimal("deduction_amount"),
                        rs.getBoolean("repair_required")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(ReturnOfEquipment returnOfEquipment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, returnOfEquipment.getContract());
            statement.setDate(2, Date.valueOf(returnOfEquipment.getReturnDate()));
            statement.setString(3, returnOfEquipment.getConditionDesc());
            statement.setString(4, returnOfEquipment.getConditionPhoto());
            statement.setBigDecimal(5, returnOfEquipment.getDamageAmount());
            statement.setBigDecimal(6, returnOfEquipment.getDeductionAmount());
            statement.setBoolean(7, returnOfEquipment.getRepairRequired());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<ReturnOfEquipment> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new ReturnOfEquipment(rs.getLong("return_id"),
                            rs.getLong("contract"),
                            rs.getDate("return_date").toLocalDate(),
                            rs.getString("condition_desc"),
                            rs.getString("condition_photo"),
                            rs.getBigDecimal("damage_amount"),
                            rs.getBigDecimal("deduction_amount"),
                            rs.getBoolean("repair_required")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<ReturnOfEquipment> getAll() {
        List<ReturnOfEquipment> list = new ArrayList<>();
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
    public List<RentalContractViewCb> getContract(){
        List<RentalContractViewCb> contracts = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_CONTRACT_VIEWS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Date issueDateSql = rs.getDate("issue_date");

                contracts.add(new RentalContractViewCb(
                        rs.getLong("contract_id"),
                        rs.getLong("reservation_id"),
                        rs.getString("client_name"),
                        issueDateSql != null ? issueDateSql.toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }

        return contracts;
    }

    @Override
    public List<ReturnView> getAllViews() {
        List<ReturnView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Date returnDateSql = rs.getDate("return_date");
                LocalDate returnDate = returnDateSql != null ? returnDateSql.toLocalDate() : null;

                list.add(new ReturnView(
                        rs.getLong("return_id"),
                        rs.getLong("contract_id"),
                        rs.getString("client_name"),
                        rs.getString("equipment_name"),
                        returnDate,
                        rs.getString("condition_desc"),
                        rs.getString("condition_photo"),
                        rs.getBigDecimal("damage_amount"),
                        rs.getBigDecimal("deduction_amount"),
                        rs.getBoolean("repair_required")
                ));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }

        return list;
    }

    @Override
    public void update(ReturnOfEquipment returnOfEquipment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, returnOfEquipment.getContract());
            statement.setDate(2, Date.valueOf(returnOfEquipment.getReturnDate()));
            statement.setString(3, returnOfEquipment.getConditionDesc());
            statement.setString(4, returnOfEquipment.getConditionPhoto());
            statement.setBigDecimal(5, returnOfEquipment.getDamageAmount());
            statement.setBigDecimal(6, returnOfEquipment.getDeductionAmount());
            statement.setBoolean(7, returnOfEquipment.getRepairRequired());
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
