package com.eoghancorp.homebase.DbConnection;

import java.sql.*;
import java.util.UUID;
import java.util.UUID.*;

import com.eoghancorp.homebase.Users.*;

public class DbConnection {

    public Connection conn;
    public Statement statement;
    public ResultSet results;

    private static final String mySqlUrl = "jdbc:mysql://127.0.0.1:3306/coinhub";

    private static final String userName = "root";
    private static final String userPass = "";

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Boolean createConnection() {
        try {
            this.setConn(DriverManager.getConnection(mySqlUrl, userName, userPass));
            if(this.conn != null)
                return true;
        }
        catch (Exception e){
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean closeConnection() {
        try {
            this.conn.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("There was an exception while closing the DB connection.");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public User getUser(String userName) {
        try {
            Statement statement = conn.createStatement();
            results = statement.executeQuery("SELECT * FROM users;");

            while(results.next()) {
                String userId = results.getString("user_name");
                System.out.println("Found user_id: " + userId);
            }

        }
        catch(SQLException e) {
            System.out.println("There was an exception while getting the selected User.");
            System.out.println(e.getMessage());
        }

        return new User();
    }

    public Boolean createUser(User user) {
        try {
            this.statement = this.conn.createStatement();

            String insertStatement = String.format("INSERT INTO " +
                    "users(user_id, user_name, user_email, user_pass, created_at, updated_at) " +
                    "VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
                    user.userId, user.userName, user.email, user.pass, user.createdAt, user.updatedAt );

            System.out.println("*****\n\n");
            System.out.println(insertStatement);
            System.out.println("\n*****\n");

            return this.statement.execute(insertStatement);
        }
        catch(SQLException e) {
            System.out.println("There was an exception while creating a user.");
            System.out.println(e.getMessage());
        }

        return false;
    }


}
