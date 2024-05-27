package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.utils.ConnectionManager;

import java.sql.SQLException;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        String sql = """
                DROP TABLE info;
                                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(statement.executeUpdate(sql));
        }
    }
}
