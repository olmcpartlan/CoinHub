package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@RestController
public class UserController {
    @PostMapping("/create")
    public static User createUser(@RequestBody User user) {

        // TODO: Can't set this in the constructor because password field has not been set in time.
        user.setPass(User.encryptPassword(user.getPass()));

        UserMethods userMethods = new UserMethods();

        // Creating the user returns a boolean so can't pull the fields from here.
        userMethods.createUser(user);

        return new UserMethods().getUser(user.getUserName());

    }

    @PostMapping("/login")
    public static User loginUser(@RequestBody Map<String, String> request_body) {
        // Find the user with the matching username.
        User foundUser = new UserMethods().getUser(request_body.get("userName"));

        // If the provided username cannot be found, don't try to check password input.
        if(foundUser.getPass() == null)
            return foundUser;

        // Encrypt the input password to check if it matches the pass in db.
        String passwordCheck = User.encryptPassword(request_body.get("pass"));

        // this returns false even though passwords do match.
        if(passwordCheck.equals(foundUser.getPass())) {
            System.out.println(String.format("The passwords match. Logging in user %s", foundUser.getUserName()));
            foundUser.printUser();
            return foundUser;
        }
        else {
            System.out.println(String.format("The passwords did not match for user %s", foundUser.getUserName()));
            return new User("NO_USER_FOUND", null, null);
        }

    }
}
