package com.writeit.write_it;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to WriteIt!";
    }

    @GetMapping("/user")
    public String user() {
        return "Welcome to my User Page!";
    }
}
