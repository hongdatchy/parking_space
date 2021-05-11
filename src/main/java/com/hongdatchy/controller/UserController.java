package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.*;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    ContractRepo contractRepo;

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
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.book(bookPayload, email)));
    }

    @PostMapping("api/us/changePass")
    public ResponseEntity<Object> changePass(@RequestBody ChangePassForm changePassForm, @RequestHeader String token){
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.changePass(changePassForm, email)));
    }

    @PostMapping("api/public/verify")
    public ResponseEntity<Object> verify(@RequestBody VerifyPayload verifyPayload){
        return ResponseEntity.ok(MyResponse.success(userService.verifyAccount(verifyPayload.getEmail(), verifyPayload.getCode())));
    }

//    @PostMapping("api/us/update_time")
//    public ResponseEntity<Object> updateTime(@RequestBody TimeUpdateForm timeUpdateForm, @RequestHeader String token) throws ParseException {
//        String email = jwtService.decode(token);
//        return ResponseEntity.ok(MyResponse.success(userService.updateTime(timeUpdateForm, email)));
//    }

    @GetMapping("api/us/get_list_contract")
    public ResponseEntity<Object> getListContract(@RequestHeader String token) throws ParseException {
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.getListContract(email)));
    }

        @PostMapping("api/us/update_contract_for_user")
    public ResponseEntity<Object> updateContractForUser(@RequestBody ContractPayload contractPayload, @RequestHeader String token) throws ParseException {
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(userService.updateContractForUser(contractPayload, email)));
    }

}
