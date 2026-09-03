package com.campus.parking.ui.panels;

import com.campus.parking.database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserManagementPanel extends JPanel {

    private final Color BG = new Color(15, 23, 42);
    private final Color CARD = new Color(30, 41, 59);
    private final Color BORDER = new Color(51, 65, 85);
    private final Color BLUE = new Color(56, 189, 248);
    private final Color GREEN = new Color(16, 185, 129);
    private final Color RED = new Color(239, 68, 68);
    private final Color PURPLE = new Color(99, 102, 241);
    private final Color WHITE = new Color(241, 245, 249);
    private final Color MUTED = new Color(148, 163, 184);

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public UserManagementPanel() {

        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(35, 38, 30, 38));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadUsers();
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(0, 0, 25, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BG);

        JLabel title = new JLabel("USER & VEHICLE DIRECTORY");
        title.setFont(new Font("Segoe UI", Font.BOLD, 29));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "Manage campus users, visitors and registered vehicles"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);
        subtitle.setBorder(new EmptyBorder(7, 0, 0, 0));

        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setBackground(BG);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(245, 42));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(WHITE);
        searchField.setBackground(new Color(15, 23, 42));
        searchField.setCaretColor(BLUE);
        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );

        searchField.setToolTipText("Search by name, email or phone");

        searchField.addActionListener(e -> searchUsers());

        JButton searchButton = createButton("SEARCH", BLUE);
        searchButton.addActionListener(e -> searchUsers());

        JButton refreshButton = createButton("REFRESH", BORDER);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            loadUsers();
        });

        JButton addButton = createButton("+  ADD USER", GREEN);
        addButton.addActionListener(e -> showUserDialog(null));

        actions.add(searchField);
        actions.add(searchButton);
        actions.add(refreshButton);
        actions.add(addButton);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {

        JPanel container = new JPanel(new BorderLayout(0, 18));
        container.setBackground(BG);

        container.add(createStatsPanel(), BorderLayout.NORTH);
        container.add(createTablePanel(), BorderLayout.CENTER);

        return container;
    }

    private JPanel createStatsPanel() {

        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBackground(BG);

        panel.add(createStatCard(
                "TOTAL USERS",
                getUserCount(),
                BLUE
        ));

        panel.add(createStatCard(
                "STUDENTS",
                getUserTypeCount("Student"),
                PURPLE
        ));

        panel.add(createStatCard(
                "STAFF",
                getUserTypeCount("Staff"),
                GREEN
        ));

        return panel;
    }

    private JPanel createStatCard(
            String title,
            int value,
            Color accent
    ) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(18, 20, 18, 20)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(MUTED);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accent);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(18, 18, 18, 18)
                )
        );

        JLabel heading = new JLabel("REGISTERED CAMPUS USERS");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heading.setForeground(WHITE);
        heading.setBorder(new EmptyBorder(0, 0, 15, 0));

        String[] columns = {
                "ID",
                "NAME",
                "EMAIL",
                "PHONE",
                "USER TYPE",
                "ACTION"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(48);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(WHITE);
        table.setBackground(CARD);
        table.setSelectionBackground(new Color(51, 65, 85));
        table.setSelectionForeground(WHITE);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setBackground(
                new Color(15, 23, 42)
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(150);

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        center.setForeground(WHITE);
        center.setBackground(CARD);

        for (int i = 0; i < 6; i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(center);
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2 &&
                        table.getSelectedRow() >= 0) {

                    int row = table.getSelectedRow();

                    int id = Integer.parseInt(
                            model.getValueAt(row, 0).toString()
                    );

                    showUserDialog(id);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        scrollPane.getViewport().setBackground(CARD);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(CARD);
        top.add(heading, BorderLayout.WEST);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        button.setForeground(WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 18, 12, 18
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private void loadUsers() {

        model.setRowCount(0);

        String sql =
                "SELECT user_id, name, email, phone, user_type " +
                        "FROM users ORDER BY user_id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("user_type"),
                        "DOUBLE CLICK TO EDIT"
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void searchUsers() {

        String search =
                searchField.getText().trim();

        if (search.isEmpty()) {

            loadUsers();
            return;
        }

        model.setRowCount(0);

        String sql =
                "SELECT user_id, name, email, phone, user_type " +
                        "FROM users " +
                        "WHERE name LIKE ? " +
                        "OR email LIKE ? " +
                        "OR phone LIKE ? " +
                        "ORDER BY user_id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            String value = "%" + search + "%";

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    model.addRow(new Object[]{
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("user_type"),
                            "DOUBLE CLICK TO EDIT"
                    });
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getUserCount() {

        String sql =
                "SELECT COUNT(*) FROM users";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ignored) {
        }

        return 0;
    }

    private int getUserTypeCount(String type) {

        String sql =
                "SELECT COUNT(*) FROM users WHERE user_type = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ignored) {
        }

        return 0;
    }

    private void showUserDialog(Integer userId) {

        JTextField nameField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "Student",
                                "Staff",
                                "Faculty",
                                "Visitor"
                        }
                );

        if (userId != null) {

            loadUserForEdit(
                    userId,
                    nameField,
                    emailField,
                    phoneField,
                    typeBox
            );
        }

        JPanel form = new JPanel(
                new GridLayout(4, 2, 10, 12)
        );

        form.setBorder(
                new EmptyBorder(15, 15, 10, 15)
        );

        form.add(new JLabel("Name"));
        form.add(nameField);

        form.add(new JLabel("Email"));
        form.add(emailField);

        form.add(new JLabel("Phone"));
        form.add(phoneField);

        form.add(new JLabel("User Type"));
        form.add(typeBox);

        String title =
                userId == null
                        ? "Add New User"
                        : "Edit User";

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String type =
                typeBox.getSelectedItem().toString();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter user name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter phone number.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (userId == null) {

            insertUser(
                    name,
                    email,
                    phone,
                    type
            );

        } else {

            updateUser(
                    userId,
                    name,
                    email,
                    phone,
                    type
            );
        }
    }

    private void loadUserForEdit(
            int id,
            JTextField name,
            JTextField email,
            JTextField phone,
            JComboBox<String> type
    ) {

        String sql =
                "SELECT name, email, phone, user_type " +
                        "FROM users WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    name.setText(
                            rs.getString("name")
                    );

                    email.setText(
                            rs.getString("email")
                    );

                    phone.setText(
                            rs.getString("phone")
                    );

                    type.setSelectedItem(
                            rs.getString("user_type")
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void insertUser(
            String name,
            String email,
            String phone,
            String type
    ) {

        String sql =
                "INSERT INTO users " +
                        "(name, email, phone, user_type) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, type);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadUsers();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add user.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateUser(
            int id,
            String name,
            String email,
            String phone,
            String type
    ) {

        String sql =
                "UPDATE users SET " +
                        "name = ?, email = ?, phone = ?, " +
                        "user_type = ? " +
                        "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, type);
            ps.setInt(5, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "User updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadUsers();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update user.\n\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}