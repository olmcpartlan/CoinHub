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
    private static final String userPass = "naltrapcm";

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
            this.statement = conn.createStatement();
            String select_statement = String.format("SELECT * FROM users WHERE user_name = '%s';", userName);
            results = this.statement.executeQuery(select_statement);

            System.out.println("STATEMENT: " + select_statement);


            while(results.next()) {
                System.out.println("Pulling data for user: " + results.getString("user_name"));
                return new User(
                        results.getString("user_name"),
                        results.getString("user_pass"),
                        results.getString("user_email")
                );
            }
        }
        catch(Exception e) {
            System.out.println("There was an exception while getting the selected User.");
            System.out.println(e.getMessage());
        }

        return new User("NO_USER_FOUND", null, null);

    }

    public Boolean createUser(User user) {
        try {
            this.statement = this.conn.createStatement();

            String insertStatement = String.format("INSERT INTO " +
                    "users(user_id, user_name, user_email, user_pass, created_at, updated_at) " +
                    "VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
                    user.getUserId(), user.getUserName(), user.getEmail(),
                    user.getPass(), user.getCreatedAt(), user.getUpdatedAt()
            );

            System.out.println(String.format("Inserting user %s was successful.", user.getUserId()));

            return this.statement.execute(insertStatement);
        }
        catch(SQLException e) {
            System.out.println("There was an exception while creating a user.");
            System.out.println(e.getMessage());
        }

        return false;
    }


}
