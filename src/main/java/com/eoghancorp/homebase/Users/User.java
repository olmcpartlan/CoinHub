package com.eoghancorp.homebase.Users;


import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class User {
    // public String ObjectId;
    private String       userId;
    private String       userName;
    private String       email;
    private String       pass;
    private Timestamp    createdAt;
    private Timestamp    updatedAt;

    // Used mainly for tests. Other method is called when sending POST request.
    public User(String user_name, String password, String user_email) {
        userId      = java.util.UUID.randomUUID().toString();
        pass        = password;
        email       = user_email;
        userName    = user_name;
        createdAt   = getDateString();
        updatedAt   = getDateString();

        // Other fields are provided by the POST request.
    }

    // When the user is created from the POST request, these fields need to be created before-hand.
    public User() {
        userId      = java.util.UUID.randomUUID().toString();
        createdAt   = getDateString();
        updatedAt   = getDateString();

        // Other fields are provided by the POST request.
    }

    public static String encryptPassword(String inputPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");

            byte[] bytes = inputPassword.getBytes();
            md.reset();

            byte[] digestedByes = md.digest(bytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.digest().length; i++) {
                if ((digestedByes[i] & 0xff) < 0x10) {
                    sb.append("0");
                }

                sb.append(Long.toString(digestedByes[i] & 0xff, 16));
            }

            return sb.toString();
        }
        catch (Exception e) {
            System.out.println("Exception thrown while encrypting. ");
            System.out.println(e.getMessage());
        }

        return "an error occurred";
    }

    // Converts the Date object into a more sql-friendly timestamp.
    private Timestamp getDateString() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }


    public void printUser() {
        System.out.println("\n\nUserId:     " + this.getUserId());
        System.out.println("UserName:   " + this.getUserName());
        System.out.println("Email:      " + this.getEmail());
        System.out.println("Password:   " + this.getPass());
        System.out.println("Created At: " + this.getCreatedAt());
        System.out.println("Updated At: " + this.getUpdatedAt() + "\n\n");
    }

    public String getUserId() { return this.userId; }

    public String getUserName() { return this.userName; }

    public String getEmail() { return this.email; }

    public String getPass() { return this.pass; }

    public Date getCreatedAt() { return this.createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setEmail(String email) { this.email = email; }

    public void setPass(String new_pass) {
        this.pass = new_pass;
    }

    public void setCreatedAt(Date createdAt) { this.createdAt = this.getDateString(); }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = this.getDateString(); }



}
