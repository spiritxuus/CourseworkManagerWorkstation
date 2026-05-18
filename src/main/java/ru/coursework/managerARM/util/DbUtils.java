package ru.coursework.managerARM.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static Connection connection;
    private static final String pgUrl = "jdbc:postgresql://localhost:5432/coursework_db";
    private static final String pgUser = "postgres";
    private static final String pgPassword = "16266";

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(pgUrl, pgUser, pgPassword);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
