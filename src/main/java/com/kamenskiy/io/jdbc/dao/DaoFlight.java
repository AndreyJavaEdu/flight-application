package com.kamenskiy.io.jdbc.dao;

import com.kamenskiy.io.jdbc.entity.Flight;
import com.kamenskiy.io.jdbc.entity.FlightStatus;
import com.kamenskiy.io.jdbc.entity.Ticket;
import com.kamenskiy.io.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DaoFlight implements Dao<Long, Flight> {
    private static final DaoFlight INSTANCE = new DaoFlight();

    private static final String FIND_ALL_SQL = """
            SELECT id, 
                   flight_no, 
                   departure_date, 
                   departure_airport_code, 
                   arrival_date, 
                   arrival_airport_code, 
                   aircraft_id, 
                   status
            FROM flight;
                                """;

    public static DaoFlight getInstance() {
        return INSTANCE;
    }

    private DaoFlight() {
    }

    @Override
    public boolean update(Flight flight) {
        return false;
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flights.add(
                        buildFlight(resultSet)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(resultSet.getLong("id"),
                resultSet.getString("flight_no"),
                new Date(resultSet.getDate("departure_date").getTime()).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime() ,
                resultSet.getString("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getString("arrival_airport_code"),
                resultSet.getInt("aircraft_id"),
                FlightStatus.valueOf(resultSet.getString("status"))
        );
    }

    @Override
    public Flight save(Flight flight) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
