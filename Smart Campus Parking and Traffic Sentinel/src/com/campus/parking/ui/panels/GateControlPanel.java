package com.campus.parking.ui.panels;

import com.campus.parking.ui.dialogs.CheckInDialog;
import com.campus.parking.ui.dialogs.CheckOutDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GateControlPanel extends JPanel {

    private final Color ROOT = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(56, 189, 248);

    public GateControlPanel() {

        setLayout(new BorderLayout());
        setBackground(ROOT);
        setBorder(
                new EmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        createInterface();
    }

    private void createInterface() {

        JPanel header = new JPanel();

        header.setBackground(ROOT);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "ENTRY / EXIT GATE CONSOLE"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Manage vehicle movement and parking sessions"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        header.add(title);

        header.add(
                Box.createVerticalStrut(6)
        );

        header.add(subtitle);

        add(
                header,
                BorderLayout.NORTH
        );

        JPanel center =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                20
                        )
                );

        center.setBackground(ROOT);

        center.setBorder(
                new EmptyBorder(
                        30,
                        0,
                        30,
                        0
                )
        );

        center.add(
                createEntryCard()
        );

        center.add(
                createExitCard()
        );

        add(
                center,
                BorderLayout.CENTER
        );
    }

    private JPanel createEntryCard() {

        JPanel card =
                new JPanel();

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                30,
                                30,
                                30
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel icon =
                new JLabel("🚗");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        45
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel title =
                new JLabel(
                        "VEHICLE ENTRY"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel description =
                new JLabel(
                        "<html><center>"
                                + "Register a vehicle and assign<br>"
                                + "an available parking slot."
                                + "</center></html>"
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(MUTED);

        description.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton checkInButton =
                new JButton(
                        "CHECK IN VEHICLE"
                );

        styleButton(
                checkInButton,
                GREEN
        );

        checkInButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        checkInButton.addActionListener(
                e -> openCheckInDialog()
        );

        card.add(icon);

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(description);

        card.add(
                Box.createVerticalGlue()
        );

        card.add(checkInButton);

        return card;
    }

    private JPanel createExitCard() {

        JPanel card =
                new JPanel();

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                30,
                                30,
                                30
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel icon =
                new JLabel("🚪");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        45
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel title =
                new JLabel(
                        "VEHICLE EXIT"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel description =
                new JLabel(
                        "<html><center>"
                                + "Calculate parking duration,<br>"
                                + "fee and complete payment."
                                + "</center></html>"
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(MUTED);

        description.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton exitButton =
                new JButton(
                        "PROCESS VEHICLE EXIT"
                );

        styleButton(
                exitButton,
                RED
        );

        exitButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        exitButton.addActionListener(
                e -> openCheckOutDialog()
        );

        card.add(icon);

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(description);

        card.add(
                Box.createVerticalGlue()
        );

        card.add(exitButton);

        return card;
    }

    private void openCheckInDialog() {

        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );

        Frame parent = null;

        if (window instanceof Frame) {
            parent = (Frame) window;
        }

        CheckInDialog dialog =
                new CheckInDialog(
                        parent
                );

        dialog.setVisible(true);
    }

    private void openCheckOutDialog() {

        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );

        Frame parent = null;

        if (window instanceof Frame) {
            parent = (Frame) window;
        }

        CheckOutDialog dialog =
                new CheckOutDialog(
                        parent
                );

        dialog.setVisible(true);
    }

    private void styleButton(
            JButton button,
            Color background
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(TEXT);

        button.setBackground(
                background
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        13,
                        25,
                        13,
                        25
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
    }
}