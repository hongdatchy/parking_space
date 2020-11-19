package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.repository.DetectorRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class DetectorRepo_Impl implements DetectorRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Detector createAndUpdate(Detector detector) {
        List<Detector> detectors = findBySlotId(detector.getSlotId());
        if(detectors.size() != 0){
            return null;
        }
        if(detector.getId() == null || entityManager.find(Detector.class, detector.getId()) == null){
            detector.setLastTimeSetup(new Date());
        }else{
            detector.setLastTimeSetup(entityManager.find(Detector.class, detector.getId()).getLastTimeSetup());
        }
        detector.setLastTimeUpdate(new Date());
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
}
