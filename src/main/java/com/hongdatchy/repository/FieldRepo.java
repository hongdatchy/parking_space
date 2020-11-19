package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Field;

import java.util.List;

public interface FieldRepo{

    Field createAndUpdate(Field field);

    boolean delete(int id);

    List<Field> findAll();

}
