package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.FieldGateway;
import com.hongdatchy.entities.payload.FieldGatewayPayload;
import com.hongdatchy.repository.FieldGatewayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class FieldGatewayService_Impl implements com.hongdatchy.service.FieldGatewayService {

    @Autowired
    FieldGatewayRepo fieldGatewayRepo;


    @Override
    public FieldGateway createAndUpdate(FieldGatewayPayload fieldGatewayPayload) {
        return fieldGatewayRepo.createAndUpdate(payload2Data(fieldGatewayPayload));
    }

    @Override
    public boolean delete(int id) {
        return fieldGatewayRepo.delete(id);
    }

    @Override
    public List<FieldGateway> findAll() {
        return fieldGatewayRepo.findAll();
    }

    public FieldGateway payload2Data(FieldGatewayPayload fieldGatewayPayload){
//        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        myDate.setTimeZone(TimeZone.getTimeZone(""));
//        Date newDate = myDate.parse("2010-05-23T09:01:02");
        return FieldGateway.builder()
                .id(fieldGatewayPayload.getId())
                .fieldId(fieldGatewayPayload.getFieldId())
                .gatewayId(fieldGatewayPayload.getGatewayId())

                .lastTimeSetup(new Date())
                .build();
    }
}
