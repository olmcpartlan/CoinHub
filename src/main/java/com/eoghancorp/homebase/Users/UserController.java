package com.eoghancorp.homebase.Users;

import com.eoghancorp.homebase.DbConnection.DbConnection;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public static String loginUser(@RequestBody Map<String, String> request_body) {
        DbConnection db = new DbConnection();

        System.out.println("\n\n");
        System.out.println(String.format("User: \t", request_body.get("userName")));
        System.out.println("\n\n");

        db.createConnection();

        User foundUser = db.getUser(request_body.get("userName"));

        db.closeConnection();

        return foundUser.getUserId();

    }

}
