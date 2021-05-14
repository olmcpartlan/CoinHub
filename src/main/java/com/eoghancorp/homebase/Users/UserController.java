package com.eoghancorp.homebase.Users;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @PostMapping("/create")
    public static String createUser(@RequestBody User user) {
        System.out.println("First: \t"    + user.firstName);
        System.out.println("Last: \t"     + user.lastName);
        System.out.println("Email: \t"    + user.email);
        System.out.println("Pass: \t"     + user.pass);
        return "done";
    }
}

class User {
    public String firstName;
    public String lastName;
    public String email;
    public String pass;
}