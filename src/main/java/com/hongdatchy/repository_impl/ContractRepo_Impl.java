package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class ContractRepo_Impl implements ContractRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SlotRepo slotRepo;

    @Override
    public Contract createAndUpdate(Contract contract) {
        Slot slot = slotRepo.findById(contract.getSlotId());
        if(contract.getTimeInBook().compareTo(contract.getTimeOutBook()) >= 0
                || contract.getTimeInBook().compareTo(new Date()) < 0
                || slot == null
                || slot.getStatus()){
            return null;
        }
        slot.setStatus(true);
        entityManager.merge(slot);
        return entityManager.merge(contract);
    }

    @Override
    public boolean delete(int id) {
        Contract contract = entityManager.find(Contract.class, id);
        if(contract != null){
            entityManager.remove(contract);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Contract> findAll() {
        return  entityManager.createQuery("select g from Contract g").getResultList();
    }

    @Override
    public List<Contract> findBySlotId(int id) {
        return entityManager.createQuery("select x from Contract x where x.slotId = :slotId")
                .setParameter("slotId", id)
                .getResultList();
    }

    @Override
    public Contract updateTimeInOut(Contract contract) {
        return entityManager.merge(contract);
    }

}
