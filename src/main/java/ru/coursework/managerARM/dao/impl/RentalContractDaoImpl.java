package ru.coursework.managerARM.dao.impl;

import ru.coursework.managerARM.dao.RentalContractDao;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.dto.RentalContractView;
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

    private static final String SELECT_VIEWS =
            "SELECT " +
                    "contr.contract_id, " +
                    "r.reservation_id, " +
                    "r.client_id, " +
                    "c.natural_person_id, " +
                    "c.legal_person_id, " +
                    "CASE " +
                    "    WHEN c.natural_person_id IS NOT NULL THEN CONCAT_WS(' ', np.surname, np.name, np.patronymic) " +
                    "    ELSE lp.company_name " +
                    "END AS client_name, " +
                    "contr.issue_date, " +
                    "contr.planned_return_date, " +
                    "contr.actual_return_date, " +
                    "contr.deposit_amount, " +
                    "contr.total_amount, " +
                    "contr.status, " +
                    "contr.issue_condition_desc, " +
                    "contr.issue_condition_photo " +
                    "FROM my_schema.rental_contract contr " +
                    "LEFT JOIN my_schema.reservation r ON contr.reservation_id = r.reservation_id " +
                    "LEFT JOIN my_schema.client c ON r.client = c.client_id " +
                    "LEFT JOIN my_schema.natural_person np ON c.natural_person_id = np.natural_person_id " +
                    "LEFT JOIN my_schema.legal_person lp ON c.legal_person_id = lp.legal_person_id ";

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
    public List<RentalContractView> getAllViews() {
        List<RentalContractView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Date actualReturnSqlDate = rs.getDate("actual_return_date");
                LocalDate actualReturnDate = actualReturnSqlDate != null
                        ? actualReturnSqlDate.toLocalDate()
                        : null;

                list.add(new RentalContractView(
                        rs.getLong("contract_id"),
                        rs.getLong("reservation_id"),
                        rs.getLong("client_id"),
                        rs.getLong("natural_person_id"),
                        rs.getLong("legal_person_id"),
                        rs.getString("client_name"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("planned_return_date").toLocalDate(),
                        actualReturnDate,
                        rs.getBigDecimal("deposit_amount"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("status"),
                        rs.getString("issue_condition_desc"),
                        rs.getString("issue_condition_photo")
                        ));
            }
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
