package com.eoghancorp.homebase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class WalletController {
    @RequestMapping("/api")
    public String SomeIndexMethod(@RequestParam String arg) {
        // what do i even want to do..
        return "from the main method: " + arg;

    }
}

