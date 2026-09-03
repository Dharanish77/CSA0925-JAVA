package com.campus.parking.dao;

import com.campus.parking.database.DBConnection;
import com.campus.parking.model.ParkingSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {

    public List<ParkingSlot> getAllSlots() {

        List<ParkingSlot> slots = new ArrayList<>();

        String sql = """
                SELECT slot_id, zone_id, slot_number, slot_status
                FROM parking_slots
                ORDER BY zone_id, slot_id
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                ParkingSlot slot = new ParkingSlot();

                slot.setSlotId(rs.getInt("slot_id"));
                slot.setZoneId(rs.getInt("zone_id"));
                slot.setSlotNumber(rs.getString("slot_number"));
                slot.setSlotStatus(rs.getString("slot_status"));

                slots.add(slot);
            }

        } catch (SQLException e) {
            System.out.println("Error loading parking slots: " + e.getMessage());
        }

        return slots;
    }

    public ParkingSlot getSlotById(int slotId) {

        String sql = """
                SELECT slot_id, zone_id, slot_number, slot_status
                FROM parking_slots
                WHERE slot_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, slotId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ParkingSlot slot = new ParkingSlot();

                    slot.setSlotId(rs.getInt("slot_id"));
                    slot.setZoneId(rs.getInt("zone_id"));
                    slot.setSlotNumber(rs.getString("slot_number"));
                    slot.setSlotStatus(rs.getString("slot_status"));

                    return slot;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding slot: " + e.getMessage());
        }

        return null;
    }

    public ParkingSlot getSlotByNumber(String slotNumber) {

        String sql = """
                SELECT slot_id, zone_id, slot_number, slot_status
                FROM parking_slots
                WHERE slot_number = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, slotNumber);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ParkingSlot slot = new ParkingSlot();

                    slot.setSlotId(rs.getInt("slot_id"));
                    slot.setZoneId(rs.getInt("zone_id"));
                    slot.setSlotNumber(rs.getString("slot_number"));
                    slot.setSlotStatus(rs.getString("slot_status"));

                    return slot;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding slot by number: " + e.getMessage());
        }

        return null;
    }

    public ParkingSlot getFirstAvailableSlot() {

        String sql = """
                SELECT slot_id, zone_id, slot_number, slot_status
                FROM parking_slots
                WHERE slot_status = 'Available'
                ORDER BY zone_id, slot_id
                LIMIT 1
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                ParkingSlot slot = new ParkingSlot();

                slot.setSlotId(rs.getInt("slot_id"));
                slot.setZoneId(rs.getInt("zone_id"));
                slot.setSlotNumber(rs.getString("slot_number"));
                slot.setSlotStatus(rs.getString("slot_status"));

                return slot;
            }

        } catch (SQLException e) {
            System.out.println("Error finding available slot: " + e.getMessage());
        }

        return null;
    }

    public boolean updateSlotStatus(int slotId, String status) {

        String sql = """
                UPDATE parking_slots
                SET slot_status = ?
                WHERE slot_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, slotId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating slot: " + e.getMessage());
            return false;
        }
    }

    public boolean occupySlot(int slotId) {

        String sql = """
                UPDATE parking_slots
                SET slot_status = 'Occupied'
                WHERE slot_id = ?
                AND slot_status = 'Available'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, slotId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error occupying slot: " + e.getMessage());
            return false;
        }
    }

    public boolean releaseSlot(int slotId) {

        String sql = """
                UPDATE parking_slots
                SET slot_status = 'Available'
                WHERE slot_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, slotId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error releasing slot: " + e.getMessage());
            return false;
        }
    }

    public List<ParkingSlot> getAvailableSlots() {

        List<ParkingSlot> slots = new ArrayList<>();

        String sql = """
                SELECT slot_id, zone_id, slot_number, slot_status
                FROM parking_slots
                WHERE slot_status = 'Available'
                ORDER BY zone_id, slot_id
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                ParkingSlot slot = new ParkingSlot();

                slot.setSlotId(rs.getInt("slot_id"));
                slot.setZoneId(rs.getInt("zone_id"));
                slot.setSlotNumber(rs.getString("slot_number"));
                slot.setSlotStatus(rs.getString("slot_status"));

                slots.add(slot);
            }

        } catch (SQLException e) {
            System.out.println("Error loading available slots: " + e.getMessage());
        }

        return slots;
    }

    public int getAvailableCount() {

        String sql = """
                SELECT COUNT(*)
                FROM parking_slots
                WHERE slot_status = 'Available'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error counting available slots: " + e.getMessage());
        }

        return 0;
    }

    public int getOccupiedCount() {

        String sql = """
                SELECT COUNT(*)
                FROM parking_slots
                WHERE slot_status = 'Occupied'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error counting occupied slots: " + e.getMessage());
        }

        return 0;
    }

    public int getReservedCount() {

        String sql = """
                SELECT COUNT(*)
                FROM parking_slots
                WHERE slot_status = 'Reserved'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error counting reserved slots: " + e.getMessage());
        }

        return 0;
    }
}