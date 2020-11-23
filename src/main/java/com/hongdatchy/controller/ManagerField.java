package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.entities.payload.ManagerFieldPayload;
import com.hongdatchy.service.DetectorService;
import com.hongdatchy.service.ManagerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerField {
    @Autowired
    ManagerFieldService managerFieldService;

    @PostMapping("api/managerField")
    public ResponseEntity<Object> createAndUpdate(@RequestBody ManagerFieldPayload managerFieldPayload){
        return ResponseEntity.ok(MyResponse.success(managerFieldService.createAndUpdate(managerFieldPayload)));
    }

    @GetMapping("api/managerField/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(managerFieldService.findAll()));
    }

    @DeleteMapping("api/managerField/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(managerFieldService.delete(id)));
    }
}
