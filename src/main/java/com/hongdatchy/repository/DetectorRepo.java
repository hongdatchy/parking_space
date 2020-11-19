package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Detector;

import java.util.List;

public interface DetectorRepo {

    Detector createAndUpdate(Detector detector);

    boolean delete(int id);

    List<Detector> findAll();

    List<Detector> findBySlotId(int id);

}
