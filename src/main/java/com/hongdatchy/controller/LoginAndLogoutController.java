package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.repository.AdminRepo;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.AdminService;
import com.hongdatchy.service.ManagerService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginAndLogoutController {

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    AdminService adminService;

    @Autowired
    JWTService jwtService;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ManagerRepo managerRepo;

    @PostMapping("api/public/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) throws Exception {
        if(userService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("user",jwtService.getToken(loginForm.getEmail())));
        }else if(managerService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("manager",jwtService.getToken(loginForm.getEmail())));
        }else if(adminService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("admin",jwtService.getToken(loginForm.getEmail())));
        }
        return ResponseEntity.ok(MyResponse.fail("wrong phone or password"));
    }
    @GetMapping("api/public/logout")
    public ResponseEntity<Object> logout(@RequestHeader String token){
        String phone = jwtService.decode(token);
        if(userRepo.findByEmail(phone) != null
                || managerRepo.findByEmail(phone) != null
                || adminRepo.findByEmail(phone) != null){
            return ResponseEntity.ok(MyResponse.success(true));
        }
        return ResponseEntity.ok(MyResponse.success(false));
    }
}
