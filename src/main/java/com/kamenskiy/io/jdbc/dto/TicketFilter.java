package com.kamenskiy.io.jdbc.dto;

public record TicketFilter(String passengerName,
                           String seatNo,
                           int limit,
                           int offset) {
}
