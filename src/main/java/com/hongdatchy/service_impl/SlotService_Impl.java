package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.GatewayJson;
import com.hongdatchy.entities.json.SlotJson;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.GatewayRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService_Impl implements SlotService {

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    DetectorRepo detectorRepo;

    @Autowired
    GatewayRepo gatewayRepo;

    @Override
    public SlotJson createAndUpdate(Slot slot) {
        Slot newSlot = slotRepo.createAndUpdate(slot);
        return data2Json(newSlot);
    }

    @Override
    public boolean delete(int id) {
        return slotRepo.delete(id);
    }

    @Override
    public List<SlotJson> findAll() {
        return slotRepo.findAll().stream().map(slot -> data2Json(slot)).collect(Collectors.toList());
    }

    public SlotJson data2Json(Slot slot){
        List<Detector> detectors = detectorRepo.findBySlotId(slot.getId());
        return SlotJson.builder()
                .id(slot.getId())
                .AddressDetector(detectors.size() != 0 ? detectors.get(0).getAddressDetector() : null)
                .AddressGateway(detectors.size() != 0 ? gatewayRepo.findById(detectors.get(0).getGatewayId()).getAddressGateway(): null)
                .fieldId(slot.getFieldId())
                .lastTimeSetup(detectors.size() != 0 ? detectors.get(0).getLastTimeSetup(): null)
                .lastTimeUpdate(detectors.size() != 0 ? detectors.get(0).getLastTimeUpdate(): null)
                .status(slot.getStatus())
                .build();
    }
}
