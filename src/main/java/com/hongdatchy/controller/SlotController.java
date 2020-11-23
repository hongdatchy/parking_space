package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SlotController {

    @Autowired
    SlotService slotService;

    @PostMapping("api/slot")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Slot slot){
        return ResponseEntity.ok(MyResponse.success(slotService.createAndUpdate(slot)));
    }

    @GetMapping("api/slot/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(slotService.findAll()));
    }

    @DeleteMapping("api/slot/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(slotService.delete(id)));
    }

}
