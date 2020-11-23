package com.hongdatchy.service;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.FieldJson;
import com.hongdatchy.entities.json.GatewayJson;

import java.util.List;

public interface GatewayService {

    GatewayJson createAndUpdate(Gateway gateway);

    boolean delete(int id);

    List<GatewayJson> findAll();



}
