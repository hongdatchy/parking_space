package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FieldController {

    @Autowired
    FieldService fieldService;

    @PostMapping("api/field")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Field field){
        return ResponseEntity.ok(MyResponse.success(fieldService.createAndUpdate(field)));
    }

    @GetMapping(value = {"api/admin/field/find_all"})// can multiple mapping
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(fieldService.findAll()));
    }

    @DeleteMapping("api/field/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(fieldService.delete(id)));
    }

}
