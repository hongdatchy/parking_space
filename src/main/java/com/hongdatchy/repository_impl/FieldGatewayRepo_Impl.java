package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.FieldGateway;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.repository.FieldGatewayRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class FieldGatewayRepo_Impl implements FieldGatewayRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public FieldGateway createAndUpdate(FieldGateway fieldGateway) {
        if(fieldGateway.getFieldId() != null
                || entityManager.find(FieldGateway.class, fieldGateway.getId()) != null){
            FieldGateway oldFieldGateway =entityManager.find(FieldGateway.class, fieldGateway.getId());
            fieldGateway.setLastTimeSetup(oldFieldGateway.getLastTimeSetup());
        }
        return entityManager.merge(fieldGateway);
    }

    @Override
    public boolean delete(int id) {
        FieldGateway fieldGateway = entityManager.find(FieldGateway.class, id);
        if(fieldGateway != null){
            entityManager.remove(fieldGateway);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<FieldGateway> findAll() {
        Query query = entityManager.createQuery("select x from FieldGateway x");
        return query.getResultList();
    }

}
