package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import jdk.jshell.spi.ExecutionControlProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class UserMethods {

    public static User getUser(String userName) {
        try {
            String select_statement = String.format("SELECT * FROM users WHERE user_name = '%s';", userName);

            System.out.println("STATEMENT: " + select_statement);

            List<Object> userFields = DbConnection.executeSelect(select_statement);

            // We don't want to generate a new unique id. Going to pull from the
            // db ResultSet instead.
            User foundUser = new User(false);

            foundUser.setUserId(    userFields.get(0).toString());
            foundUser.setUserName(  userFields.get(1).toString());
            foundUser.setEmail(     userFields.get(2).toString());
            foundUser.setPass(      userFields.get(3).toString());

            foundUser.printUser();

            return foundUser;

        }
        catch(Exception e) {
            System.out.println("There was an exception while getting the selected User.");
            System.out.println(e.getMessage());
        }


        return new User("NO_USER_FOUND", null, null);

    }

    public static Boolean createUser(User user) {
        try {
            String insertStatement = String.format("INSERT INTO " +
                            "users(user_id, user_name, user_email, user_pass, created_at, updated_at) " +
                            "VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
                    user.getUserId(), user.getUserName(), user.getEmail(),
                    user.getPass(), user.getCreatedAt(), user.getUpdatedAt()
            );

            Boolean result = DbConnection.executeInsert(insertStatement);

            System.out.println(String.format("Inserting user %s was successful.", user.getUserId()));

            return result;
        }
        catch(Exception e) {
            System.out.println("There was an exception while creating a user.");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static boolean deleteUser(String userName) {
        try {
            String deleteStatement = String.format("DELETE FROM users WHERE user_name = '%s'", userName);

            System.out.println(deleteStatement);

            return DbConnection.executeDelete(deleteStatement);


        }
        catch(Exception e) {
            System.out.println("There was a SQL error deleting user:");
            System.out.println(e.getMessage());
        }


        return false;
    }

    public static boolean checkUserName(String userName) {
        String selectStatement = String.format("SELECT * FROM users WHERE user_name='%s'", userName);
        try
        {
            List<Object> foundUsers = DbConnection.executeSelect(selectStatement);

            if(foundUsers.stream().count() > 0) {
                return true;
            }

        }
        catch(Exception e) {}

        return false;
    }
}
