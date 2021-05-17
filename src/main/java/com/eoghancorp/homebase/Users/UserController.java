package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @PostMapping("/create")
    public static String createUser(@RequestBody User user) {

        // TODO: Can't set this in the constructor because attributes haven't been set yet.
        user.setPass(user.encryptPassword());

        DbConnection db = new DbConnection();
        db.createConnection();
        if(db.createConnection()) {
            System.out.println("connection's fine.");

            db.createUser(user);
            db.closeConnection();
        }

        return "done";
    }

    @PostMapping("/login")
    public static String loginUser(@RequestBody String userName, @RequestBody String pass) {
        DbConnection db = new DbConnection();

        db.createConnection();

        User foundUser = db.getUser(userName);

        db.closeConnection();

        return foundUser.getUserId();

    }

}
