package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;
import com.kamenskiy.io.jdbc.entity.Ticket;

import java.util.List;

public class JdbcRunner3 {
    public static void main(String[] args) {
        DaoTicket daoTicket = DaoTicket.getINSTANCE();
        List<Ticket> all = daoTicket.findAll();
        System.out.println(all);
    }
}
