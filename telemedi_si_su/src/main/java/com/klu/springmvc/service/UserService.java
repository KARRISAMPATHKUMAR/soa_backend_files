package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.User;
import com.klu.springmvc.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;


    @Autowired
    private JWTService jwtService;


    // =====================================================
    // PATIENT SIGNUP
    // =====================================================

    public Object signupservice(User user) {

        Map<String, Object> response =
                new HashMap<>();


        try {

            // Basic validation
            if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {

                response.put("code", 400);
                response.put(
                        "message",
                        "Username is required"
                );

                return response;
            }


            if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {

                response.put("code", 400);
                response.put(
                        "message",
                        "Password is required"
                );

                return response;
            }


            // Check whether patient already exists
            User existingUser =
                    repo.findByUsername(
                            user.getUsername()
                    );


            if (existingUser != null) {

                response.put("code", 409);
                response.put(
                        "message",
                        "Patient is already registered"
                );

                return response;
            }


            // Every user registering through this
            // service is a Patient
            user.setRole(1);


            repo.save(user);


            response.put("code", 200);
            response.put(
                    "message",
                    "Patient registration successful"
            );


        } catch (Exception e) {

            response.put("code", 500);
            response.put(
                    "message",
                    "Registration failed"
            );

            response.put(
                    "error",
                    e.getMessage()
            );
        }


        return response;
    }



    // =====================================================
    // PATIENT SIGNIN
    // =====================================================

    public Object signinservice(
            Map<String, String> userData) {


        Map<String, Object> response =
                new HashMap<>();


        try {

            String username =
                    userData.get("username");

            String password =
                    userData.get("password");


            if (username == null ||
                password == null) {

                response.put("code", 400);
                response.put(
                        "message",
                        "Username and password are required"
                );

                return response;
            }


            User user =
                    repo.findByUsername(username);


            // Authentication
            if (user == null ||
                !user.getPassword().equals(password)) {

                response.put("code", 401);
                response.put(
                        "message",
                        "Invalid username or password"
                );

                return response;
            }


            // Generate JWT
            String token =
                    jwtService.generateJWT(
                            userData,
                            String.valueOf(
                                    user.getRole()
                            ),
                            user.getId()
                    );


            response.put("code", 200);

            response.put(
                    "message",
                    "Patient login successful"
            );


            // Keep this name because our current
            // React login can use it.
            response.put(
                    "JWT Token",
                    token
            );


            // Also return normal token field
            response.put(
                    "token",
                    token
            );


            response.put(
                    "userId",
                    user.getId()
            );


            response.put(
                    "username",
                    user.getUsername()
            );


            response.put(
                    "role",
                    user.getRole()
            );


        } catch (Exception e) {

            response.put("code", 500);

            response.put(
                    "message",
                    "Login failed"
            );

            response.put(
                    "error",
                    e.getMessage()
            );
        }


        return response;
    }
}