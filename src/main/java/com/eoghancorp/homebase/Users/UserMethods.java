package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class UserMethods {

    public static User getUser(String userName) {
        try {
            String select_statement = String.format("SELECT * FROM users WHERE user_name = '%s';", userName);

            System.out.println("STATEMENT: " + select_statement);

            User foundUser = DbConnection.executeSelect(select_statement);

            foundUser.printUser();


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
