package com.campus.parking.dao;

import com.campus.parking.model.User;

import java.util.List;

public class UserTest {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        List<User> users =
                dao.getAllUsers();

        System.out.println(
                "===== CAMPUS USER DIRECTORY ====="
        );

        for (User user : users) {

            System.out.println(
                    "ID: " + user.getUserId()
                            + " | Name: "
                            + user.getName()
                            + " | Email: "
                            + user.getEmail()
                            + " | Type: "
                            + user.getUserType()
            );
        }

        System.out.println(
                "================================="
        );

        System.out.println(
                "Total Users: "
                        + dao.getUserCount()
        );
    }
}