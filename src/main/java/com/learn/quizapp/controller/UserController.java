package com.learn.quizapp.controller;

import com.learn.quizapp.model.Users;
import com.learn.quizapp.service.AuthService;
import com.learn.quizapp.service.UserDetailService;
import com.learn.quizapp.vo.ApiResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponseVO<String>> saveUser(@RequestBody Users user){
        if(user.getPassword().isEmpty() || user.getUsername().isEmpty()){
            return ResponseEntity.ok(
                    new ApiResponseVO<>("success", "Please provide username and password.")
            );
        }
        userDetailService.saveUser(user);
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", "User created.")
        );
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponseVO<String>> login(@RequestBody Users user){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", authService.authenticate(user.getUsername(), user.getPassword()))
        );
    }
    @PostMapping("generateToken")
    public ResponseEntity<ApiResponseVO<String>> generateToken(@RequestBody Users user){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", authService.authenticate(user.getUsername(), user.getPassword()))
        );
    }
}
