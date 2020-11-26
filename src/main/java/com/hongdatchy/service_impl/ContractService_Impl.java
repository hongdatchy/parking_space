package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.payload.ContractPayload;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractService_Impl implements ContractService {

    @Autowired
    ContractRepo contractRepo;

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
                .invoiceId(contractPayload.getInvoiceId())
                .slotId(contractPayload.getSlotId())
                .timeCarIn(null)
                .timeCarOut(null)
                .timeOutBook(contractPayload.getTimeOutBook())
                .timeInBook(contractPayload.getTimeInBook())
                .build();
    }

}
