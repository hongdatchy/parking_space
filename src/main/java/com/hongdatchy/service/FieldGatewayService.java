package com.hongdatchy.service;

import com.hongdatchy.entities.data.FieldGateway;
import com.hongdatchy.entities.payload.FieldGatewayPayload;

import java.util.List;

public interface FieldGatewayService {

    FieldGateway createAndUpdate(FieldGatewayPayload fieldGatewayPayload);

    boolean delete(int id);

    List<FieldGateway> findAll();
}
