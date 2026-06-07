package ru.coursework.managerARM.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Управление подключением к PostgreSQL.
 * Загружает config.properties, инициализирует соединение с UTF-8.
 */
public final class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    private static Connection connection;
    private static String dbUrl;
    private static String dbName;

    static {
        Properties props = new Properties();

        try (InputStream is = DbUtils.class.getResourceAsStream("/config.properties")) {
            if (is == null) {
                throw new IllegalStateException("config.properties not found");
            }
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));

            dbUrl = props.getProperty("db.url");
            dbName = props.getProperty("db.name");

            if (dbUrl == null || dbName == null) {
                throw new IllegalStateException("db.url or db.name is missing in config.properties");
            }

            logger.debug("Loaded DB config: url={}, name={}", dbUrl, dbName);
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private DbUtils() {
    }

    /**
     * Устанавливает соединение с БД.
     * @param user логин
     * @param password пароль
     * @throws SQLException при ошибке подключения
     */
    public static void initConnection(String user, String password) throws SQLException {
        closeConnection();

        String fullUrl = dbUrl + dbName;
        logger.info("Connecting to database {} as user {}", fullUrl, user);

        if (!fullUrl.contains("client_encoding")) {
            fullUrl += (fullUrl.contains("?") ? "&" : "?") +
                    "client_encoding=UTF-8&useUnicode=true&characterEncoding=utf8";
        }
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("client_encoding", "UTF-8");
        props.setProperty("charSet", "UTF-8");
        connection = DriverManager.getConnection(fullUrl, user, password);
        connection.setAutoCommit(true);
        logger.info("Database connection established");
    }

    /** Возвращает активное соединение. */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not initialized. Call initConnection() first.");
        }
        return connection;
    }

    /** Закрывает соединение. */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error while closing DB connection", e);
            }
        }
    }
}