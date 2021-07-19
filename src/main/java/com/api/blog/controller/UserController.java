package com.api.blog.controller;

import com.api.blog.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return "";
    }

    @PostMapping
    public void singUp(@RequestBody User user){

    }
}
