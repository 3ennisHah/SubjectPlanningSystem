package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @GetMapping("/")
    public String helloWorld() {
        return "index"; // This maps to index.html in the templates directory
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf template for login page
    }

    @PostMapping("/login")
    public ModelAndView loginSubmit(@RequestParam String username, @RequestParam String password) {
        // For demonstration purposes, no actual authentication
        if ("user".equals(username) && "password".equals(password)) {
            return new ModelAndView("welcome"); // Redirect to a welcome page on successful login
        }
        return new ModelAndView("login"); // Return to login page if authentication fails
    }

    //skips login and go directly to main page
    @GetMapping("/skip")
    public String skipLogin() {
        return "index"; // Skip login and go directly to the main page
    }

    @GetMapping("/dragAndDrop")
    public String dragAndDrop() {
        return "dragAndDrop"; // Maps to drag-and-drop.html in the templates directory
    }

}
