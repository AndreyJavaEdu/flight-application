package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;
import com.kamenskiy.io.jdbc.entity.Ticket;

import java.util.Optional;

public class JdbcRunner4 {
    public static void main(String[] args) {
        DaoTicket daoTicket = DaoTicket.getINSTANCE();

        Optional<Ticket> byId = daoTicket.findById(111L);
        if (byId.isPresent()) {
            System.out.println(byId.get());
        } else {
            System.out.println(byId);
        }
    }
}
