package ru.coursework.managerARM.util;

import java.io.IOException;
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
    public static class OverdueEquipment {
        public int equipmentId;
        public String equipmentName;
        public LocalDate plannedReturnDate;
        public String clientName;

        public OverdueEquipment(int id, String name, LocalDate date, String client) {
            this.equipmentId = id;
            this.equipmentName = name;
            this.plannedReturnDate = date;
            this.clientName = client;
        }

        @Override
        public String toString() {
            return String.format("%s (ID: %d) - должен быть возвращён %s клиентом %s",
                    equipmentName, equipmentId, plannedReturnDate, clientName);
        }
    }

    //категории
    public static class PopularCategory {
        public String categoryName;
        public long rentalCount;

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
            e.printStackTrace(); //TODO ЛОГИРОВАНИЕ ВО ВСЕХ DAO
        }
        return list;
    }

    public List<PopularCategory> getPopularCategories() {
        List<PopularCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM get_popular_categories()";

        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                list.add(new PopularCategory(
                        rs.getString("category_name"),
                        rs.getLong("rental_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getMonthlyRevenue(int year, int month) {
        String sql = "SELECT get_monthly_revenue(?, ?)";
        try (PreparedStatement statement = DbUtils.getConnection().prepareStatement(sql)) {

            statement.setInt(1, year);
            statement.setInt(2, month);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO ЛОГИРОВАНИЕ ВО ВСЕХ DAO
        }
        return 0.0;
    }

    public void generateReportToFile(int year, int month, String filePath) throws IOException {
        List<OverdueEquipment> overdue = getOverdueEquipment();
        List<PopularCategory> popular = getPopularCategories();
        double revenue = getMonthlyRevenue(year, month);

        StringBuilder sb = new StringBuilder();
        sb.append("Отчёт за ").append(month).append(".").append(year).append("\n\n");

        sb.append("Просроченное оборудование - \n");
        if (overdue.isEmpty()) {
            sb.append("  Нет просроченного оборудования.\n");
        } else {
            for (OverdueEquipment eq : overdue) {
                sb.append("  - ").append(eq.toString()).append("\n");
            }
        }

        sb.append("\nВостребованные категории - \n");
        if (popular.isEmpty()) {
            sb.append("  Нет данных.\n");
        } else {
            for (PopularCategory cat : popular) {
                sb.append("  - ").append(cat.toString()).append("\n");
            }
        }

        sb.append("\nВыручка за месяц - ").append(String.format("%.2f руб.", revenue)).append("\n");

        Path path = Paths.get(filePath);
        Files.writeString(path, sb.toString());
    }
}
