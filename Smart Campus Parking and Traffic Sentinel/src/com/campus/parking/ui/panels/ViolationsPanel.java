package com.campus.parking.ui.panels;

import com.campus.parking.dao.ViolationDAO;
import com.campus.parking.model.Violation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViolationsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;
    private JLabel fineLabel;
    private JLabel unpaidLabel;

    private final ViolationDAO violationDAO = new ViolationDAO();

    private final Color BG = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);
    private final Color ORANGE = new Color(245, 158, 11);
    private final Color WHITE = new Color(241, 245, 249);
    private final Color MUTED = new Color(148, 163, 184);

    public ViolationsPanel() {

        setLayout(new BorderLayout(20, 20));

        setBackground(BG);

        setBorder(
                BorderFactory.createEmptyBorder(
                        30, 35, 30, 35
                )
        );

        add(createHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(
                new BorderLayout(0, 20)
        );

        center.setBackground(BG);

        center.add(
                createSummaryCards(),
                BorderLayout.NORTH
        );

        center.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        add(center, BorderLayout.CENTER);

        loadViolations();
    }

    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(BG);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(BG);

        JLabel title =
                new JLabel(
                        "VIOLATIONS & FINE REGISTRY"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(WHITE);

        JLabel subtitle =
                new JLabel(
                        "Monitor parking violations, fines and settlement status"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(6)
        );

        titlePanel.add(subtitle);

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setBackground(BG);

        JButton addButton =
                createButton(
                        "ADD VIOLATION",
                        GREEN
                );

        JButton refreshButton =
                createButton(
                        "REFRESH",
                        BLUE
                );

        addButton.addActionListener(
                e -> showAddViolationDialog()
        );

        refreshButton.addActionListener(
                e -> loadViolations()
        );

        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                buttonPanel,
                BorderLayout.EAST
        );

        return header;
    }

    private JPanel createSummaryCards() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        panel.setBackground(BG);

        totalLabel =
                createCard(
                        panel,
                        "TOTAL VIOLATIONS",
                        "0",
                        RED
                );

        fineLabel =
                createCard(
                        panel,
                        "TOTAL FINES",
                        "₹0.00",
                        ORANGE
                );

        unpaidLabel =
                createCard(
                        panel,
                        "UNPAID",
                        "0",
                        BLUE
                );

        return panel;
    }

    private JLabel createCard(
            JPanel parent,
            String title,
            String value,
            Color accent
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLabel.setForeground(MUTED);

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        valueLabel.setForeground(accent);

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(valueLabel);

        parent.add(card);

        return valueLabel;
    }

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(0, 12)
                );

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setBackground(CARD);

        JLabel heading =
                new JLabel(
                        "VIOLATION RECORDS"
                );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        heading.setForeground(WHITE);

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        actionPanel.setBackground(CARD);

        JButton paidButton =
                createButton(
                        "MARK PAID",
                        GREEN
                );

        JButton deleteButton =
                createButton(
                        "DELETE",
                        RED
                );

        paidButton.addActionListener(
                e -> markSelectedAsPaid()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedViolation()
        );

        actionPanel.add(paidButton);
        actionPanel.add(deleteButton);

        topPanel.add(
                heading,
                BorderLayout.WEST
        );

        topPanel.add(
                actionPanel,
                BorderLayout.EAST
        );

        model =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "USER ID",
                                "VEHICLE ID",
                                "VIOLATION TYPE",
                                "DESCRIPTION",
                                "FINE",
                                "DATE",
                                "STATUS"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(40);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.setForeground(WHITE);

        table.setBackground(CARD);

        table.setGridColor(BORDER);

        table.setSelectionBackground(
                new Color(
                        79,
                        70,
                        229
                )
        );

        table.setSelectionForeground(WHITE);

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        table.getTableHeader().setForeground(WHITE);

        table.getTableHeader().setBackground(
                new Color(
                        15,
                        23,
                        42
                )
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(
                        0,
                        40
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        scrollPane
                .getViewport()
                .setBackground(CARD);

        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(WHITE);

        button.setBackground(color);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        16,
                        10,
                        16
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private void showAddViolationDialog() {

        JTextField userIdField =
                new JTextField();

        JTextField vehicleIdField =
                new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "Wrong Parking",
                                "Reserved Slot Violation",
                                "No Parking",
                                "Overstay",
                                "Unauthorized Parking",
                                "Other"
                        }
                );

        JTextField descriptionField =
                new JTextField();

        JTextField fineField =
                new JTextField();

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Unpaid",
                                "Paid"
                        }
                );

        JPanel form =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10
                        )
                );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        form.add(
                new JLabel("User ID:")
        );

        form.add(userIdField);

        form.add(
                new JLabel("Vehicle ID:")
        );

        form.add(vehicleIdField);

        form.add(
                new JLabel("Violation Type:")
        );

        form.add(typeBox);

        form.add(
                new JLabel("Description:")
        );

        form.add(descriptionField);

        form.add(
                new JLabel("Fine Amount:")
        );

        form.add(fineField);

        form.add(
                new JLabel("Status:")
        );

        form.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        form,
                        "Add New Violation",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result !=
                        JOptionPane.OK_OPTION
        ) {
            return;
        }

        try {

            String userText =
                    userIdField
                            .getText()
                            .trim();

            String vehicleText =
                    vehicleIdField
                            .getText()
                            .trim();

            String description =
                    descriptionField
                            .getText()
                            .trim();

            String fineText =
                    fineField
                            .getText()
                            .trim();

            if (vehicleText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vehicle ID is required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (description.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Description is required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (fineText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Fine amount is required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int userId = 0;

            if (!userText.isEmpty()) {

                userId =
                        Integer.parseInt(
                                userText
                        );
            }

            int vehicleId =
                    Integer.parseInt(
                            vehicleText
                    );

            double fine =
                    Double.parseDouble(
                            fineText
                    );

            if (fine < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Fine amount cannot be negative.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Violation violation =
                    new Violation();

            violation.setUserId(userId);

            violation.setVehicleId(
                    vehicleId
            );

            violation.setViolationType(
                    typeBox
                            .getSelectedItem()
                            .toString()
            );

            violation.setDescription(
                    description
            );

            violation.setFineAmount(
                    fine
            );

            violation.setStatus(
                    statusBox
                            .getSelectedItem()
                            .toString()
            );

            boolean success =
                    violationDAO.addViolation(
                            violation
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Violation added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadViolations();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add violation.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "User ID, Vehicle ID and Fine must contain valid numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void markSelectedAsPaid() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a violation first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int violationId =
                Integer.parseInt(
                        model.getValueAt(
                                row,
                                0
                        ).toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Mark this violation as Paid?",
                        "Confirm Payment",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                violationDAO.updateStatus(
                        violationId,
                        "Paid"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Violation marked as Paid.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadViolations();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update violation.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteSelectedViolation() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a violation first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int violationId =
                Integer.parseInt(
                        model.getValueAt(
                                row,
                                0
                        ).toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this violation?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                violationDAO.deleteViolation(
                        violationId
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Violation deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadViolations();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete violation.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadViolations() {

        model.setRowCount(0);

        try {

            List<Violation> violations =
                    violationDAO.getAllViolations();

            double totalFine = 0.0;

            int unpaid = 0;

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm"
                    );

            for (
                    Violation violation :
                    violations
            ) {

                totalFine +=
                        violation.getFineAmount();

                if (
                        violation.getStatus() != null &&
                                violation.getStatus()
                                        .equalsIgnoreCase(
                                                "Unpaid"
                                        )
                ) {

                    unpaid++;
                }

                String dateText = "";

                LocalDateTime date =
                        violation.getViolationDate();

                if (date != null) {

                    dateText =
                            date.format(
                                    formatter
                            );
                }

                model.addRow(
                        new Object[]{
                                violation.getViolationId(),
                                violation.getUserId(),
                                violation.getVehicleId(),
                                violation.getViolationType(),
                                violation.getDescription(),
                                String.format(
                                        "₹%.2f",
                                        violation.getFineAmount()
                                ),
                                dateText,
                                violation.getStatus()
                        }
                );
            }

            totalLabel.setText(
                    String.valueOf(
                            violations.size()
                    )
            );

            fineLabel.setText(
                    String.format(
                            "₹%.2f",
                            totalFine
                    )
            );

            unpaidLabel.setText(
                    String.valueOf(unpaid)
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load violations.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}