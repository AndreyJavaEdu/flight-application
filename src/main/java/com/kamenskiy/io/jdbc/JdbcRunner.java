package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
//        2020-06-14 2020-05-03
//        System.out.println(getTicketByFlightId(6L));
        System.out.println(getFlightsBetween(LocalDate.of(2020, 2, 14).atStartOfDay(),
                LocalDate.of(2020, 7, 3).atStartOfDay()));
        getMetadata();
    }

    public static List<Long> getTicketByFlightId(Long flightId) throws SQLException {
        List<Long> tickets = new ArrayList<>();
        String sqlThickets = """
                SELECT id FROM ticket t 
                WHERE t.flight_id = ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sqlThickets);) {
            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> flights = new ArrayList<>();
        String sqlAllFlights = """
                SELECT * FROM flight
                WHERE departure_date BETWEEN ? AND ?;
                                """;
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sqlAllFlights)) {
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setFetchSize(2);
            preparedStatement.setMaxRows(2);
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);
            var result = preparedStatement.executeQuery();
            while (result.next()) {
                flights.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    public static void getMetadata() throws SQLException {
        try (Connection connection = ConnectionManager.get();) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));
            }
        }
    }
}
