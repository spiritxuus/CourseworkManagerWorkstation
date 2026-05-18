package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.RentalContractDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.RentalContract;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalContractDaoImpl implements RentalContractDao {
    private static final String INSERT =
            "INSERT INTO my_schema.rental_contract (reservation_id, client_id, issue_date, planned_return_date, " +
                    "actual_return_date, deposit_amount, total_amount, " +
                    "status, issue_condition_desc, issue_condition_photo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT contract_id, reservation_id, client_id, issue_date, " +
                    "planned_return_date, actual_return_date, deposit_amount, total_amount, " +
                    "status, issue_condition_desc, issue_condition_photo " +
                    "FROM my_schema.rental_contract WHERE contract_id = ?";

    private static final String SELECT =
            "SELECT contract_id, reservation_id, client_id, issue_date, " +
                    "planned_return_date, actual_return_date, deposit_amount, total_amount, " +
                    "status, issue_condition_desc, issue_condition_photo " +
                    "FROM my_schema.rental_contract";

    private static final String UPDATE =
            "UPDATE my_schema.rental_contract " +
                    "SET reservation_id = ?, client_id = ?, issue_date = ?, " +
                    "planned_return_date = ?, actual_return_date = ?, deposit_amount = ?, total_amount = ?, " +
                    "status = ?, issue_condition_desc = ?, issue_condition_photo = ? " +
                    "WHERE contract_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.rental_contract WHERE contract_id = ?";

    protected List<RentalContract> mapper(ResultSet rs){
        List<RentalContract> list = new ArrayList<>();
        try {
            Date actualReturnSqlDate = rs.getDate("actual_return_date");
            LocalDate actualReturnDate = actualReturnSqlDate != null
                    ? actualReturnSqlDate.toLocalDate()
                    : null;

            while (rs.next()) {
                list.add(new RentalContract(rs.getLong("contract_id"),
                        rs.getLong("reservation_id"),
                        rs.getLong("client_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("planned_return_date").toLocalDate(),
                        actualReturnDate,
                        rs.getBigDecimal("deposit_amount"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("status"),
                        rs.getString("issue_condition_desc"),
                        rs.getString("issue_condition_photo")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(RentalContract contract) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, contract.getReservationId());
            statement.setLong(2, contract.getClientId());
            statement.setDate(3, Date.valueOf(contract.getIssueDate()));
            statement.setDate(4, Date.valueOf(contract.getPlannedReturnDate()));
            if (contract.getActualReturnDate() != null) {
                statement.setDate(5, Date.valueOf(contract.getActualReturnDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }
            statement.setBigDecimal(6, contract.getDepositAmount());
            statement.setBigDecimal(7, contract.getTotalAmount());
            statement.setString(8, contract.getStatus());
            statement.setString(9, contract.getIssueConditionDesc());
            statement.setString(10, contract.getIssueConditionPhoto());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<RentalContract> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                Date actualReturnSqlDate = rs.getDate("actual_return_date");
                LocalDate actualReturnDate = actualReturnSqlDate != null
                        ? actualReturnSqlDate.toLocalDate()
                        : null;

                if (rs.next()) {
                    return Optional.of(new RentalContract(rs.getLong("contract_id"),
                            rs.getLong("reservation_id"),
                            rs.getLong("client_id"),
                            rs.getDate("issue_date").toLocalDate(),
                            rs.getDate("planned_return_date").toLocalDate(),
                            actualReturnDate,
                            rs.getBigDecimal("deposit_amount"),
                            rs.getBigDecimal("total_amount"),
                            rs.getString("status"),
                            rs.getString("issue_condition_desc"),
                            rs.getString("issue_condition_photo")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<RentalContract> getAll() {
        List<RentalContract> list = new ArrayList<>();
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
    public void update(RentalContract contract) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, contract.getReservationId());
            statement.setLong(2, contract.getClientId());
            statement.setDate(3, Date.valueOf(contract.getIssueDate()));
            statement.setDate(4, Date.valueOf(contract.getPlannedReturnDate()));
            if (contract.getActualReturnDate() != null) {
                statement.setDate(5, Date.valueOf(contract.getActualReturnDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }
            statement.setBigDecimal(6, contract.getDepositAmount());
            statement.setBigDecimal(7, contract.getTotalAmount());
            statement.setString(8, contract.getStatus());
            statement.setString(9, contract.getIssueConditionDesc());
            statement.setString(10, contract.getIssueConditionPhoto());
            statement.executeUpdate();
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
