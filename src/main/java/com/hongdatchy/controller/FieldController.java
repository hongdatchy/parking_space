package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FieldController {

    @Autowired
    FieldService fieldService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/field")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Field field){
        return ResponseEntity.ok(MyResponse.success(fieldService.createAndUpdate(field)));
    }

    @GetMapping(value = {"api/field/find_all"})// can multiple mapping
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(fieldService.findAll()));
    }

    @DeleteMapping("api/field/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(fieldService.delete(id)));
    }

    @GetMapping(value = {"api/manager/field"})
    public ResponseEntity<Object> findSome(@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(fieldService.managerFind(phone)));
    }

    @PostMapping(value = {"api/manager/field"})
    public ResponseEntity<Object> updateSome(@RequestBody Field field ,@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(fieldService.managerUpdate(field,phone)));
    }
}
