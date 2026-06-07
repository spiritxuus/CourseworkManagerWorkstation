package ru.coursework.managerARM.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.dao.AddressDao;
import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.util.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDaoImpl implements AddressDao {
    private static final String INSERT =
            "INSERT INTO my_schema.address (country, region, city, street, house, apartment) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT address_id, country, region, city, street, house, apartment FROM my_schema.address WHERE address_id = ?";

    private static final String SELECT =
            "SELECT address_id, country, region, city, street, house, apartment FROM my_schema.address";

    private static final String UPDATE =
            "UPDATE my_schema.address " +
                    "SET country = ?, region = ?, city = ?, street = ?, house = ?, apartment = ? " +
                    "WHERE address_id = ?";

    private static final String DELETE =
            "DELETE FROM my_schema.address WHERE address_id = ?";

    private static final String SELECT_VIEWS =
            "SELECT address_id, country, region, city, street, house, apartment " +
                    "FROM my_schema.address " +
                    "ORDER BY country, region, city, street, house, apartment";

    private static final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class);

    protected List<Address> mapper(ResultSet rs){
        List<Address> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Address(rs.getLong("address_id"),
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getString("house"),
                        rs.getString("apartment")));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return list;
    }

    @Override
    public void add(Address address) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouse());
            statement.setString(6, address.getApartment());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL error", e);
        }
    }

    @Override
    public Optional<Address> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Address(rs.getLong("address_id"),
                            rs.getString("country"),
                            rs.getString("region"),
                            rs.getString("city"),
                            rs.getString("street"),
                            rs.getString("house"),
                            rs.getString("apartment")));
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Address> getAll() {
        List<Address> list = new ArrayList<>();
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
    public List<AddressView> getAllViews() {
        List<AddressView> list = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_VIEWS);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                list.add(new AddressView(
                        rs.getLong("address_id"),
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getString("house"),
                        rs.getString("apartment")
                ));
            }
        } catch (SQLException e) {
            logger.error("SQL error", e);
        }

        return list;
    }

    @Override
    public void update(Address address) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getHouse());
            statement.setString(6, address.getApartment());
            statement.setLong(7, address.getAddressId());
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
