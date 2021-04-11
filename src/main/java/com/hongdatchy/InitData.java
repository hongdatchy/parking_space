package com.hongdatchy;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.GatewayRepo;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class InitData {

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private GatewayRepo gatewayRepo;

    @Autowired
    SlotRepo slotRepo;

    @PostConstruct
    public void init(){
        if(fieldRepo.findAll().size() == 0){
            System.out.println("Init data start");
            fieldRepo.createAndUpdate(new Field(1,"C9", "lat, long", "số 1 đại cồ việt"));
            fieldRepo.createAndUpdate(new Field(2,"C3", "lat, long", "số 1 đại cồ việt"));
            gatewayRepo.createAndUpdate(new Gateway(1,1,"1.1.1.1"));
            gatewayRepo.createAndUpdate(new Gateway(2,2,"2.2.2.2"));
            for (int i = 0; i < 200; i++) {
                slotRepo.createAndUpdate(new Slot(i+1, 1, null, null));
            }
            for (int i = 0; i < 200; i++) {
                slotRepo.createAndUpdate(new Slot(i+201, 2, null, null));
            }
            System.out.println("Init data end");
        }
    }
}