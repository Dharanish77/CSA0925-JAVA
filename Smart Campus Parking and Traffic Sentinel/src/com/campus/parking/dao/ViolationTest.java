package com.campus.parking.dao;

public class ViolationTest {

    public static void main(String[] args) {

        ViolationDAO dao =
                new ViolationDAO();

        System.out.println(
                "===== VIOLATION REGISTRY ====="
        );

        System.out.println(
                "Total Violations: "
                        + dao.getViolationCount()
        );

        System.out.println(
                "Total Fines: ₹"
                        + String.format(
                        "%.2f",
                        dao.getTotalFines()
                )
        );

        System.out.println(
                "Unpaid Violations: "
                        + dao.getUnpaidCount()
        );

        System.out.println(
                "=============================="
        );
    }
}