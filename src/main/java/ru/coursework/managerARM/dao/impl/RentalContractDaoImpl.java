package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.RentalContractDao;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.RentalContract;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalContractDaoImpl implements RentalContractDao {
    private static final String INSERT = SqlProvider.get("sql.rentalContract_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.rentalContract_selectById");

    private static final String SELECT = SqlProvider.get("sql.rentalContract_select");

    private static final String UPDATE = SqlProvider.get("sql.rentalContract_update");

    private final static String DELETE = SqlProvider.get("sql.rentalContract_delete");

    private static final String SELECT_VIEWS = SqlProvider.get("sql.rentalContract_selectViews");

    private static final Logger logger = LoggerFactory.getLogger(RentalContractDaoImpl.class);


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
                        rs.getInt("status"),
                        rs.getString("issue_condition_desc"),
                        rs.getString("issue_condition_photo")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(RentalContract contract) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, contract.getReservationId());
            statement.setDate(2, Date.valueOf(contract.getIssueDate()));
            statement.setDate(3, Date.valueOf(contract.getPlannedReturnDate()));
            if (contract.getActualReturnDate() != null) {
                statement.setDate(4, Date.valueOf(contract.getActualReturnDate()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }
            statement.setBigDecimal(5, contract.getDepositAmount());
            statement.setBigDecimal(6, contract.getTotalAmount());
            statement.setInt(7, contract.getStatus());
            statement.setString(8, contract.getIssueConditionDesc());
            statement.setString(9, contract.getIssueConditionPhoto());
            statement.execute();
        } catch (SQLException e){
            logger.error("SQL error", e);
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
                            rs.getInt("status"),
                            rs.getString("issue_condition_desc"),
                            rs.getString("issue_condition_photo")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
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
            logger.error("SQL error", e);
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
                        rs.getLong("client"),
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
            logger.error("SQL error", e);
        }

        return list;
    }

    @Override
    public void update(RentalContract contract) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
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
            statement.setInt(8, contract.getStatus());
            statement.setString(9, contract.getIssueConditionDesc());
            statement.setString(10, contract.getIssueConditionPhoto());
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
