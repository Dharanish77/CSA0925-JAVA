package com.campus.parking.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {

    private boolean hovered = false;
    private boolean active = false;

    private final Color normalColor = new Color(30, 41, 59);
    private final Color hoverColor = new Color(56, 189, 248);
    private final Color activeColor = new Color(99, 102, 241);
    private final Color textColor = new Color(226, 232, 240);

    public ModernButton(String text) {

        super(text);

        setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        setForeground(textColor);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        18,
                        12,
                        18
                )
        );

        addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {
                        hovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {
                        hovered = false;
                        repaint();
                    }
                }
        );
    }

    public void setActive(boolean active) {

        this.active = active;

        repaint();
    }

    public boolean isActive() {

        return active;
    }

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color background;

        if (active) {

            background = activeColor;

        } else if (hovered) {

            background = hoverColor;

        } else {

            background = normalColor;
        }

        g2.setColor(background);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                14,
                14
        );

        if (hovered || active) {

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            35
                    )
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    14,
                    14
            );
        }

        g2.dispose();

        super.paintComponent(g);
    }
}