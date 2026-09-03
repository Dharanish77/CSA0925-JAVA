package com.campus.parking.dao;

import com.campus.parking.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;

public class SessionDAO {

    public int createSession(int vehicleId, int slotId) {

        String sql = "INSERT INTO parking_sessions " +
                "(vehicle_id, slot_id, entry_time, session_status) " +
                "VALUES (?, ?, NOW(), 'Active')";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setInt(1, vehicleId);
            ps.setInt(2, slotId);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Session creation error: " + e.getMessage());
        }

        return -1;
    }

    public boolean updateSlotStatus(int slotId, String status) {

        String sql = "UPDATE parking_slots " +
                "SET slot_status = ? " +
                "WHERE slot_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, slotId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Slot update error: " + e.getMessage());
            return false;
        }
    }

    public int findSlotId(String slotNumber) {

        String sql = "SELECT slot_id FROM parking_slots " +
                "WHERE slot_number = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, slotNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("slot_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Slot search error: " + e.getMessage());
        }

        return -1;
    }

    public int findVehicleId(String vehicleNumber) {

        String sql = "SELECT vehicle_id FROM vehicles " +
                "WHERE vehicle_number = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, vehicleNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("vehicle_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Vehicle search error: " + e.getMessage());
        }

        return -1;
    }

    public boolean createVehicle(
            int userId,
            String vehicleNumber,
            String vehicleType
    ) {

        String sql = "INSERT INTO vehicles " +
                "(user_id, vehicle_number, vehicle_type) " +
                "VALUES (?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ps.setString(2, vehicleNumber);
            ps.setString(3, vehicleType);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Vehicle creation error: " + e.getMessage());
            return false;
        }
    }

    public boolean vehicleExists(String vehicleNumber) {

        String sql = "SELECT COUNT(*) FROM vehicles " +
                "WHERE vehicle_number = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, vehicleNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Vehicle check error: " + e.getMessage());
        }

        return false;
    }

    public int getActiveSessionCount() {

        String sql = "SELECT COUNT(*) FROM parking_sessions " +
                "WHERE session_status = 'Active'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Active session error: " + e.getMessage());
        }

        return 0;
    }

    public double calculateFee(long minutes) {

        if (minutes <= 0) {
            return 2.00;
        }

        long hours = (minutes + 59) / 60;

        return 2.00 + (hours * 1.50);
    }

    public double calculateFee(
            LocalDateTime entryTime,
            LocalDateTime exitTime
    ) {

        if (entryTime == null || exitTime == null) {
            return 2.00;
        }

        if (exitTime.isBefore(entryTime)) {
            return 2.00;
        }

        long minutes = Duration
                .between(entryTime, exitTime)
                .toMinutes();

        return calculateFee(minutes);
    }

    public boolean isSlotAvailable(int slotId) {

        String sql = "SELECT slot_status FROM parking_slots " +
                "WHERE slot_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, slotId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String status =
                            rs.getString("slot_status");

                    return "Available".equalsIgnoreCase(status);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Slot availability error: " + e.getMessage()
            );
        }

        return false;
    }

    public boolean hasActiveSessionForVehicle(int vehicleId) {

        String sql = "SELECT COUNT(*) FROM parking_sessions " +
                "WHERE vehicle_id = ? " +
                "AND session_status = 'Active'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, vehicleId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Active vehicle session error: "
                            + e.getMessage()
            );
        }

        return false;
    }

    public boolean checkInVehicle(
            int vehicleId,
            int slotId
    ) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            String checkSlot =
                    "SELECT slot_status FROM parking_slots " +
                            "WHERE slot_id = ? FOR UPDATE";

            try (PreparedStatement ps =
                         con.prepareStatement(checkSlot)) {

                ps.setInt(1, slotId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        con.rollback();
                        return false;
                    }

                    String status =
                            rs.getString("slot_status");

                    if (!"Available".equalsIgnoreCase(status)) {
                        con.rollback();
                        return false;
                    }
                }
            }

            String sessionSql =
                    "INSERT INTO parking_sessions " +
                            "(vehicle_id, slot_id, entry_time, session_status) " +
                            "VALUES (?, ?, NOW(), 'Active')";

            try (PreparedStatement ps =
                         con.prepareStatement(sessionSql)) {

                ps.setInt(1, vehicleId);
                ps.setInt(2, slotId);

                ps.executeUpdate();
            }

            String slotSql =
                    "UPDATE parking_slots " +
                            "SET slot_status = 'Occupied' " +
                            "WHERE slot_id = ?";

            try (PreparedStatement ps =
                         con.prepareStatement(slotSql)) {

                ps.setInt(1, slotId);

                ps.executeUpdate();
            }

            con.commit();

            return true;

        } catch (SQLException e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ignored) {
            }

            System.out.println(
                    "Check-in error: " + e.getMessage()
            );

            return false;

        } finally {

            try {

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (SQLException ignored) {
            }
        }
    }
}