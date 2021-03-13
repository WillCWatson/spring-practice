package com.example.practice.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class PracticeController {

    @RequestMapping("/") // Any time someone comes to the root, this code will run
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/hello") // Any time someone comes to the root, this code will run
    public PracticeClass hello() {
        PracticeClass student = new PracticeClass();

        student.fName = "Gary";
        student.lName = "Watson";
        student.age = 42;

        return student;

    }

}

