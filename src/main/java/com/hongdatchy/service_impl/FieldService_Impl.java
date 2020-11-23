package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.json.FieldJson;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService_Impl implements FieldService {

    @Autowired
    FieldRepo fieldRepo;

    @Autowired
    SlotRepo slotRepo;

    @Override
    public FieldJson createAndUpdate(Field field) {
        return data2Json(fieldRepo.createAndUpdate(field));
    }

    @Override
    public boolean delete(int id) {
        return fieldRepo.delete(id);
    }

    @Override
    public List<FieldJson> findAll() {
        return fieldRepo.findAll().stream().map(field -> data2Json(field)).collect(Collectors.toList());
    }

    public FieldJson data2Json(Field field) {
        return FieldJson.builder()
                .id(field.getId())
                .busySlot(slotRepo.findAll().stream().filter(slot -> slot.getStatus()== true)
                        .collect(Collectors.toList()).size())
                .position(field.getPosition())
                .totalSlot(slotRepo.findAll().size())
                .build();
    }

}
