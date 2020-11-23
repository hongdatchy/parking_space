package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.entities.payload.FieldGatewayPayload;
import com.hongdatchy.service.DetectorService;
import com.hongdatchy.service.FieldGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FieldGatewayController {

    @Autowired
    FieldGatewayService fieldGatewayService;

    @PostMapping("api/fieldGateway")
    public ResponseEntity<Object> createAndUpdate(@RequestBody FieldGatewayPayload fieldGatewayPayload){
        return ResponseEntity.ok(MyResponse.success(fieldGatewayService.createAndUpdate(fieldGatewayPayload)));
    }

    @GetMapping("api/fieldGateway/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(fieldGatewayService.findAll()));
    }

    @DeleteMapping("api/fieldGateway/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(fieldGatewayService.delete(id)));
    }
}
