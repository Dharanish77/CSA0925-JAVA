package com.campus.parking.ui.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final Color BG = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);
    private final Color BLUE = new Color(56, 189, 248);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);
    private final Color ORANGE = new Color(245, 158, 11);

    public DashboardPanel() {

        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);

        JLabel title = new JLabel("CAMPUS PARKING CONTROL CENTER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Real-time parking and traffic intelligence");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        JPanel titleBox = new JPanel();
        titleBox.setBackground(BG);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(8));
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(25, 0, 0, 0));

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 0));
        cards.setBackground(BG);

        cards.add(createCard("20", "AVAILABLE SLOTS", GREEN));
        cards.add(createCard("0", "OCCUPIED SLOTS", RED));
        cards.add(createCard("0", "RESERVED SLOTS", ORANGE));

        center.add(cards, BorderLayout.NORTH);

        JPanel lower = new JPanel(new GridLayout(1, 2, 20, 0));
        lower.setBackground(BG);

        lower.add(createActivityPanel());
        lower.add(createSystemPanel());

        center.add(lower, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel createCard(String value, String label, Color color) {

        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(22, 25, 22, 25)
                )
        );

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel number = new JLabel(value);
        number.setFont(new Font("Segoe UI", Font.BOLD, 34));
        number.setForeground(color);

        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.BOLD, 13));
        text.setForeground(MUTED);

        panel.add(number);
        panel.add(Box.createVerticalStrut(8));
        panel.add(text);

        return panel;
    }

    private JPanel createActivityPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel title = new JLabel("SENTINEL ACTIVITY");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setForeground(TEXT);

        panel.add(title, BorderLayout.NORTH);

        JTextArea activity = new JTextArea();

        activity.setText(
                "SYSTEM STATUS\n\n" +
                        "●  Database connection established\n\n" +
                        "●  20 parking spaces monitored\n\n" +
                        "●  Traffic sentinel active\n\n" +
                        "●  Waiting for vehicle activity"
        );

        activity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        activity.setForeground(MUTED);
        activity.setBackground(CARD);
        activity.setEditable(false);
        activity.setBorder(new EmptyBorder(25, 0, 0, 0));

        panel.add(activity, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSystemPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel title = new JLabel("SYSTEM OVERVIEW");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setForeground(TEXT);

        panel.add(title, BorderLayout.NORTH);

        JTextArea info = new JTextArea();

        info.setText(
                "PARKING ZONES\n\n" +
                        "Zone A     5 Slots\n" +
                        "Zone B     5 Slots\n" +
                        "Zone C     5 Slots\n" +
                        "Zone D     5 Slots\n\n" +
                        "TOTAL CAPACITY     20\n" +
                        "CURRENT OCCUPANCY  0%\n" +
                        "SYSTEM STATUS       ONLINE"
        );

        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setForeground(MUTED);
        info.setBackground(CARD);
        info.setEditable(false);
        info.setBorder(new EmptyBorder(25, 0, 0, 0));

        panel.add(info, BorderLayout.CENTER);

        return panel;
    }
}