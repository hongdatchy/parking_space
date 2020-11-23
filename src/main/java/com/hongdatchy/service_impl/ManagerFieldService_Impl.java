package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.data.ManagerField;
import com.hongdatchy.entities.payload.ManagerFieldPayload;
import com.hongdatchy.repository.ManagerFieldRepo;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.service.ManagerFieldService;
import com.hongdatchy.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ManagerFieldService_Impl implements ManagerFieldService {

    @Autowired
    ManagerFieldRepo managerFieldRepo;


    @Override
    public ManagerField createAndUpdate(ManagerFieldPayload managerFieldPayload) {
        return managerFieldRepo.createAndUpdate(payload2Data(managerFieldPayload));
    }

    @Override
    public boolean delete(int id) {
        return managerFieldRepo.delete(id);
    }

    @Override
    public List<ManagerField> findAll() {
        return managerFieldRepo.findAll();
    }

    public ManagerField payload2Data(ManagerFieldPayload managerFieldPayload){
        return ManagerField.builder()
                .id(managerFieldPayload.getId())
                .lastTimeSetup(new Date())
                .fieldId(managerFieldPayload.getFieldId())
                .managerId(managerFieldPayload.getManagerId())
                .build();
    }
}
