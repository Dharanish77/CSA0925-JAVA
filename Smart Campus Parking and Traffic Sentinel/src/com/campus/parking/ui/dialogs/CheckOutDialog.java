package com.campus.parking.ui.dialogs;

import com.campus.parking.database.DBConnection;
import com.campus.parking.dao.SessionDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class CheckOutDialog extends JDialog {

    private JTextField vehicleField;
    private JLabel vehicleInfo;
    private JLabel entryInfo;
    private JLabel durationInfo;
    private JLabel amountInfo;

    private JComboBox<String> paymentMethod;

    private final SessionDAO sessionDAO;

    private final Color ROOT = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);
    private final Color BLUE = new Color(56, 189, 248);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);

    public CheckOutDialog(Frame parent) {

        super(parent, "Vehicle Exit & Payment", true);

        sessionDAO = new SessionDAO();

        setSize(560, 600);
        setLocationRelativeTo(parent);
        setResizable(false);

        createInterface();
    }

    private void createInterface() {

        JPanel main = new JPanel(
                new BorderLayout()
        );

        main.setBackground(ROOT);

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        JPanel header = new JPanel();

        header.setBackground(ROOT);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("VEHICLE EXIT & PAYMENT");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Calculate parking duration and settle payment"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(MUTED);

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);

        main.add(
                header,
                BorderLayout.NORTH
        );

        JPanel content =
                new JPanel(
                        new GridBagLayout()
                );

        content.setBackground(CARD);

        content.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8, 8, 8, 8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        int row = 0;

        JLabel vehicleLabel =
                createLabel(
                        "Vehicle Number"
                );

        vehicleField =
                createTextField();

        JButton searchButton =
                new JButton("SEARCH");

        styleButton(
                searchButton,
                BLUE
        );

        JPanel vehiclePanel =
                new JPanel(
                        new BorderLayout(8, 0)
                );

        vehiclePanel.setBackground(CARD);

        vehiclePanel.add(
                vehicleField,
                BorderLayout.CENTER
        );

        vehiclePanel.add(
                searchButton,
                BorderLayout.EAST
        );

        addRow(
                content,
                gbc,
                row++,
                vehicleLabel,
                vehiclePanel
        );

        vehicleInfo =
                createInfoLabel(
                        "Vehicle: -"
                );

        entryInfo =
                createInfoLabel(
                        "Entry Time: -"
                );

        durationInfo =
                createInfoLabel(
                        "Duration: -"
                );

        amountInfo =
                createInfoLabel(
                        "Parking Amount: ₹0.00"
                );

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;

        content.add(
                vehicleInfo,
                gbc
        );

        gbc.gridy = row++;

        content.add(
                entryInfo,
                gbc
        );

        gbc.gridy = row++;

        content.add(
                durationInfo,
                gbc
        );

        gbc.gridy = row++;

        amountInfo.setForeground(GREEN);

        amountInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        content.add(
                amountInfo,
                gbc
        );

        JLabel paymentLabel =
                createLabel(
                        "Payment Method"
                );

        paymentMethod =
                new JComboBox<>(
                        new String[]{
                                "Cash",
                                "UPI",
                                "Card"
                        }
                );

        styleComboBox(
                paymentMethod
        );

        gbc.gridwidth = 1;

        addRow(
                content,
                gbc,
                row++,
                paymentLabel,
                paymentMethod
        );

        main.add(
                content,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        bottom.setBackground(ROOT);

        JButton cancel =
                new JButton("CANCEL");

        styleButton(
                cancel,
                BORDER
        );

        cancel.addActionListener(
                e -> dispose()
        );

        JButton checkout =
                new JButton(
                        "✓  COMPLETE EXIT"
                );

        styleButton(
                checkout,
                GREEN
        );

        checkout.addActionListener(
                e -> completeExit()
        );

        searchButton.addActionListener(
                e -> searchVehicle()
        );

        vehicleField.addActionListener(
                e -> searchVehicle()
        );

        bottom.add(cancel);
        bottom.add(checkout);

        main.add(
                bottom,
                BorderLayout.SOUTH
        );

        setContentPane(main);
    }

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        label.setForeground(MUTED);

        return label;
    }

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(TEXT);

        field.setBackground(ROOT);

        field.setCaretColor(BLUE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );

        return field;
    }

    private void styleComboBox(
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        combo.setForeground(TEXT);

        combo.setBackground(ROOT);

        combo.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );
    }

    private void styleButton(
            JButton button,
            Color background
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(TEXT);

        button.setBackground(background);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 18, 10, 18
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            JLabel label,
            JComponent component
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        panel.add(
                label,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        panel.add(
                component,
                gbc
        );
    }

    private void searchVehicle() {

        String vehicleNumber =
                vehicleField
                        .getText()
                        .trim()
                        .toUpperCase();

        if (vehicleNumber.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a vehicle number.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "SELECT ps.session_id, " +
                        "ps.entry_time, " +
                        "ps.slot_id, " +
                        "v.vehicle_number, " +
                        "p.slot_number " +
                        "FROM parking_sessions ps " +
                        "JOIN vehicles v " +
                        "ON ps.vehicle_id = v.vehicle_id " +
                        "JOIN parking_slots p " +
                        "ON ps.slot_id = p.slot_id " +
                        "WHERE v.vehicle_number = ? " +
                        "AND ps.session_status = 'Active' " +
                        "LIMIT 1";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    vehicleNumber
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    int sessionId =
                            rs.getInt(
                                    "session_id"
                            );

                    Timestamp timestamp =
                            rs.getTimestamp(
                                    "entry_time"
                            );

                    int slotId =
                            rs.getInt(
                                    "slot_id"
                            );

                    String number =
                            rs.getString(
                                    "vehicle_number"
                            );

                    String slotNumber =
                            rs.getString(
                                    "slot_number"
                            );

                    LocalDateTime entry =
                            timestamp
                                    .toLocalDateTime();

                    LocalDateTime now =
                            LocalDateTime.now();

                    long minutes =
                            Duration
                                    .between(
                                            entry,
                                            now
                                    )
                                    .toMinutes();

                    double fee =
                            sessionDAO.calculateFee(
                                    minutes
                            );

                    vehicleInfo.setText(
                            "Vehicle: "
                                    + number
                                    + "    |    Slot: "
                                    + slotNumber
                    );

                    entryInfo.setText(
                            "Entry Time: "
                                    + entry
                    );

                    durationInfo.setText(
                            "Duration: "
                                    + formatDuration(
                                    minutes
                            )
                    );

                    amountInfo.setText(
                            String.format(
                                    "Parking Amount: ₹%.2f",
                                    fee
                            )
                    );

                    vehicleInfo.putClientProperty(
                            "sessionId",
                            sessionId
                    );

                    vehicleInfo.putClientProperty(
                            "slotId",
                            slotId
                    );

                    vehicleInfo.putClientProperty(
                            "entryTime",
                            entry
                    );

                    vehicleInfo.putClientProperty(
                            "fee",
                            fee
                    );

                } else {

                    clearInformation();

                    JOptionPane.showMessageDialog(
                            this,
                            "No active parking session found for "
                                    + vehicleNumber,
                            "Vehicle Not Found",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String formatDuration(
            long minutes
    ) {

        long hours =
                minutes / 60;

        long remaining =
                minutes % 60;

        if (hours == 0) {

            return remaining
                    + " minutes";
        }

        return hours
                + " hour(s) "
                + remaining
                + " minute(s)";
    }

    private void completeExit() {

        Object sessionObject =
                vehicleInfo.getClientProperty(
                        "sessionId"
                );

        Object slotObject =
                vehicleInfo.getClientProperty(
                        "slotId"
                );

        Object feeObject =
                vehicleInfo.getClientProperty(
                        "fee"
                );

        if (sessionObject == null ||
                slotObject == null ||
                feeObject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search an active vehicle first.",
                    "Vehicle Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int sessionId =
                (Integer) sessionObject;

        int slotId =
                (Integer) slotObject;

        double fee =
                (Double) feeObject;

        String method =
                String.valueOf(
                        paymentMethod.getSelectedItem()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Parking Amount: ₹"
                                + String.format(
                                "%.2f",
                                fee
                        )
                                + "\nPayment Method: "
                                + method
                                + "\n\n"
                                + "Complete vehicle exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }

        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();

            con.setAutoCommit(false);

            String updateSession =
                    "UPDATE parking_sessions " +
                            "SET exit_time = NOW(), " +
                            "duration_minutes = TIMESTAMPDIFF(" +
                            "MINUTE, entry_time, NOW()), " +
                            "parking_amount = ?, " +
                            "session_status = 'Completed' " +
                            "WHERE session_id = ?";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateSession
                            )
            ) {

                ps.setDouble(
                        1,
                        fee
                );

                ps.setInt(
                        2,
                        sessionId
                );

                ps.executeUpdate();
            }

            String updateSlot =
                    "UPDATE parking_slots " +
                            "SET slot_status = 'Available' " +
                            "WHERE slot_id = ?";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateSlot
                            )
            ) {

                ps.setInt(
                        1,
                        slotId
                );

                ps.executeUpdate();
            }

            String paymentSql =
                    "INSERT INTO payments " +
                            "(session_id, amount, payment_method, " +
                            "payment_status) " +
                            "VALUES (?, ?, ?, 'Paid')";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    paymentSql
                            )
            ) {

                ps.setInt(
                        1,
                        sessionId
                );

                ps.setDouble(
                        2,
                        fee
                );

                ps.setString(
                        3,
                        method
                );

                ps.executeUpdate();
            }

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "VEHICLE EXIT SUCCESSFUL\n\n"
                            + "Session ID: "
                            + sessionId
                            + "\n"
                            + "Amount Paid: ₹"
                            + String.format(
                            "%.2f",
                            fee
                    )
                            + "\n"
                            + "Payment: "
                            + method
                            + "\n"
                            + "Slot Status: AVAILABLE",
                    "Payment Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (SQLException e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (SQLException ignored) {
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Exit processing failed:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

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

    private void clearInformation() {

        vehicleInfo.setText(
                "Vehicle: -"
        );

        entryInfo.setText(
                "Entry Time: -"
        );

        durationInfo.setText(
                "Duration: -"
        );

        amountInfo.setText(
                "Parking Amount: ₹0.00"
        );

        vehicleInfo.putClientProperty(
                "sessionId",
                null
        );

        vehicleInfo.putClientProperty(
                "slotId",
                null
        );

        vehicleInfo.putClientProperty(
                "entryTime",
                null
        );

        vehicleInfo.putClientProperty(
                "fee",
                null
        );
    }
}