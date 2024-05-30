package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;
import com.kamenskiy.io.jdbc.dto.TicketFilter;
import com.kamenskiy.io.jdbc.entity.Ticket;

import java.util.List;

public class JdbcRunner6 {
    public static void main(String[] args) {
        DaoTicket daoTicket = DaoTicket.getINSTANCE();
        var filter = new TicketFilter(null, null, 5, 0);
        List<Ticket> all = daoTicket.findAll(filter);
        System.out.println(all);
    }
}
