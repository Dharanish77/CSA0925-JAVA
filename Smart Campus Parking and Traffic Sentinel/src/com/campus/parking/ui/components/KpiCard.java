package com.campus.parking.ui.components;

import javax.swing.*;
import java.awt.*;

public class KpiCard extends JPanel {

    private final JLabel titleLabel;
    private final JLabel valueLabel;
    private final JLabel subtitleLabel;

    private Color accentColor =
            new Color(56, 189, 248);

    public KpiCard(
            String title,
            String value,
            String subtitle
    ) {

        setLayout(
                new BorderLayout(
                        8,
                        4
                )
        );

        setOpaque(false);

        setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setForeground(
                new Color(148, 163, 184)
        );

        valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        valueLabel.setForeground(
                Color.WHITE
        );

        subtitleLabel =
                new JLabel(subtitle);

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitleLabel.setForeground(
                new Color(148, 163, 184)
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.add(titleLabel);
        textPanel.add(valueLabel);
        textPanel.add(subtitleLabel);

        add(
                textPanel,
                BorderLayout.CENTER
        );
    }

    public void setValue(
            String value
    ) {

        valueLabel.setText(value);
    }

    public void setAccentColor(
            Color color
    ) {

        accentColor = color;

        repaint();
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

        g2.setColor(
                new Color(
                        30,
                        41,
                        59
                )
        );

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                18,
                18
        );

        g2.setColor(accentColor);

        g2.fillRoundRect(
                0,
                0,
                5,
                getHeight(),
                5,
                5
        );

        g2.setColor(
                new Color(
                        51,
                        65,
                        85
                )
        );

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                18,
                18
        );

        g2.dispose();

        super.paintComponent(g);
    }
}