package com.kamenskiy.io.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    public static Connection open() {
        try {
            return DriverManager.getConnection(PropertyUtils.getProperty(URL_KEY),
                    PropertyUtils.getProperty(USERNAME_KEY),
                    PropertyUtils.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
}
