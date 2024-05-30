package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoTicket;

public class JdbcRunner7 {
    public static void main(String[] args) {
        var ticketDao = DaoTicket.getINSTANCE();
        System.out.println(ticketDao.findById(3L));
    }
}
