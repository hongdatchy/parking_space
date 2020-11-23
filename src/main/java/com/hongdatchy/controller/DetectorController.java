package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.service.DetectorService;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DetectorController {

    @Autowired
    DetectorService detectorService;

    @PostMapping("api/detector")
    public ResponseEntity<Object> createAndUpdate(@RequestBody DetectorPayload detectorPayload){
        return ResponseEntity.ok(MyResponse.success(detectorService.createAndUpdate(detectorPayload)));
    }

    @GetMapping("api/detector/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(detectorService.findAll()));
    }

    @DeleteMapping("api/detector/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(detectorService.delete(id)));
    }

}
