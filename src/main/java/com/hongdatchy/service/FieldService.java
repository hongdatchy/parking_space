package com.hongdatchy.service;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.json.FieldJson;

import java.util.List;

public interface FieldService {

    FieldJson createAndUpdate(Field field);

    boolean delete(int id);

    List<FieldJson> findAll();

}
