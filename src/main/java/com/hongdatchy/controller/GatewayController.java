package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

    @Autowired
    GatewayService gatewayService;

    @PostMapping("api/gateway")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Gateway gateway){
        return ResponseEntity.ok(MyResponse.success(gatewayService.createAndUpdate(gateway)));
    }

    @GetMapping("api/gateway/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(gatewayService.findAll()));
    }

    @DeleteMapping("api/gateway/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(gatewayService.delete(id)));
    }

}
