package com.kamenskiy.io.jdbc;

import com.kamenskiy.io.jdbc.dao.DaoFlight;

public class JdbcFlightRunner1 {
    public static void main(String[] args) {
        DaoFlight daoFlight = DaoFlight.getInstance();
        System.out.println(daoFlight.findAll());
    }
}
