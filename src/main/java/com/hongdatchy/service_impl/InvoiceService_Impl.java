package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Invoice;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.InvoiceJson;
import com.hongdatchy.entities.json.SlotJson;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.InvoiceRepo;
import com.hongdatchy.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService_Impl implements InvoiceService {

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    ContractRepo contractRepo;

    @Override
    public InvoiceJson createAndUpdate(Invoice invoice) {
        return data2Json(invoiceRepo.createAndUpdate(invoice));
    }

    @Override
    public boolean delete(int id) {
        return invoiceRepo.delete(id);
    }

    @Override
    public List<InvoiceJson> findAll() {
        return invoiceRepo.findAll().stream()
                .map(invoice -> data2Json(invoice))
                .collect(Collectors.toList());
    }
    public InvoiceJson data2Json(Invoice invoice){
        List<Contract> contracts = contractRepo.findAll();
        if(contracts.size() != 0){
            contracts = null;
        }else{
            contracts = contracts.stream()
                    .filter(contract -> contract.getInvoiceId() == invoice.getId())
                    .collect(Collectors.toList());
        }
        return InvoiceJson.builder()
                .id(invoice.getId())
                .contracts(contracts)
                .build();
    }
}
