package ru.coursework.managerARM.dao.impl;


import ru.coursework.managerARM.dao.EquipmentDao;
import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.util.DbUtils;
import ru.coursework.managerARM.model.Equipment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentDaoImpl implements EquipmentDao {
    private static final String INSERT =
            "INSERT INTO my_schema.equipment (category, name, manufacturer, model, " +
                    "inventory_number, serial_number, rental_price_per_day, " +
                    "deposit_amount, condition_status, requires_repair, photo, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT equipment_id, category, name, manufacturer, " +
                    "model, inventory_number, serial_number, rental_price_per_day, " +
                    "deposit_amount, condition_status, requires_repair, photo, description " +
                    "FROM my_schema.equipment WHERE equipment_id = ?";

    private static final String SELECT_CATEGORY_VIEWS =
            "SELECT category_id, category_name " +
                    "FROM my_schema.equipment_category " +
                    "ORDER BY category_name";

    private static final String SELECT =
            "SELECT equipment_id, category, name, manufacturer, " +
                    "model, inventory_number, serial_number, rental_price_per_day, " +
                    "deposit_amount, condition_status, requires_repair, photo, description " +
                    "FROM my_schema.equipment";

    private static final String UPDATE =
            "UPDATE my_schema.equipment " +
                    "SET category = ?, name = ?, manufacturer = ?, model = ?, " +
                    "inventory_number = ?, serial_number = ?, rental_price_per_day = ?, deposit_amount = ?, " +
                    "condition_status = ?, requires_repair = ?, photo = ?, description = ? " +
                    "WHERE equipment_id = ?";

    private final static String DELETE =
            "DELETE FROM my_schema.equipment WHERE equipment_id = ?";

    protected List<Equipment> mapper(ResultSet rs){
        List<Equipment> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Equipment(rs.getLong("equipment_id"),
                        rs.getLong("category"),
                        rs.getString("name"),
                        rs.getString("manufacturer"),
                        rs.getString("model"),
                        rs.getString("inventory_number"),
                        rs.getString("serial_number"),
                        rs.getBigDecimal("rental_price_per_day"),
                        rs.getBigDecimal("deposit_amount"),
                        rs.getString("condition_status"),
                        rs.getBoolean("requires_repair"),
                        rs.getString("photo"),
                        rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void add(Equipment equipment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(INSERT)){
            statement.setLong(1, equipment.getCategory());
            statement.setString(2, equipment.getName());
            statement.setString(3, equipment.getManufacturer());
            statement.setString(4, equipment.getModel());
            statement.setString(5, equipment.getInventoryNumber());
            statement.setString(6, equipment.getSerialNumber());
            statement.setBigDecimal(7, equipment.getRentalPricePerDay());
            statement.setBigDecimal(8, equipment.getDepositAmount());
            statement.setString(9, equipment.getConditionStatus());
            statement.setBoolean(10, equipment.getRequiresRepair());
            statement.setString(11, equipment.getPhoto());
            statement.setString(12, equipment.getDescription());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Equipment> getById(Long id) {
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Equipment(rs.getLong("equipment_id"),
                            rs.getLong("category"),
                            rs.getString("name"),
                            rs.getString("manufacturer"),
                            rs.getString("model"),
                            rs.getString("inventory_number"),
                            rs.getString("serial_number"),
                            rs.getBigDecimal("rental_price_per_day"),
                            rs.getBigDecimal("deposit_amount"),
                            rs.getString("condition_status"),
                            rs.getBoolean("requires_repair"),
                            rs.getString("photo"),
                            rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Equipment> getAll() {
        List<Equipment> list = new ArrayList<>();
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
    public List<EquipmentCategoryView> getCategory() {
        List<EquipmentCategoryView> categories = new ArrayList<>();
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(SELECT_CATEGORY_VIEWS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                categories.add(new EquipmentCategoryView(
                        rs.getLong("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    @Override
    public void update(Equipment equipment) {
        try(PreparedStatement statement =
                    DbUtils.getConnection().prepareStatement(UPDATE)){
            statement.setLong(1, equipment.getCategory());
            statement.setString(2, equipment.getName());
            statement.setString(3, equipment.getManufacturer());
            statement.setString(4, equipment.getModel());
            statement.setString(5, equipment.getInventoryNumber());
            statement.setString(6, equipment.getSerialNumber());
            statement.setBigDecimal(7, equipment.getRentalPricePerDay());
            statement.setBigDecimal(8, equipment.getDepositAmount());
            statement.setString(9, equipment.getConditionStatus());
            statement.setBoolean(10, equipment.getRequiresRepair());
            statement.setString(11, equipment.getPhoto());
            statement.setString(12, equipment.getDescription());
            statement.setLong(13, equipment.getEquipmentId());
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
