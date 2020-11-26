package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.GatewayJson;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.GatewayRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.service.FieldService;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatewayService_Impl implements GatewayService {

    @Autowired
    GatewayRepo gatewayRepo;

    @Autowired
    DetectorRepo detectorRepo;

    @Autowired
    FieldService fieldService;

    @Override
    public GatewayJson createAndUpdate(Gateway gateway) {
        return data2Json(gatewayRepo.createAndUpdate(gateway));
    }

    @Override
    public boolean delete(int id) {
        return gatewayRepo.delete(id);
    }

    @Override
    public List<GatewayJson> findAll() {
        return gatewayRepo.findAll().stream().map(gateway -> data2Json(gateway)).collect(Collectors.toList());
    }

    @Override
    public List<Gateway> managerFind(String phone) {
        List<Field> fields = fieldService.managerFind(phone);
        return gatewayRepo.managerFind(fields);
    }

    public GatewayJson data2Json(Gateway gateway){
        return GatewayJson.builder()
            .id(gateway.getId())
            .address(gateway.getAddressGateway())
            .totalDetector(detectorRepo.findAll().size())
            .build();
    }
}
