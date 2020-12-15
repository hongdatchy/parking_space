package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.service.DetectorService;
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
    ManagerRepo managerRepo;

    @Override
    public Detector createAndUpdate(DetectorPayload detectorPayload) {
        System.out.println(payLoad2Data(detectorPayload));
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
        Manager manager = managerRepo.findByPhone(phone);
        return detectorRepo.managerFind(manager);
    }

    @Override
    public Detector managerCreateAndUpdate(DetectorPayload detectorPayload, String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        return detectorRepo.managerCreateAndUpdate(payLoad2Data(detectorPayload), manager);
    }

    @Override
    public boolean managerDelete(int id, String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        return detectorRepo.managerDelete(id, manager);
    }

    @Override
    public Detector findById(int id) {
        return detectorRepo.findById(id);
    }

    @Override
    public Detector managerFindById(int id, String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        return detectorRepo.managerFindById(id, manager);
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
