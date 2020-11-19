package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Gateway;

import java.util.List;

public interface GatewayRepo {

    Gateway createAndUpdate(Gateway gateway);

    boolean delete(int id);

    List<Gateway> findAll();

    Gateway findById(int id);
}
