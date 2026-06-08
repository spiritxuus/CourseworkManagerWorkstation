package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.controller.ManagerController;
import ru.coursework.managerARM.dao.PaymentDao;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Payment;
import ru.coursework.managerARM.util.SqlProvider;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDaoImpl implements PaymentDao {
    private static final String INSERT = SqlProvider.get("sql.payment_insert");

    private static final String SELECT_BY_ID = SqlProvider.get("sql.payment_selectById");

    private static final String SELECT_BY_CONTRACT = SqlProvider.get("sql.payment_selectByContract");

    private static final String SELECT = SqlProvider.get("sql.payment_select");

    private static final String UPDATE = SqlProvider.get("sql.payment_update");

    private final static String DELETE = SqlProvider.get("sql.payment_delete");

    private static final Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);


    protected List<Payment> mapper(ResultSet rs){
        List<Payment> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Payment(rs.getLong("payment_id"),
                        rs.getLong("contract"),
                        rs.getDate("payment_date").toLocalDate(),
                        rs.getBigDecimal("amount"),
                        rs.getInt("payment_method")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(Payment payment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, payment.getContract());
            statement.setDate(2, Date.valueOf(payment.getPaymentDate()));
            statement.setBigDecimal(3, payment.getAmount());
            statement.setInt(4, payment.getPaymentMethod());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<Payment> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Payment(rs.getLong("payment_id"),
                            rs.getLong("contract"),
                            rs.getDate("payment_date").toLocalDate(),
                            rs.getBigDecimal("amount"),
                            rs.getInt("payment_method")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Payment> getByContract(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_CONTRACT)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Payment(rs.getLong("payment_id"),
                            rs.getLong("contract"),
                            rs.getDate("payment_date").toLocalDate(),
                            rs.getBigDecimal("amount"),
                            rs.getInt("payment_method")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();
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
    public void update(Payment payment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, payment.getContract());
            statement.setDate(2, Date.valueOf(payment.getPaymentDate()));
            statement.setBigDecimal(3, payment.getAmount());
            statement.setInt(4, payment.getPaymentMethod());
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
