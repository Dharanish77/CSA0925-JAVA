package com.campus.parking.dao;

import com.campus.parking.database.DBConnection;
import com.campus.parking.model.Violation;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViolationDAO {

    public boolean addViolation(Violation violation) {

        String sql = """
                INSERT INTO violations
                (user_id, vehicle_id, violation_type,
                 fine_amount, violation_date, status)
                VALUES (?, ?, ?, ?, NOW(), ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            if (violation.getUserId() > 0) {
                ps.setInt(1, violation.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setInt(2, violation.getVehicleId());

            ps.setString(
                    3,
                    violation.getViolationType()
            );

            ps.setDouble(
                    4,
                    violation.getFineAmount()
            );

            ps.setString(
                    5,
                    violation.getStatus()
            );

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {

            String message =
                    "DATABASE ERROR\n\n"
                            + e.getMessage();

            JOptionPane.showMessageDialog(
                    null,
                    message,
                    "Violation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.out.println(
                    "VIOLATION DATABASE ERROR: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public List<Violation> getAllViolations() {

        List<Violation> violations =
                new ArrayList<>();

        String sql = """
                SELECT violation_id,
                       user_id,
                       vehicle_id,
                       violation_type,
                       fine_amount,
                       violation_date,
                       status
                FROM violations
                ORDER BY violation_id DESC
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Violation violation =
                        new Violation();

                violation.setViolationId(
                        rs.getInt("violation_id")
                );

                violation.setUserId(
                        rs.getInt("user_id")
                );

                violation.setVehicleId(
                        rs.getInt("vehicle_id")
                );

                violation.setViolationType(
                        rs.getString("violation_type")
                );

                violation.setFineAmount(
                        rs.getDouble("fine_amount")
                );

                Timestamp timestamp =
                        rs.getTimestamp(
                                "violation_date"
                        );

                if (timestamp != null) {

                    violation.setViolationDate(
                            timestamp.toLocalDateTime()
                    );
                }

                violation.setStatus(
                        rs.getString("status")
                );

                violations.add(violation);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error loading violations:\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.out.println(
                    "ERROR LOADING VIOLATIONS: "
                            + e.getMessage()
            );
        }

        return violations;
    }

    public boolean updateStatus(
            int violationId,
            String status
    ) {

        String sql = """
                UPDATE violations
                SET status = ?
                WHERE violation_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    violationId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error updating violation:\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    public boolean deleteViolation(
            int violationId
    ) {

        String sql = """
                DELETE FROM violations
                WHERE violation_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    violationId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error deleting violation:\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    public double getTotalFines() {

        String sql = """
                SELECT COALESCE(
                    SUM(fine_amount), 0
                )
                FROM violations
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR CALCULATING FINES: "
                            + e.getMessage()
            );
        }

        return 0.0;
    }

    public int getViolationCount() {

        String sql =
                "SELECT COUNT(*) FROM violations";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR COUNTING VIOLATIONS: "
                            + e.getMessage()
            );
        }

        return 0;
    }

    public int getUnpaidCount() {

        String sql = """
                SELECT COUNT(*)
                FROM violations
                WHERE status = 'Unpaid'
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR COUNTING UNPAID VIOLATIONS: "
                            + e.getMessage()
            );
        }

        return 0;
    }
}