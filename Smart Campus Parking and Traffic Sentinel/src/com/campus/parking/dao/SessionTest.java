package com.campus.parking.dao;

import java.time.LocalDateTime;

public class SessionTest {

    public static void main(String[] args) {

        SessionDAO dao = new SessionDAO();

        System.out.println("===== PARKING SESSION TEST =====");

        int activeSessions =
                dao.getActiveSessionCount();

        System.out.println(
                "Active Parking Sessions: "
                        + activeSessions
        );

        LocalDateTime entry =
                LocalDateTime.now().minusMinutes(90);

        LocalDateTime exit =
                LocalDateTime.now();

        double fee =
                dao.calculateFee(
                        entry,
                        exit
                );

        System.out.printf(
                "Sample 90 Minute Fee: ₹%.2f%n",
                fee
        );

        System.out.println(
                "================================"
        );
    }
}