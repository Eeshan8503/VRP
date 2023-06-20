package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import src.App;

@RestController
public class MyController {
    @GetMapping("/")
    public String index() {
        double ans= App.runner();
        return String.valueOf(ans);
    }
}
