package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.payload.ManagerPayload;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ManagerService_Impl implements ManagerService {

    @Autowired
    ManagerRepo managerRepo;

    @Override
    public Manager createAndUpdate(ManagerPayload  managerPayload) {
        return managerRepo.createAndUpdate(payload2data(managerPayload));
    }

    @Override
    public boolean delete(int id) {
        return managerRepo.delete(id);
    }

    @Override
    public List<Manager> findAll() {
        return managerRepo.findAll();
    }

    public Manager payload2data(ManagerPayload managerPayload){
        return Manager.builder()
                .id(managerPayload.getId())
                .acp(managerPayload.getAcp())
                .pass(managerPayload.getPass())
                .lastTimeAccess(new Date())
                .build();
    }
}
