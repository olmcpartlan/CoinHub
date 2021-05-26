package com.eoghancorp.homebase;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import com.eoghancorp.homebase.Users.User;
import com.eoghancorp.homebase.Users.UserController;
import com.eoghancorp.homebase.Users.UserMethods;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;


@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class UserEntryTests {

    private User user;

    public UserEntryTests()
    {
        this.setUser(new User("omcpartlan", "S0m3S3cur3DP$55", "omcpartlan@gmail.com"));
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Test
    @DisplayName("A. Load Context")
    // TODO: create table if not exists
    void contextLoads() {
        System.out.println("Starting Tests");
    }

    @Test
    @DisplayName("B. Create New User")
    void createUser() {
// User is created in the constructor, but the password is encrypted after.

// TODO: This should probably be done automatically.
        String pass = User.encryptPassword(this.user.getPass());
        user.setPass(pass);

// Want to make sure that when encryptPassword() is called, only the encrypted password is attached to the record.
        Assertions.assertNotEquals(this.getUser().getPass(), "S0m3S3cur3DP$55");

    }


    @Test
    @DisplayName("C. Insert User into MySQL")
    void insertUser() {
        try {
            this.user.printUser();
            UserMethods methods = new UserMethods();

            methods.createUser(this.user);

        }
        catch(Exception e) {
            System.out.println("***** UNABLE TO CREATE CONNECTION TO MySQL ******");
            System.out.println(e.getMessage());
        }

    }

    @Test
    @DisplayName("D. Test new User Login functionality.")
    void loginUser() {
        UserMethods methods = new UserMethods();
        User foundUser = methods.getUser(this.user.getUserName());
        foundUser.printUser();

        Assertions.assertEquals(this.user.getPass(), foundUser.getPass());
    }

    @Test
    @DisplayName("E. Remove new user from db")
    void deleteUser() {
        UserMethods.deleteUser(user.getUserName());


        String userName = user.getUserName();

        User foundUser = UserMethods.getUser(userName);

        Assertions.assertEquals(foundUser.getUserName(), "NO_USER_FOUND");
    }

}
