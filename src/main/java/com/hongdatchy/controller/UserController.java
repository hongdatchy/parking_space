package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/user/register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm){
        if(!registerForm.getPassword().equals(registerForm.getRePassword())){
            return ResponseEntity.ok(MyResponse.fail("password is not equal with rePassword"));
        }
        if(!userService.register(registerForm)){
            return ResponseEntity.ok(MyResponse.fail("phone number already exist"));
        }
        return ResponseEntity.ok(MyResponse.success(true));
    }

    @PostMapping("api/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) throws Exception {
        if(userService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.success(jwtService.getToken(loginForm.getPhone())));
        }
        return ResponseEntity.ok(MyResponse.fail("wrong phone or password"));
    }

    @GetMapping("api/user/logout/{token}")
    public ResponseEntity<Object> logout(@PathVariable String token){
        return ResponseEntity.ok(MyResponse.success(userService.logout(token)));
    }

}
