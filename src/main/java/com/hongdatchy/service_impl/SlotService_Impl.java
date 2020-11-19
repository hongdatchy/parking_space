package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.SlotJson;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.GatewayRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        List<Detector> detectors = detectorRepo.findBySlotId(newSlot.getId());
        SlotJson slotJson = SlotJson.builder()
                .id(newSlot.getId())
                .AddressDetector(detectors.size() != 0 ? detectors.get(0).getAddressDetector() : null)
                .AddressGateway(detectors.size() != 0 ? gatewayRepo.findById(detectors.get(0).getGatewayId()).getAddressGateway(): null)
                .fieldId(newSlot.getFieldId())
                .lastTimeSetup(detectors.size() != 0 ? detectors.get(0).getLastTimeSetup(): null)
                .lastTimeUpdate(detectors.size() != 0 ? detectors.get(0).getLastTimeUpdate(): null)
                .status(newSlot.getStatus())
                .build();
        return slotJson;
    }

    @Override
    public boolean delete(int id) {
        return slotRepo.delete(id);
    }

    @Override
    public List<Slot> findAll() {
        return slotRepo.findAll();
    }
}
