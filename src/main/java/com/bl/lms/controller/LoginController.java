package com.bl.lms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/hello")
public class LoginController {
    @RequestMapping({"/login"})
    public String first() {
        return "Login successFull";
    }
}