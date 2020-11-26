package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;

import java.util.List;

public interface DetectorRepo {

    Detector createAndUpdate(Detector detector);

    boolean delete(int id);

    List<Detector> findAll();

    List<Detector> findBySlotId(int id);

    List<Detector> managerFind(List<Gateway> gateways);
}
