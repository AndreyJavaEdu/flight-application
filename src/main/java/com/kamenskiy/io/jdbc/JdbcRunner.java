package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.utils.ConnectionManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        String sql = """
//                SELECT * FROM ticket;
//                                """;
//        try (var connection = ConnectionManager.open();
//             var statement = connection.createStatement()) {
//            var result = statement.executeQuery(sql);
//            while (result.next()) {
//                System.out.println("Номер билета: " + result.getLong("id"));
//                System.out.println("Имя пассажира: " + result.getString("passenger_name"));
//                System.out.println("Цена билета: " + result.getBigDecimal("cost"));
//                System.out.println("-----------------------------------------------------------");
//            }
//        }

        System.out.println(getTicketByFlightId(1L));
    }

    public static List<Long> getTicketByFlightId(Long id)  {
        List<Long> tickets = new ArrayList<>();
        String sqlThickets = """
                 SELECT id FROM ticket t 
                 WHERE t.flight_id = %d;
                 """.formatted(id);
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            var result = statement.executeQuery(sqlThickets);
            while (result.next()) {
                tickets.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
}
