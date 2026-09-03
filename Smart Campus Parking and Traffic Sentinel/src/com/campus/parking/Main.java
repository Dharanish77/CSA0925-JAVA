package com.campus.parking;

import com.campus.parking.database.DatabaseBootstrap;
import com.campus.parking.ui.MainAppFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        DatabaseBootstrap.initialize();

        SwingUtilities.invokeLater(() -> {

            MainAppFrame frame =
                    new MainAppFrame();

            frame.setVisible(true);
        });
    }
}