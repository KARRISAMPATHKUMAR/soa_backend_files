package com.klu.springmvc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.springmvc.model.User;
import com.klu.springmvc.service.UserService;

@RestController
@RequestMapping("/ms1")

public class MS1Controller {

    @Autowired
    private UserService us;


    // Test endpoint
    @GetMapping("/test")
    public String test() {
        return "Patient Service is working";
    }


    // Patient Signup
    @PostMapping("/signup")
    public Object signup(@RequestBody User user) {
        return us.signupservice(user);
    }


    // Patient Signin
    @PostMapping("/signin")
    public Object signin(@RequestBody Map<String, String> user) {
        return us.signinservice(user);
    }
}