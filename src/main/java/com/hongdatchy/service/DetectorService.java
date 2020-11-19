package com.hongdatchy.service;

import com.hongdatchy.entities.data.Detector;

import java.util.List;

public interface DetectorService {

    Detector createAndUpdate(Detector slot);

    boolean delete(int id);

    List<Detector> findAll();
}
