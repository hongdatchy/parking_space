package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.repository.DetectorRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class DetectorRepo_Impl implements DetectorRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Detector createAndUpdate(Detector detector) {
        if(detector.getId() == null || entityManager.find(Detector.class, detector.getId()) == null){
            List<Detector> detectors = findBySlotId(detector.getSlotId());
            if(detectors.size() != 0){
                return null;
            }
        }else{
            Detector oldDetector = entityManager.find(Detector.class, detector.getId());
            if(oldDetector.getSlotId() == detector.getSlotId()){
                // if slotId of detector not change -> keep stable lastTimeSetup
                detector.setLastTimeSetup(oldDetector.getLastTimeSetup());
            }
        }
        return entityManager.merge(detector);
    }

    @Override
    public boolean delete(int id) {
        Detector detector = entityManager.find(Detector.class, id);
        if(detector != null){
            entityManager.remove(detector);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Detector> findAll() {
        Query query = entityManager.createQuery("select d from Detector d");
        return query.getResultList();
    }

    @Override
    public List<Detector> findBySlotId(int id) {
        Query query = entityManager.createQuery("select d from Detector d where d.slotId = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Detector> managerFind(List<Gateway> gateways) {
        List<Detector> detectors = new ArrayList<>();
        for(Gateway gateway: gateways){
            detectors = (List<Detector>) Stream.concat(
                    detectors.stream(),
                    entityManager.createQuery("select x from Detector x where x.gatewayId =:id")
                    .setParameter("id", gateway.getId()).getResultList().stream())
                    .collect(Collectors.toList());
        }
        return detectors;
    }
}
