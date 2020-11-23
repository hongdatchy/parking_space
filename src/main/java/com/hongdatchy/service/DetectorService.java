package com.hongdatchy.service;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.payload.DetectorPayload;

import java.util.List;

public interface DetectorService {

    Detector createAndUpdate(DetectorPayload detectorPayload);

    boolean delete(int id);

    List<Detector> findAll();
}
