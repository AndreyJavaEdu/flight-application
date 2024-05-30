package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;
import com.kamenskiy.io.jdbc.entity.Ticket;

import java.math.BigDecimal;

public class JdbcRunner2 {
    public static void main(String[] args) {
        DaoTicket daoTicket = DaoTicket.getINSTANCE();
        Ticket ticket = new Ticket();
        ticket.setPassportNo("asdf1");
        ticket.setPassengerName("Serious Sam");
//        ticket.setFlight(4L);
        ticket.setSeatNo("D1");
        ticket.setCost(BigDecimal.TEN);

//        System.out.println(daoTicket.save(ticket));
        System.out.println(daoTicket.delete(57L));
    }
}
