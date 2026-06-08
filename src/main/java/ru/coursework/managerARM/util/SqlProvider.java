package ru.coursework.managerARM.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class SqlProvider {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream is = SqlProvider.class.getResourceAsStream("/sql/statements.properties")) {
            if (is == null) {
                throw new IllegalStateException("sql/statements.properties not found");
            }
            PROPERTIES.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private SqlProvider() {
    }

    public static String get(String key) {
        String sql = PROPERTIES.getProperty(key);
        if (sql == null) {
            throw new IllegalArgumentException("SQL key not found: " + key);
        }
        return sql;
    }
}