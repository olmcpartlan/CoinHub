package com.eoghancorp.homebase.DbConnection;

import java.sql.*;
import java.util.*;
import java.util.UUID.*;

import com.eoghancorp.homebase.Users.*;
import com.mysql.cj.jdbc.ConnectionImpl;

public class DbConnection {

    public ResultSet results;


    static Connection createConnection(final String mySqlUrl, final String userName, final String userPass) {
        try {
           Connection conn = DriverManager.getConnection(mySqlUrl, userName, userPass);
            if(conn != null) {
                return conn;
            }
            else return null;
        }
        catch (Exception e){
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static Boolean closeConnection(Connection conn) {
        try {
            conn.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("There was an exception while closing the DB connection.");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static List<Object> executeSelect(String selectStatement) {
        try {
            Connection conn = createConnection(DbConstants.mySqlUrl, DbConstants.userName, DbConstants.userPass);
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(selectStatement);
            // Column data, types, field names, ...
            ResultSetMetaData metadata = result.getMetaData();

            List<Object> classes = new LinkedList<Object>();


            while(result.next()) {
                // Loop each column and pull the field type from the db resultset.
                for (int i = 1; i < metadata.getColumnCount(); i++) {
                    Object obj = result.getObject(i);
                    classes.add(obj);
                }

            }
            return classes;

        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("error");
        }

        return null;
    }


    public static Boolean executeInsert(String insertStatement) {
        try {
            Connection conn = createConnection(DbConstants.mySqlUrl, DbConstants.userName, DbConstants.userPass);
            Statement statement = conn.createStatement();

            Boolean result = statement.execute(insertStatement);
            // don't forget to close open connections.
            closeConnection(conn);

            return result;
        }
        catch(SQLException e) {
            System.out.println("There was an exception while inserting a record:");
            System.out.println(e.getMessage());
        }

        return false;
    }


    public static Boolean executeDelete(String deleteStatement) {
        try {
            Connection conn = createConnection(DbConstants.mySqlUrl, DbConstants.userName, DbConstants.userPass);
            Statement statement = conn.createStatement();

            Boolean result = statement.execute(deleteStatement);
            // don't forget to close open connections.
            closeConnection(conn);

            boolean response = result;
            System.out.println("WAS USER DELETED: " + response);

            return response;
        }
        catch(SQLException e) {
            System.out.println("There was an exception while deleting a record:");
            System.out.println("CLASS: " + e.getClass());
            System.out.println(e.getMessage());
        }

        return false;
    }
}

// These should probably go into a config file. not important right now.
class DbConstants {
    static String mySqlUrl = "jdbc:mysql://127.0.0.1:3306/coinhub";
    static String userName = "root";
    static String userPass = "naltrapcm";
    // static String userPass = "";
}