package com.YammyEater.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("api/getRecipeByTag")
    public String test(){
        return "this is test json";

    }
}
