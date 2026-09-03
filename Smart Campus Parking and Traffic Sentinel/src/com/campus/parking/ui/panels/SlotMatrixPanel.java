package com.campus.parking.ui.panels;

import com.campus.parking.dao.SlotDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SlotMatrixPanel extends JPanel {

    private final Color ROOT = new Color(15, 23, 42);
    private final Color PANEL = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);

    private final Color BLUE = new Color(56, 189, 248);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);
    private final Color ORANGE = new Color(245, 158, 11);

    private JPanel slotContainer;

    private JLabel availableLabel;
    private JLabel occupiedLabel;
    private JLabel reservedLabel;

    private final SlotDAO slotDAO;

    public SlotMatrixPanel() {

        slotDAO = new SlotDAO();

        setBackground(ROOT);

        setLayout(new BorderLayout());

        setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        createInterface();

        loadSlots();
    }

    private void createInterface() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(100, 65));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("INTERACTIVE SLOT MATRIX");

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
                        "Live parking availability across all campus zones"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(MUTED);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        JButton refresh =
                new JButton("↻  REFRESH");

        refresh.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        refresh.setForeground(TEXT);

        refresh.setBackground(PANEL);

        refresh.setFocusPainted(false);

        refresh.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        refresh.setPreferredSize(
                new Dimension(
                        125,
                        45
                )
        );

        refresh.addActionListener(
                e -> loadSlots()
        );

        header.add(
                refresh,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        center.setOpaque(false);

        center.add(
                createStatistics(),
                BorderLayout.NORTH
        );

        slotContainer = new JPanel();

        slotContainer.setOpaque(false);

        slotContainer.setLayout(
                new BoxLayout(
                        slotContainer,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        slotContainer
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        scroll.setBackground(ROOT);

        scroll.getViewport()
                .setBackground(ROOT);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        center.add(
                scroll,
                BorderLayout.CENTER
        );

        add(
                center,
                BorderLayout.CENTER
        );
    }

    private JPanel createStatistics() {

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        stats.setOpaque(false);

        JPanel available =
                createStatCard(
                        "AVAILABLE",
                        GREEN
                );

        JPanel occupied =
                createStatCard(
                        "OCCUPIED",
                        RED
                );

        JPanel reserved =
                createStatCard(
                        "RESERVED",
                        ORANGE
                );

        availableLabel =
                (JLabel) available.getClientProperty(
                        "VALUE"
                );

        occupiedLabel =
                (JLabel) occupied.getClientProperty(
                        "VALUE"
                );

        reservedLabel =
                (JLabel) reserved.getClientProperty(
                        "VALUE"
                );

        stats.add(available);
        stats.add(occupied);
        stats.add(reserved);

        return stats;
    }

    private JPanel createStatCard(
            String name,
            Color accent
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(PANEL);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                20,
                                12,
                                20
                        )
                )
        );

        JLabel value =
                new JLabel("0");

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        value.setForeground(accent);

        JLabel label =
                new JLabel(name);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(MUTED);

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.add(value);

        content.add(
                Box.createVerticalStrut(2)
        );

        content.add(label);

        card.putClientProperty(
                "VALUE",
                value
        );

        card.add(
                content,
                BorderLayout.CENTER
        );

        return card;
    }

    private void loadSlots() {

        slotContainer.removeAll();

        try {

            List<?> slots =
                    slotDAO.getAllSlots();

            int available = 0;
            int occupied = 0;
            int reserved = 0;

            int currentZone = -1;

            JPanel currentZonePanel = null;
            JPanel currentGrid = null;

            for (Object obj : slots) {

                String slotNumber =
                        getSlotNumber(obj);

                int zoneId =
                        getZoneId(obj);

                String status =
                        getStatus(obj);

                if (zoneId != currentZone) {

                    currentZone = zoneId;

                    currentZonePanel =
                            createZonePanel(
                                    "ZONE "
                                            + getZoneLetter(zoneId)
                            );

                    currentGrid =
                            new JPanel(
                                    new GridLayout(
                                            1,
                                            5,
                                            14,
                                            0
                                    )
                            );

                    currentGrid.setOpaque(false);

                    currentGrid.setPreferredSize(
                            new Dimension(
                                    800,
                                    95
                            )
                    );

                    currentGrid.setMinimumSize(
                            new Dimension(
                                    800,
                                    95
                            )
                    );

                    currentGrid.setMaximumSize(
                            new Dimension(
                                    Integer.MAX_VALUE,
                                    95
                            )
                    );

                    currentZonePanel.add(
                            currentGrid,
                            BorderLayout.CENTER
                    );

                    slotContainer.add(
                            currentZonePanel
                    );

                    slotContainer.add(
                            Box.createVerticalStrut(
                                    15
                            )
                    );
                }

                JPanel slot =
                        createSlotCard(
                                slotNumber,
                                status,
                                obj
                        );

                currentGrid.add(slot);

                if ("Available".equalsIgnoreCase(status)) {
                    available++;
                }
                else if ("Occupied".equalsIgnoreCase(status)) {
                    occupied++;
                }
                else if ("Reserved".equalsIgnoreCase(status)) {
                    reserved++;
                }
            }

            availableLabel.setText(
                    String.valueOf(available)
            );

            occupiedLabel.setText(
                    String.valueOf(occupied)
            );

            reservedLabel.setText(
                    String.valueOf(reserved)
            );

        }
        catch (Exception e) {

            JLabel error =
                    new JLabel(
                            "Unable to load parking slots: "
                                    + e.getMessage()
                    );

            error.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            14
                    )
            );

            error.setForeground(RED);

            slotContainer.add(error);
        }

        slotContainer.revalidate();

        slotContainer.repaint();
    }

    private JPanel createZonePanel(
            String zoneName
    ) {

        JPanel zone =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        zone.setBackground(PANEL);

        zone.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        zone.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel label =
                new JLabel(zoneName);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        label.setForeground(BLUE);

        label.setOpaque(false);

        zone.add(
                label,
                BorderLayout.NORTH
        );

        return zone;
    }

    private JPanel createSlotCard(
            String slotNumber,
            String status,
            Object slotObject
    ) {

        JPanel card =
                new JPanel();

        card.setPreferredSize(
                new Dimension(
                        145,
                        90
                )
        );

        card.setMinimumSize(
                new Dimension(
                        145,
                        90
                )
        );

        card.setMaximumSize(
                new Dimension(
                        145,
                        90
                )
        );

        card.setBackground(
                getStatusBackground(status)
        );

        card.setBorder(
                BorderFactory.createLineBorder(
                        getStatusColor(status)
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel slotLabel =
                new JLabel(slotNumber);

        slotLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        slotLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        slotLabel.setForeground(
                getStatusColor(status)
        );

        JLabel statusLabel =
                new JLabel(
                        status.toUpperCase()
                );

        statusLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        statusLabel.setForeground(
                getStatusColor(status)
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(slotLabel);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(statusLabel);

        card.add(
                Box.createVerticalGlue()
        );

        card.setToolTipText(
                "Click "
                        + slotNumber
                        + " to view slot information"
        );

        card.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createLineBorder(
                                        TEXT,
                                        2
                                )
                        );

                        card.setCursor(
                                Cursor.getPredefinedCursor(
                                        Cursor.HAND_CURSOR
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createLineBorder(
                                        getStatusColor(
                                                status
                                        )
                                )
                        );

                        card.setCursor(
                                Cursor.getDefaultCursor()
                        );
                    }

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        showSlotInformation(
                                slotNumber,
                                status,
                                slotObject
                        );
                    }
                }
        );

        return card;
    }

    private void showSlotInformation(
            String slotNumber,
            String status,
            Object slotObject
    ) {

        if ("Available".equalsIgnoreCase(status)) {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Parking Slot: "
                                    + slotNumber
                                    + "\n\n"
                                    + "Status: AVAILABLE"
                                    + "\n\n"
                                    + "This slot is ready for vehicle entry."
                                    + "\n\n"
                                    + "Do you want to start vehicle check-in?",
                            "Available Slot",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE
                    );

            if (result ==
                    JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vehicle Check-In module will be connected next.\n\n"
                                + "Selected Slot: "
                                + slotNumber,
                        "Check-In",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        }
        else if ("Occupied".equalsIgnoreCase(status)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Parking Slot: "
                            + slotNumber
                            + "\n\n"
                            + "Status: OCCUPIED"
                            + "\n\n"
                            + "This slot currently contains a vehicle.",
                    "Occupied Slot",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Parking Slot: "
                            + slotNumber
                            + "\n\n"
                            + "Status: "
                            + status.toUpperCase()
                            + "\n\n"
                            + "This slot is currently reserved.",
                    "Reserved Slot",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private Color getStatusColor(
            String status
    ) {

        if ("Occupied".equalsIgnoreCase(status)) {
            return RED;
        }

        if ("Reserved".equalsIgnoreCase(status)) {
            return ORANGE;
        }

        return GREEN;
    }

    private Color getStatusBackground(
            String status
    ) {

        Color color =
                getStatusColor(status);

        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                35
        );
    }

    private String getZoneLetter(
            int zoneId
    ) {

        switch (zoneId) {

            case 1:
                return "A";

            case 2:
                return "B";

            case 3:
                return "C";

            case 4:
                return "D";

            default:
                return String.valueOf(zoneId);
        }
    }

    private String getSlotNumber(
            Object obj
    ) {

        try {

            return String.valueOf(
                    obj.getClass()
                            .getMethod(
                                    "getSlotNumber"
                            )
                            .invoke(obj)
            );

        }
        catch (Exception e) {

            return "UNKNOWN";
        }
    }

    private int getZoneId(
            Object obj
    ) {

        try {

            Object value =
                    obj.getClass()
                            .getMethod(
                                    "getZoneId"
                            )
                            .invoke(obj);

            return Integer.parseInt(
                    String.valueOf(value)
            );

        }
        catch (Exception e) {

            return 1;
        }
    }

    private String getStatus(
            Object obj
    ) {

        try {

            return String.valueOf(
                    obj.getClass()
                            .getMethod(
                                    "getSlotStatus"
                            )
                            .invoke(obj)
            );

        }
        catch (Exception e) {

            try {

                return String.valueOf(
                        obj.getClass()
                                .getMethod(
                                        "getStatus"
                                )
                                .invoke(obj)
                );

            }
            catch (Exception ex) {

                return "Available";
            }
        }
    }
}