package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Manager;

import java.util.List;

public interface ManagerRepo {

    Manager createAndUpdate(Manager manager);

    boolean delete(int id);

    List<Manager> findAll();
}
