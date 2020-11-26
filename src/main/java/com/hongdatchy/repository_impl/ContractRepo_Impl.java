package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.ContractRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class ContractRepo_Impl implements ContractRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Contract createAndUpdate(Contract contract) {
        Slot slot = entityManager.find(Slot.class, contract.getSlotId());
        if(contract.getTimeInBook().compareTo(contract.getTimeOutBook()) >= 0 || slot.getStatus()){
            return null;
        }
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
        Query query = entityManager.createQuery("select g from Contract g");
        return query.getResultList();
    }

}
