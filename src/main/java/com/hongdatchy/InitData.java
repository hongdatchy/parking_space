package com.hongdatchy;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.MyPackage;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.GatewayRepo;
import com.hongdatchy.repository.PackageRepo;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class InitData {

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private GatewayRepo gatewayRepo;

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    PackageRepo packageRepo;

    @PostConstruct
    public void init(){
        if(fieldRepo.findAll().get(0).getId() != 1){
            System.out.println("Init data start");
            fieldRepo.createAndUpdate(new Field(1,"C9", "lat", "long", "số 1 đại cồ việt", "image", "price", "O", new BigDecimal("0.0"), "details"));
            fieldRepo.createAndUpdate(new Field(2,"Trần Đại Nghĩa", "lat", "long", "số 1 đại cồ việt", "image", "price", "O", new BigDecimal("0.0"), "details"));
            gatewayRepo.createAndUpdate(new Gateway(1,1,"1.1.1.1"));
            gatewayRepo.createAndUpdate(new Gateway(2,2,"2.2.2.2"));
            for (int i = 0; i < 50; i++) {
                slotRepo.createAndUpdate(new Slot(i+1, 1, null, null));
            }
            for (int i = 0; i < 50; i++) {
                slotRepo.createAndUpdate(new Slot(i+201, 2, null, null));
            }
            System.out.println("Init data end");
        }

//        if(packageRepo.findAll().size() > 0){
//            System.out.println("Init data2 start");
//            for (int i = 50000; i < 60000; i++) {
//                packageRepo.create(MyPackage.builder()
//                        .id(i+1)
//                        .idNode("idNode")
//                        .batteryLevel("ssssssssss")
//                        .state(true)
//                        .nodeAddress("nodeAddress")
//                        .communicationLevel("communicationLevel")
//                        .location("location")
//                        .packetNumber("packetNumber")
//                        .time("20210401132437")
//                        .build());
//            }
//            System.out.println("Init data2 end");
//        }
    }
}