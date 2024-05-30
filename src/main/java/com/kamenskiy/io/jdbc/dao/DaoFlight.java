package com.kamenskiy.io.jdbc.dao;

public class DaoFlight implements Dao<Long, Flight> {
    private static final DaoFlight INSTANCE = new DaoFlight();

    public static DaoFlight getInstance() {
        return INSTANCE;
    }

    private DaoFlight() {
    }
}
