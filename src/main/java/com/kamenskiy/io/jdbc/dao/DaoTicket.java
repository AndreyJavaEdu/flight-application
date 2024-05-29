package com.kamenskiy.io.jdbc.dao;

import com.kamenskiy.io.jdbc.entity.Ticket;
import com.kamenskiy.io.jdbc.exceptions.DaoException;
import com.kamenskiy.io.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoTicket {
    private static final DaoTicket INSTANCE = new DaoTicket();
    private static final String SAVE_SQL = """
            INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES(?, ?, ?, ?, ?);
                                          """;
    private static final String DELETE_SQL = """
            DELETE FROM ticket 
            WHERE id = ?;
                                          """;
    private static final String FIND_ALL_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost FROM ticket;
                                          """;

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getLong("id"),
                        resultSet.getString("passport_no"),
                        resultSet.getString("passenger_name"),
                        resultSet.getLong("flight_id"),
                        resultSet.getString("seat_no"),
                        resultSet.getBigDecimal("cost")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Ticket save(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticket.getPassportNo());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlightId());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getCost());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ticket;
    }

    public boolean delete(Long ticketId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, ticketId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static DaoTicket getINSTANCE() {
        return INSTANCE;
    }

    private DaoTicket() {
    }
}
