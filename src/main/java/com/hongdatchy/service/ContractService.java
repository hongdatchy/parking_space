package com.hongdatchy.service;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.payload.ContractPayload;

import java.util.List;


public interface ContractService {

    Contract createAndUpdate(ContractPayload contractPayload);

    boolean delete(int id);

    List<Contract> findAll();

    public Contract payload2data(ContractPayload contractPayload);
}
