package com.eoghancorp.homebase.Users;


import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class User {
    // public String ObjectId;
    public String userId;
    public String userName;
    public String email;
    public String pass;
    public Timestamp createdAt;
    public Timestamp updatedAt;

    public User() {
        userId      = java.util.UUID.randomUUID().toString();
        createdAt   = getDateString();
        updatedAt   = getDateString();

        // Other fields are provided by the POST request.
    }

    private Timestamp getDateString() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    public String encryptPassword() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bytes = this.getPass().getBytes();
            md.reset();

            byte[] digestedByes = md.digest(bytes);
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < digestedByes.length; i++) {
                // TODO: what
                sb.append(Integer.toHexString(0xff & digestedByes[i]));
            }

            this.setPass(sb.toString());

            System.out.println("Encrypted Password: " + sb.toString());

            return sb.toString();
        }
        catch (Exception e) {
            System.out.println("Exception thrown while encrypting. ");
            System.out.println(e.getMessage());
        }

        return "an error occurred";
    }

    public void printUser() {
        System.out.println("\n\nUserId:     " + this.getUserId());
        System.out.println("UserName:   " + this.getUserName());
        System.out.println("Email:      " + this.getEmail());
        System.out.println("Created At: " + this.getCreatedAt());
        System.out.println("Updated At: " + this.getUpdatedAt() + "\n\n");
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = this.getDateString();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = this.getDateString();
    }



}
