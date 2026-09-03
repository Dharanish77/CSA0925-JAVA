package com.campus.parking.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseBootstrap {

    public static void initialize() {

        String[] queries = {

                """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    phone VARCHAR(15) NOT NULL,
                    user_type ENUM(
                        'Student',
                        'Faculty',
                        'Staff',
                        'Visitor'
                    ) NOT NULL
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS vehicles (
                    vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    vehicle_number VARCHAR(20) NOT NULL UNIQUE,
                    vehicle_type VARCHAR(30) NOT NULL,
                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS parking_zones (
                    zone_id INT PRIMARY KEY AUTO_INCREMENT,
                    zone_name VARCHAR(50) NOT NULL,
                    location VARCHAR(100)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS parking_slots (
                    slot_id INT PRIMARY KEY AUTO_INCREMENT,
                    zone_id INT NOT NULL,
                    slot_number VARCHAR(20) NOT NULL,
                    slot_status ENUM(
                        'Available',
                        'Occupied',
                        'Reserved'
                    ) DEFAULT 'Available',
                    FOREIGN KEY (zone_id)
                    REFERENCES parking_zones(zone_id)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS reservations (
                    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    vehicle_id INT NOT NULL,
                    slot_id INT NOT NULL,
                    reservation_date DATETIME
                        DEFAULT CURRENT_TIMESTAMP,
                    status ENUM(
                        'Active',
                        'Completed',
                        'Cancelled'
                    ) DEFAULT 'Active',
                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id),
                    FOREIGN KEY (vehicle_id)
                    REFERENCES vehicles(vehicle_id),
                    FOREIGN KEY (slot_id)
                    REFERENCES parking_slots(slot_id)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS parking_sessions (
                    session_id INT PRIMARY KEY AUTO_INCREMENT,
                    vehicle_id INT NOT NULL,
                    slot_id INT NOT NULL,
                    entry_time DATETIME
                        DEFAULT CURRENT_TIMESTAMP,
                    exit_time DATETIME NULL,
                    duration_minutes INT DEFAULT 0,
                    parking_amount DECIMAL(10,2)
                        DEFAULT 0.00,
                    session_status ENUM(
                        'Active',
                        'Completed'
                    ) DEFAULT 'Active',
                    FOREIGN KEY (vehicle_id)
                    REFERENCES vehicles(vehicle_id),
                    FOREIGN KEY (slot_id)
                    REFERENCES parking_slots(slot_id)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS payments (
                    payment_id INT PRIMARY KEY AUTO_INCREMENT,
                    session_id INT NOT NULL,
                    amount DECIMAL(10,2) NOT NULL,
                    payment_method ENUM(
                        'Cash',
                        'UPI',
                        'Card'
                    ) NOT NULL,
                    payment_date DATETIME
                        DEFAULT CURRENT_TIMESTAMP,
                    payment_status ENUM(
                        'Paid',
                        'Pending'
                    ) DEFAULT 'Paid',
                    FOREIGN KEY (session_id)
                    REFERENCES parking_sessions(session_id)
                )
                """,

                """
                CREATE TABLE IF NOT EXISTS violations (
                    violation_id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT,
                    vehicle_id INT NOT NULL,
                    violation_type VARCHAR(100) NOT NULL,
                    fine_amount DECIMAL(10,2)
                        DEFAULT 0.00,
                    violation_date DATETIME
                        DEFAULT CURRENT_TIMESTAMP,
                    status VARCHAR(20) NOT NULL,
                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id),
                    FOREIGN KEY (vehicle_id)
                    REFERENCES vehicles(vehicle_id)
                )
                """
        };

        try (
                Connection connection =
                        DBConnection.getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            for (String query : queries) {
                statement.executeUpdate(query);
            }

            System.out.println(
                    "Database tables created successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Database Bootstrap Error:"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }
}