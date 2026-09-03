package com.campus.parking.ui;

import com.campus.parking.ui.panels.DashboardPanel;
import com.campus.parking.ui.panels.SlotMatrixPanel;
import com.campus.parking.ui.panels.GateControlPanel;
import com.campus.parking.ui.panels.UserManagementPanel;
import com.campus.parking.ui.panels.ViolationsPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainAppFrame extends JFrame {

    private final Color ROOT = new Color(15, 23, 42);
    private final Color SIDEBAR = new Color(30, 41, 59);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);
    private final Color BLUE = new Color(56, 189, 248);
    private final Color PURPLE = new Color(99, 102, 241);
    private final Color GREEN = new Color(16, 185, 129);

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JLabel clockLabel;
    private JLabel dateLabel;

    public MainAppFrame() {

        setTitle(
                "Smart Campus Parking & Traffic Sentinel"
        );

        setSize(
                1500,
                900
        );

        setMinimumSize(
                new Dimension(
                        1100,
                        700
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        createInterface();

        startClock();
    }

    private void createInterface() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(ROOT);

        setContentPane(root);

        root.add(
                createTopBar(),
                BorderLayout.NORTH
        );

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(
                        cardLayout
                );

        contentPanel.setBackground(ROOT);

        contentPanel.add(
                new DashboardPanel(),
                "DASHBOARD"
        );

        contentPanel.add(
                new SlotMatrixPanel(),
                "SLOTS"
        );

        contentPanel.add(
                new GateControlPanel(),
                "GATE"
        );

        contentPanel.add(
                new UserManagementPanel(),
                "USERS"
        );

        contentPanel.add(
                new ViolationsPanel(),
                "VIOLATIONS"
        );

        root.add(
                contentPanel,
                BorderLayout.CENTER
        );
    }

    private JPanel createTopBar() {

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setBackground(ROOT);

        top.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                1,
                                0,
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                14,
                                25,
                                14,
                                25
                        )
                )
        );

        JTextField search =
                new JTextField(
                        "Search vehicle, slot or session..."
                );

        search.setPreferredSize(
                new Dimension(
                        505,
                        45
                )
        );

        search.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        search.setForeground(
                MUTED
        );

        search.setBackground(
                SIDEBAR
        );

        search.setCaretColor(
                BLUE
        );

        search.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        left.setBackground(ROOT);

        left.add(search);

        top.add(
                left,
                BorderLayout.WEST
        );

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                0
                        )
                );

        right.setBackground(ROOT);

        clockLabel =
                new JLabel(
                        "00:00:00"
                );

        clockLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        clockLabel.setForeground(
                TEXT
        );

        JLabel live =
                new JLabel(
                        "● LIVE"
                );

        live.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        live.setForeground(
                GREEN
        );

        JButton checkIn =
                new JButton(
                        "+  VEHICLE CHECK-IN"
                );

        styleButton(
                checkIn,
                PURPLE
        );

        checkIn.addActionListener(
                e -> showGate()
        );

        right.add(clockLabel);
        right.add(live);
        right.add(checkIn);

        top.add(
                right,
                BorderLayout.EAST
        );

        return top;
    }

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(
                        315,
                        0
                )
        );

        sidebar.setBackground(
                SIDEBAR
        );

        JPanel top =
                new JPanel();

        top.setBackground(
                SIDEBAR
        );

        top.setLayout(
                new BoxLayout(
                        top,
                        BoxLayout.Y_AXIS
                )
        );

        top.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        18,
                        20,
                        18
                )
        );

        JLabel logo =
                new JLabel(
                        "▣"
                );

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        logo.setForeground(
                BLUE
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel brand =
                new JLabel(
                        "SMART CAMPUS"
                );

        brand.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        brand.setForeground(
                TEXT
        );

        brand.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel sub =
                new JLabel(
                        "PARKING SENTINEL"
                );

        sub.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        sub.setForeground(
                MUTED
        );

        sub.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        top.add(logo);

        top.add(
                Box.createVerticalStrut(
                        12
                )
        );

        top.add(brand);

        top.add(
                Box.createVerticalStrut(
                        3
                )
        );

        top.add(sub);

        top.add(
                Box.createVerticalStrut(
                        30
                )
        );

        JButton dashboard =
                createMenuButton(
                        "▣   Command Dashboard"
                );

        JButton slots =
                createMenuButton(
                        "▦   Live Slot Matrix"
                );

        JButton gate =
                createMenuButton(
                        "⏱   ENTRY / EXIT GATE CONSOLE"
                );

        JButton users =
                createMenuButton(
                        "♙   User Directory"
                );

        JButton violations =
                createMenuButton(
                        "⚠   Violations"
                );

        JButton payments =
                createMenuButton(
                        "₹   Payments"
                );

        dashboard.addActionListener(
                e -> showPage("DASHBOARD")
        );

        slots.addActionListener(
                e -> showPage("SLOTS")
        );

        gate.addActionListener(
                e -> showGate()
        );

        users.addActionListener(
                e -> showPage("USERS")
        );

        violations.addActionListener(
                e -> showPage("VIOLATIONS")
        );

        payments.addActionListener(
                e -> showPage("DASHBOARD")
        );

        top.add(dashboard);

        top.add(
                Box.createVerticalStrut(
                        8
                )
        );

        top.add(slots);

        top.add(
                Box.createVerticalStrut(
                        8
                )
        );

        top.add(gate);

        top.add(
                Box.createVerticalStrut(
                        8
                )
        );

        top.add(users);

        top.add(
                Box.createVerticalStrut(
                        8
                )
        );

        top.add(violations);

        top.add(
                Box.createVerticalStrut(
                        8
                )
        );

        top.add(payments);

        sidebar.add(
                top,
                BorderLayout.NORTH
        );

        JPanel status =
                new JPanel();

        status.setBackground(
                SIDEBAR
        );

        status.setLayout(
                new BoxLayout(
                        status,
                        BoxLayout.Y_AXIS
                )
        );

        status.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                1,
                                0,
                                0,
                                0,
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );

        JLabel statusTitle =
                new JLabel(
                        "SYSTEM STATUS"
                );

        statusTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        statusTitle.setForeground(
                MUTED
        );

        JLabel database =
                new JLabel(
                        "●  MySQL Connected"
                );

        database.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        database.setForeground(
                GREEN
        );

        JLabel engine =
                new JLabel(
                        "●  Sentinel Engine Online"
                );

        engine.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        engine.setForeground(
                TEXT
        );

        status.add(statusTitle);

        status.add(
                Box.createVerticalStrut(
                        10
                )
        );

        status.add(database);

        status.add(
                Box.createVerticalStrut(
                        8
                )
        );

        status.add(engine);

        sidebar.add(
                status,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        button.setPreferredSize(
                new Dimension(
                        280,
                        52
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                TEXT
        );

        button.setBackground(
                SIDEBAR
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        18,
                        0,
                        10
                )
        );

        button.setFocusPainted(
                false
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                TEXT
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        20,
                        12,
                        20
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    private void showGate() {

        cardLayout.show(
                contentPanel,
                "GATE"
        );
    }

    private void showPage(
            String page
    ) {

        cardLayout.show(
                contentPanel,
                page
        );
    }

    private void startClock() {

        Timer timer =
                new Timer(
                        1000,
                        e -> updateClock()
                );

        timer.start();

        updateClock();
    }

    private void updateClock() {

        LocalDateTime now =
                LocalDateTime.now();

        clockLabel.setText(
                now.format(
                        DateTimeFormatter.ofPattern(
                                "HH:mm:ss"
                        )
                )
        );
    }
}