package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.*;
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

    @PostMapping("api/public/register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm){
        if(!registerForm.getPassword().equals(registerForm.getRePassword())){
            return ResponseEntity.ok(MyResponse.fail("password is not equal with rePassword"));
        }
        return ResponseEntity.ok(MyResponse.success(userService.register(registerForm)));
    }

    @PostMapping("api/ad/user/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody UserPayload userPayload){
        return ResponseEntity.ok(MyResponse.success(userService.createAndUpdate(userPayload)));
    }

    @GetMapping("api/ad/user/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(userService.findAll()));
    }

    @DeleteMapping("api/ad/user/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(userService.delete(id)));
    }

    @PostMapping("api/us/book")
    public ResponseEntity<Object> book(@RequestBody BookPayload bookPayload, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.book(bookPayload, phone)));
    }

    @PostMapping("api/us/changePass")
    public ResponseEntity<Object> changePass(@RequestBody ChangePassForm changePassForm, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.changePass(changePassForm, phone)));
    }

    @PostMapping("api/public/verify")
    public ResponseEntity<Object> verify(@RequestBody VerifyPayload verifyPayload){
        return ResponseEntity.ok(MyResponse.success(userService.verifyAccount(verifyPayload.getEmail(), verifyPayload.getCode())));
    }

}
