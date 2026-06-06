package ru.coursework.managerARM.util;

import lombok.Getter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReportService {
    //просроченное
    @Getter
    public static class OverdueEquipment {
        private final int equipmentId;
        private final String equipmentName;
        private final LocalDate plannedReturnDate;
        private final String clientName;


        public OverdueEquipment(int id, String name, LocalDate date, String client) {
            this.equipmentId = id;
            this.equipmentName = name;
            this.plannedReturnDate = date;
            this.clientName = client;
        }

        @Override
        public String toString() {
            return String.format("%s (ID: %d) — должен быть возвращён %s клиентом %s",
                    equipmentName, equipmentId, plannedReturnDate, clientName);
        }
    }

    //категории
    @Getter
    public static class PopularCategory {
        private final String categoryName;
        private final long rentalCount;

        public PopularCategory(String name, long count) {
            this.categoryName = name;
            this.rentalCount = count;
        }

        @Override
        public String toString() {
            return String.format("%s: %d аренд", categoryName, rentalCount);
        }
    }

    public List<OverdueEquipment> getOverdueEquipment() {
        List<OverdueEquipment> list = new ArrayList<>();
        String sql = "SELECT * FROM get_overdue_equipment()";

        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                list.add(new OverdueEquipment(
                        rs.getInt("equipment_id"),
                        rs.getString("equipment_name"),
                        rs.getDate("planned_return_date").toLocalDate(),
                        rs.getString("client_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO log
        }
        return list;
    }

    public List<PopularCategory> getPopularCategories(int year, int month) {
        List<PopularCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM my_schema.get_popular_categories(?, ?)";

        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setInt(2, month);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(new PopularCategory(
                            rs.getString("category_name"),
                            rs.getLong("rental_count")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO log позже лучше logger.error(...)
        }

        return list;
    }

    public BigDecimal getMonthlyRevenue(int year, int month) {
        String sql = "SELECT my_schema.get_monthly_revenue(?, ?)";
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setInt(2, month);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO позже лучше logger.error(...)
        }
        return BigDecimal.ZERO;
    }

    public void generateReportToFile(int year, int month, String filePath) throws IOException {
        List<OverdueEquipment> overdue = getOverdueEquipment();
        List<PopularCategory> popular = getPopularCategories(year, month);
        BigDecimal revenue = getMonthlyRevenue(year, month);

        StringBuilder sb = new StringBuilder();
        sb.append("Report ").append(month).append(".").append(year).append("\n\n");

        sb.append("Overdue equipment - \n");
        if (overdue.isEmpty()) {
            sb.append("  There is no overdue equipment.\n");
        } else {
            for (OverdueEquipment eq : overdue) {
                sb.append("  - ").append(eq.toString()).append("\n");
            }
        }

        sb.append("\nPopular categories - \n");
        if (popular.isEmpty()) {
            sb.append("  No data.\n");
        } else {
            for (PopularCategory cat : popular) {
                sb.append("  - ").append(cat.toString()).append("\n");
            }
        }

        sb.append("\nMonthly revenue - ").append(revenue.setScale(2, RoundingMode.HALF_UP)).append(" rub.\n");

        Path path = Paths.get(filePath);
        Files.writeString(path, sb.toString());
    }
}
