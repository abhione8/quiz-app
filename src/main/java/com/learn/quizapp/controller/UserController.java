package com.learn.quizapp.controller;

import com.learn.quizapp.model.User;
import com.learn.quizapp.service.AuthService;
import com.learn.quizapp.service.UserDetailService;
import com.learn.quizapp.vo.ApiResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponseVO<String>> saveUser(@RequestBody User user){
        if(user.getPassword().isEmpty() || user.getUsername().isEmpty()){
            return ResponseEntity.ok(
                    new ApiResponseVO<>("error", "Please provide username and password.")
            );
        }
        if(userDetailService.saveUser(user)){
            return ResponseEntity.ok(
                    new ApiResponseVO<>("success", "User created.")
            );
        }
        else {
            return ResponseEntity.ok(
                    new ApiResponseVO<>("success", "User already exist.")
            );
        }
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponseVO<String>> login(@RequestBody User user){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", authService.authenticate(user.getUsername(), user.getPassword()))
        );
    }
    @PostMapping("generateToken")
    public ResponseEntity<ApiResponseVO<String>> generateToken(@RequestBody User user){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", "valid till 30 minutes : "+authService.authenticate(user.getUsername(), user.getPassword()))
        );
    }
}
