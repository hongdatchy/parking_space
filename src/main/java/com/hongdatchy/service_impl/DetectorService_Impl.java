package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.service.DetectorService;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DetectorService_Impl implements DetectorService {

    @Autowired
    DetectorRepo detectorRepo;

    @Autowired
    FieldRepo fieldRepo;

    @Autowired
    GatewayService gatewayService;

    @Override
    public Detector createAndUpdate(DetectorPayload detectorPayload) {
        return detectorRepo.createAndUpdate(payLoad2Data(detectorPayload));
    }

    @Override
    public boolean delete(int id) {
        return detectorRepo.delete(id);
    }

    @Override
    public List<Detector> findAll() {
        return detectorRepo.findAll();
    }

    @Override
    public List<Detector> managerFind(String phone) {
        List<Gateway> gateways = gatewayService.managerFind(phone);
        return detectorRepo.managerFind(gateways);
    }

    public Detector payLoad2Data(DetectorPayload detectorPayload){
        return Detector.builder()
                .id(detectorPayload.getId())
                .addressDetector(detectorPayload.getAddressDetector())
                .batteryLevel(detectorPayload.getBatteryLevel())
                .gatewayId(detectorPayload.getGatewayId())
                .lastTimeSetup(new Date())
                .lastTimeUpdate(new Date())
                .loracomLevel(detectorPayload.getLoracomLevel())
                .operatingMode(detectorPayload.getOperatingMode())
                .slotId(detectorPayload.getSlotId())
                .build();
    }
}
