package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.ReturnOfEquipmentDao;
import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.dto.ReturnView;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.ReturnOfEquipment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnOfEquipmentDaoImpl implements ReturnOfEquipmentDao {
    private static final String INSERT =
            "INSERT INTO my_schema.return_of_equipment (contract, return_date, condition_desc, condition_photo, damage_amount, deduction_amount, repair_required) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT return_id, contract, return_date, condition_desc, condition_photo, damage_amount, deduction_amount, repair_required FROM my_schema.return_of_equipment WHERE return_id = ?";

    private static final String SELECT_CONTRACT_VIEWS =
            "SELECT " +
                    "contract.contract_id AS contract_id, " +
                    "contract.reservation_id AS reservation_id, " +
                    "CASE " +
                    "    WHEN client.natural_person_id IS NOT NULL THEN CONCAT_WS(' ', np.surname, np.name) " +
                    "    ELSE lp.company_name " +
                    "END AS client_name, " +
                    "contract.issue_date AS issue_date " +
                    "FROM my_schema.rental_contract contract " +
                    "LEFT JOIN my_schema.reservation r ON contract.reservation_id = r.reservation_id " +
                    "LEFT JOIN my_schema.client client ON r.client = client.client_id " +
                    "LEFT JOIN my_schema.natural_person np ON client.natural_person_id = np.natural_person_id " +
                    "LEFT JOIN my_schema.legal_person lp ON client.legal_person_id = lp.legal_person_id";

    private static final String SELECT_VIEWS =
            "SELECT " +
                    "ret.return_id AS return_id, " +
                    "contract.contract_id AS contract_id, " +
                    "CASE " +
                    "    WHEN client.natural_person_id IS NOT NULL THEN CONCAT_WS(' ', np.surname, np.name) " +
                    "    ELSE lp.company_name " +
                    "END AS client_name, " +
                    "equip.name AS equipment_name, " +
                    "ret.return_date AS return_date, " +
                    "ret.condition_desc AS condition_desc, " +
                    "ret.condition_photo AS condition_photo, " +
                    "ret.damage_amount AS damage_amount, " +
                    "ret.deduction_amount AS deduction_amount, " +
                    "ret.repair_required AS repair_required " +
                    "FROM my_schema.return_of_equipment ret " +
                    "LEFT JOIN my_schema.rental_contract contract ON contract.contract_id = ret.contract " +
                    "LEFT JOIN my_schema.reservation r ON contract.reservation_id = r.reservation_id " +
                    "LEFT JOIN my_schema.equipment equip ON r.equipment = equip.equipment_id " +
                    "LEFT JOIN my_schema.client client ON r.client = client.client_id " +
                    "LEFT JOIN my_schema.natural_person np ON client.natural_person_id = np.natural_person_id " +
                    "LEFT JOIN my_schema.legal_person lp ON client.legal_person_id = lp.legal_person_id";

    private static final String SELECT =
            "SELECT return_id, contract, return_date, condition_desc, condition_photo, damage_amount, deduction_amount, repair_required FROM my_schema.return_of_equipment";

    private static final String UPDATE =
            "UPDATE my_schema.return_of_equipment " +
                    "SET contract = ?, return_date = ?, condition_desc = ?, condition_photo = ?, damage_amount = ?, deduction_amount = ?, repair_required = ? " +
                    "WHERE return_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.return_of_equipment WHERE return_id = ?";

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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
