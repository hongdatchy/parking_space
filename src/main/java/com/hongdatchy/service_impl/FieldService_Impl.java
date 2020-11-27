package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.json.FieldJson;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.ManagerRepo;
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

    @Autowired
    ManagerRepo managerRepo;

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

    @Override
    public List<Field> managerFind(String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        if(manager == null){
            return null;
        }
        return fieldRepo.managerFind(manager);
    }

    @Override
    public Field managerUpdate(Field field, String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        if(manager == null){
            return null;
        }
        return fieldRepo.managerUpdate(field, manager);
    }

    @Override
    public boolean managerDelete(int id, String phone) {
        Manager manager = managerRepo.findByPhone(phone);
        if(manager == null){
            return false;
        }
        return fieldRepo.managerDelete(id, manager);
    }

    public FieldJson data2Json(Field field) {
        return FieldJson.builder()
                .id(field.getId())
                .busySlot(slotRepo.findAll().stream().filter(slot -> slot.getStatus() && slot.getFieldId() == field.getId())
                        .collect(Collectors.toList()).size())
                .position(field.getPosition())
                .totalSlot(slotRepo.findAll().stream().filter(slot -> slot.getFieldId() == field.getId())
                        .collect(Collectors.toList()).size())
                .build();
    }

}
