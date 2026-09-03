package com.campus.parking.dao;

import com.campus.parking.database.DBConnection;
import com.campus.parking.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id, name, email, phone, user_type
                FROM users
                ORDER BY user_id
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                User user = new User();

                user.setUserId(
                        rs.getInt("user_id")
                );

                user.setName(
                        rs.getString("name")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setPhone(
                        rs.getString("phone")
                );

                user.setUserType(
                        rs.getString("user_type")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading users: "
                            + e.getMessage()
            );
        }

        return users;
    }

    public User getUserById(int userId) {

        String sql = """
                SELECT user_id, name, email, phone, user_type
                FROM users
                WHERE user_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    User user = new User();

                    user.setUserId(
                            rs.getInt("user_id")
                    );

                    user.setName(
                            rs.getString("name")
                    );

                    user.setEmail(
                            rs.getString("email")
                    );

                    user.setPhone(
                            rs.getString("phone")
                    );

                    user.setUserType(
                            rs.getString("user_type")
                    );

                    return user;
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding user: "
                            + e.getMessage()
            );
        }

        return null;
    }

    public boolean addUser(User user) {

        String sql = """
                INSERT INTO users
                (name, email, phone, user_type)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getUserType());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding user: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public boolean updateUser(User user) {

        String sql = """
                UPDATE users
                SET name = ?,
                    email = ?,
                    phone = ?,
                    user_type = ?
                WHERE user_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getUserType());
            ps.setInt(5, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating user: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public boolean deleteUser(int userId) {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting user: "
                            + e.getMessage()
            );

            return false;
        }
    }

    public List<User> searchUsers(String keyword) {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id, name, email, phone, user_type
                FROM users
                WHERE name LIKE ?
                   OR email LIKE ?
                   OR phone LIKE ?
                ORDER BY user_id
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    User user = new User();

                    user.setUserId(
                            rs.getInt("user_id")
                    );

                    user.setName(
                            rs.getString("name")
                    );

                    user.setEmail(
                            rs.getString("email")
                    );

                    user.setPhone(
                            rs.getString("phone")
                    );

                    user.setUserType(
                            rs.getString("user_type")
                    );

                    users.add(user);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error searching users: "
                            + e.getMessage()
            );
        }

        return users;
    }

    public int getUserCount() {

        String sql =
                "SELECT COUNT(*) FROM users";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error counting users: "
                            + e.getMessage()
            );
        }

        return 0;
    }
}