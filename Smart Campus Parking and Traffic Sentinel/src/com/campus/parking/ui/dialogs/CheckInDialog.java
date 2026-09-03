package com.campus.parking.ui.dialogs;

import com.campus.parking.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CheckInDialog extends JDialog {

    private final Color BG = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color BLUE = new Color(56, 189, 248);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color WHITE = new Color(241, 245, 249);
    private final Color MUTED = new Color(148, 163, 184);

    private JTextField vehicleNumberField;
    private JTextField ownerNameField;
    private JTextField phoneField;
    private JComboBox<String> vehicleTypeBox;
    private JComboBox<String> slotBox;

    public CheckInDialog(Frame parent) {
        super(parent, "Vehicle Check-In", true);

        setSize(560, 650);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildInterface();
        loadAvailableSlots();
    }

    private void buildInterface() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 20, 30)
        );

        JLabel title = new JLabel("VEHICLE CHECK-IN");
        title.setForeground(WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
                "Register vehicle entry and assign a parking slot"
        );
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(BG);

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(7));
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);

        root.add(header, BorderLayout.NORTH);

        JPanel formCard = new JPanel();
        formCard.setBackground(CARD);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(25, 30, 25, 30)
                )
        );

        formCard.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);

        int row = 0;

        JLabel vehicleLabel = createLabel("VEHICLE NUMBER");
        gbc.gridy = row++;
        formCard.add(vehicleLabel, gbc);

        vehicleNumberField = createTextField("Example: TN38AB1234");
        gbc.gridy = row++;
        formCard.add(vehicleNumberField, gbc);

        JLabel ownerLabel = createLabel("OWNER / VISITOR NAME");
        gbc.gridy = row++;
        formCard.add(ownerLabel, gbc);

        ownerNameField = createTextField("Enter owner name");
        gbc.gridy = row++;
        formCard.add(ownerNameField, gbc);

        JLabel phoneLabel = createLabel("PHONE NUMBER");
        gbc.gridy = row++;
        formCard.add(phoneLabel, gbc);

        phoneField = createTextField("Enter phone number");
        gbc.gridy = row++;
        formCard.add(phoneField, gbc);

        JLabel typeLabel = createLabel("VEHICLE TYPE");
        gbc.gridy = row++;
        formCard.add(typeLabel, gbc);

        vehicleTypeBox = new JComboBox<>(
                new String[]{
                        "Car",
                        "Bike",
                        "Scooter",
                        "Electric Car",
                        "Other"
                }
        );

        styleComboBox(vehicleTypeBox);

        gbc.gridy = row++;
        formCard.add(vehicleTypeBox, gbc);

        JLabel slotLabel = createLabel("PARKING SLOT");
        gbc.gridy = row++;
        formCard.add(slotLabel, gbc);

        slotBox = new JComboBox<>();
        styleComboBox(slotBox);

        gbc.gridy = row++;
        formCard.add(slotBox, gbc);

        root.add(formCard, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 15));
        bottom.setBackground(BG);

        JButton cancelButton = createButton(
                "CANCEL",
                new Color(51, 65, 85)
        );

        JButton checkInButton = createButton(
                "CHECK IN VEHICLE",
                GREEN
        );

        cancelButton.addActionListener(e -> dispose());

        checkInButton.addActionListener(e -> processCheckIn());

        bottom.add(cancelButton);
        bottom.add(checkInButton);

        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(BLUE);
        label.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        return label;
    }

    private JTextField createTextField(String placeholder) {

        JTextField field = new JTextField();

        field.setPreferredSize(new Dimension(400, 42));

        field.setBackground(new Color(15, 23, 42));
        field.setForeground(WHITE);
        field.setCaretColor(WHITE);

        field.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(0, 12, 0, 12)
                )
        );

        return field;
    }

    private void styleComboBox(JComboBox<String> box) {

        box.setPreferredSize(new Dimension(400, 42));

        box.setBackground(new Color(15, 23, 42));
        box.setForeground(WHITE);

        box.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        box.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
    }

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setPreferredSize(
                new Dimension(175, 45)
        );

        button.setBackground(background);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private void loadAvailableSlots() {

        slotBox.removeAllItems();

        String sql =
                "SELECT slot_id, slot_number " +
                        "FROM parking_slots " +
                        "WHERE slot_status = 'Available' " +
                        "ORDER BY zone_id, slot_number";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                int slotId =
                        rs.getInt("slot_id");

                String slotNumber =
                        rs.getString("slot_number");

                slotBox.addItem(
                        slotId + " - " + slotNumber
                );
            }

            if (slotBox.getItemCount() == 0) {

                slotBox.addItem("NO AVAILABLE SLOTS");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load parking slots.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void processCheckIn() {

        String vehicleNumber =
                vehicleNumberField.getText()
                        .trim()
                        .toUpperCase();

        String ownerName =
                ownerNameField.getText()
                        .trim();

        String phone =
                phoneField.getText()
                        .trim();

        String vehicleType =
                (String) vehicleTypeBox.getSelectedItem();

        if (vehicleNumber.isEmpty()) {

            showError(
                    "Please enter vehicle number."
            );

            vehicleNumberField.requestFocus();
            return;
        }

        if (ownerName.isEmpty()) {

            showError(
                    "Please enter owner / visitor name."
            );

            ownerNameField.requestFocus();
            return;
        }

        if (phone.isEmpty()) {

            showError(
                    "Please enter phone number."
            );

            phoneField.requestFocus();
            return;
        }

        if (!phone.matches("\\d{10}")) {

            showError(
                    "Phone number must contain exactly 10 digits."
            );

            phoneField.requestFocus();
            return;
        }

        if (slotBox.getItemCount() == 0 ||
                slotBox.getSelectedItem() == null ||
                slotBox.getSelectedItem()
                        .toString()
                        .equals("NO AVAILABLE SLOTS")) {

            showError(
                    "No parking slot is available."
            );

            return;
        }

        String selectedSlot =
                slotBox.getSelectedItem().toString();

        int slotId =
                Integer.parseInt(
                        selectedSlot
                                .split(" - ")[0]
                );

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            int userId = findOrCreateUser(
                    con,
                    ownerName,
                    phone
            );

            int vehicleId =
                    findOrCreateVehicle(
                            con,
                            userId,
                            vehicleNumber,
                            vehicleType
                    );

            String checkActiveSql =
                    "SELECT session_id " +
                            "FROM parking_sessions " +
                            "WHERE vehicle_id = ? " +
                            "AND session_status = 'Active'";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(checkActiveSql)
            ) {

                ps.setInt(1, vehicleId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (rs.next()) {

                        con.rollback();

                        showError(
                                "This vehicle is already inside the campus."
                        );

                        return;
                    }
                }
            }

            String slotCheckSql =
                    "SELECT slot_status " +
                            "FROM parking_slots " +
                            "WHERE slot_id = ? " +
                            "FOR UPDATE";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(slotCheckSql)
            ) {

                ps.setInt(1, slotId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        showError(
                                "Selected slot does not exist."
                        );

                        return;
                    }

                    String status =
                            rs.getString("slot_status");

                    if (!"Available".equalsIgnoreCase(status)) {

                        con.rollback();

                        showError(
                                "This parking slot is no longer available.\nPlease select another slot."
                        );

                        loadAvailableSlots();
                        return;
                    }
                }
            }

            String updateSlotSql =
                    "UPDATE parking_slots " +
                            "SET slot_status = 'Occupied' " +
                            "WHERE slot_id = ?";

            try (
                    PreparedStatement ps =
                            con.prepareStatement(updateSlotSql)
            ) {

                ps.setInt(1, slotId);

                if (ps.executeUpdate() == 0) {

                    con.rollback();

                    showError(
                            "Unable to occupy parking slot."
                    );

                    return;
                }
            }

            String sessionSql =
                    "INSERT INTO parking_sessions " +
                            "(vehicle_id, slot_id, entry_time, " +
                            "duration_minutes, parking_amount, session_status) " +
                            "VALUES (?, ?, NOW(), 0, 0.00, 'Active')";

            int sessionId;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    sessionSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                ps.setInt(1, vehicleId);
                ps.setInt(2, slotId);

                ps.executeUpdate();

                try (ResultSet keys =
                             ps.getGeneratedKeys()) {

                    if (!keys.next()) {

                        con.rollback();

                        showError(
                                "Unable to create parking session."
                        );

                        return;
                    }

                    sessionId =
                            keys.getInt(1);
                }
            }

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "VEHICLE CHECK-IN SUCCESSFUL\n\n"
                            + "Vehicle: " + vehicleNumber
                            + "\nOwner: " + ownerName
                            + "\nType: " + vehicleType
                            + "\nSlot: "
                            + selectedSlot
                            + "\nSession ID: "
                            + sessionId
                            + "\n\nEntry recorded successfully.",
                    "Check-In Complete",
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
                    "Check-in failed.\n\n"
                            + e.getMessage(),
                    "Check-In Error",
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

    private int findOrCreateUser(
            Connection con,
            String name,
            String phone
    ) throws SQLException {

        String findSql =
                "SELECT user_id FROM users " +
                        "WHERE phone = ? LIMIT 1";

        try (
                PreparedStatement ps =
                        con.prepareStatement(findSql)
        ) {

            ps.setString(1, phone);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt("user_id");
                }
            }
        }

        String email =
                phone + "@campus.local";

        String insertSql =
                "INSERT INTO users " +
                        "(name, email, phone, user_type) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                PreparedStatement ps =
                        con.prepareStatement(
                                insertSql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, "Student");

            ps.executeUpdate();

            try (ResultSet keys =
                         ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Unable to create user."
        );
    }

    private int findOrCreateVehicle(
            Connection con,
            int userId,
            String vehicleNumber,
            String vehicleType
    ) throws SQLException {

        String findSql =
                "SELECT vehicle_id " +
                        "FROM vehicles " +
                        "WHERE vehicle_number = ? " +
                        "LIMIT 1";

        try (
                PreparedStatement ps =
                        con.prepareStatement(findSql)
        ) {

            ps.setString(1, vehicleNumber);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    int vehicleId =
                            rs.getInt("vehicle_id");

                    String updateOwnerSql =
                            "UPDATE vehicles " +
                                    "SET user_id = ?, vehicle_type = ? " +
                                    "WHERE vehicle_id = ?";

                    try (
                            PreparedStatement update =
                                    con.prepareStatement(
                                            updateOwnerSql
                                    )
                    ) {

                        update.setInt(1, userId);
                        update.setString(2, vehicleType);
                        update.setInt(3, vehicleId);
                        update.executeUpdate();
                    }

                    return vehicleId;
                }
            }
        }

        String insertSql =
                "INSERT INTO vehicles " +
                        "(user_id, vehicle_number, vehicle_type) " +
                        "VALUES (?, ?, ?)";

        try (
                PreparedStatement ps =
                        con.prepareStatement(
                                insertSql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setInt(1, userId);
            ps.setString(2, vehicleNumber);
            ps.setString(3, vehicleType);

            ps.executeUpdate();

            try (ResultSet keys =
                         ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Unable to create vehicle."
        );
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Check-In Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }
}