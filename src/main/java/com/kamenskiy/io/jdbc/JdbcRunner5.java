package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;
import com.kamenskiy.io.jdbc.entity.Ticket;

public class JdbcRunner5 {
    public static void main(String[] args) {
        DaoTicket daoTicket = DaoTicket.getINSTANCE();
        Ticket ticket = daoTicket.findById(11L).get();
        System.out.println(ticket);
        ticket.setSeatNo("DL2");
        System.out.println(daoTicket.update(ticket));
        ticket = daoTicket.findById(11L).get();
        System.out.println(ticket);
    }
}
