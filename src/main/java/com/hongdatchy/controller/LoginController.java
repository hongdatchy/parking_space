package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.AdminService;
import com.hongdatchy.service.ManagerService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    AdminService adminService;

    @Autowired
    JWTService jwtService;


    @PostMapping("api/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) throws Exception {
        if(userService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("user",jwtService.getToken(loginForm.getPhone())));
        }else if(managerService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("manager",jwtService.getToken(loginForm.getPhone())));
        }else if(adminService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("admin",jwtService.getToken(loginForm.getPhone())));
        }
        return ResponseEntity.ok(MyResponse.fail("wrong phone or password"));
    }

}
