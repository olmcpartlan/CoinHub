package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;

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

            /* -- Hoping I will be able to delete this.
            while(results.next()) {
                System.out.println("Pulling data for user: " + results.getString("user_name"));
                return new User(
                        results.getString("user_name"),
                        results.getString("user_pass"),
                        results.getString("user_email")
                );
            }
            */
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
}
