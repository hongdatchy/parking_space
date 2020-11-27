package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.BookPayload;
import com.hongdatchy.entities.payload.RegisterForm;
import com.hongdatchy.entities.payload.UserPayload;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/public/register/create_and_update")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm){
        if(!registerForm.getPassword().equals(registerForm.getRePassword())){
            return ResponseEntity.ok(MyResponse.fail("password is not equal with rePassword"));
        }
        if(!userService.register(registerForm)){
            return ResponseEntity.ok(MyResponse.fail("phone number already exist"));
        }
        return ResponseEntity.ok(MyResponse.success(true));
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
    public ResponseEntity<Object> book(@RequestBody List<BookPayload> bookPayloads, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.book(bookPayloads, phone)));
    }

}
