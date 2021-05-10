package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.json.ContractJson;
import com.hongdatchy.entities.payload.ContractPayload;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService_Impl implements ContractService {



    @Value("${timeConditionDelay}")
    String timeConditionDelay;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    FieldRepo fieldRepo;

    @Override
    public Contract createAndUpdate(ContractPayload contractPayload) {
        return contractRepo.createAndUpdate(payload2data(contractPayload));
    }

    @Override
    public boolean delete(int id) {
        return contractRepo.delete(id);
    }

    @Override
    public List<Contract> findAll() {
        return contractRepo.findAll();
    }

    public Contract payload2data(ContractPayload contractPayload){
        return Contract.builder()
                .id(contractPayload.getId())
                .fieldId(contractPayload.getFieldId())
                .userId(contractPayload.getUserId())
                .timeCarIn(null)
                .timeCarOut(null)
                .timeOutBook(contractPayload.getTimeOutBook())
                .timeInBook(contractPayload.getTimeInBook())
                .carNumber(contractPayload.getCarNumber())
                .dtCreate(contractPayload.getDtCreate())
                .status(contractPayload.getStatus())
                .build();
    }

    public ContractJson data2Json(Contract contract){
        List<Field> fields = fieldRepo.findAll().stream()
                .filter(field -> (field.getId().equals(contract.getFieldId())))
                .collect(Collectors.toList());
        if(fields.size() == 0){
            return null;
        }
        float price= Float.parseFloat(fields.get(0).getPrice());
        Timestamp timeCostIn, timeCostOut;
        if(contract.getTimeCarIn().getTime() < contract.getTimeInBook().getTime() + Integer.parseInt(timeConditionDelay)){
            timeCostIn = contract.getTimeCarIn();
        }else {
            timeCostIn = contract.getTimeInBook();
        }
        if(contract.getTimeCarOut().getTime() < contract.getTimeOutBook().getTime() - Integer.parseInt(timeConditionDelay)){
            timeCostOut = contract.getTimeOutBook();
        }else {
            timeCostOut = contract.getTimeCarOut();
        }

        float cost = (float)(timeCostOut.getTime() - timeCostIn.getTime())/1000/60/60 * price;
        return ContractJson.builder()
                .id(contract.getId())
                .carNumber(contract.getCarNumber())
                .dtCreate(contract.getDtCreate())
                .fieldId(contract.getFieldId())
                .status(contract.getStatus())
                .timeCarIn(contract.getTimeCarIn())
                .timeCarOut(contract.getTimeCarOut())
                .timeInBook(contract.getTimeInBook())
                .timeOutBook(contract.getTimeOutBook())
                .userId(contract.getUserId())
                .cost(String.valueOf(cost))
                .build();
    }

}
