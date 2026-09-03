package com.campus.parking.database;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        try {

            Connection connection =
                    DBConnection.getConnection();

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "DATABASE CONNECTED SUCCESSFULLY"
            );

            System.out.println(
                    "Smart Campus Parking Sentinel"
            );

            System.out.println(
                    "================================"
            );

            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "DATABASE CONNECTION FAILED"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }
}