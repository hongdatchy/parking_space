package com.hongdatchy.repository;

import com.hongdatchy.entities.data.FieldGateway;

import java.util.List;

public interface FieldGatewayRepo {

    FieldGateway createAndUpdate(FieldGateway fieldGw);

    boolean delete(int id);

    List<FieldGateway> findAll();
}
