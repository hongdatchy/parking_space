package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.service.DetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectorService_Impl implements DetectorService {

    @Autowired
    DetectorRepo detectorRepo;

    @Override
    public Detector createAndUpdate(Detector detector) {
        return detectorRepo.createAndUpdate(detector);
    }

    @Override
    public boolean delete(int id) {
        return detectorRepo.delete(id);
    }

    @Override
    public List<Detector> findAll() {
        return detectorRepo.findAll();
    }
}
