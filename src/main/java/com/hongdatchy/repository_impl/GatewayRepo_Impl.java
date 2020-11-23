package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.repository.GatewayRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class GatewayRepo_Impl implements GatewayRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Gateway createAndUpdate(Gateway gateway) {
        return entityManager.merge(gateway);
    }

    @Override
    public boolean delete(int id) {
        Gateway gateway = entityManager.find(Gateway.class, id);
        if(gateway != null){
            Query query = entityManager.createQuery("delete from Detector x where x.gatewayId =:id");
            query.setParameter("id", id).executeUpdate();

            Query query2 = entityManager.createQuery("delete from FieldGateway x where x.gatewayId =:id");
            query2.setParameter("id", id).executeUpdate();

            entityManager.remove(gateway);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Gateway> findAll() {
        Query query = entityManager.createQuery("select x from Gateway x");
        return query.getResultList();
    }

    @Override
    public Gateway findById(int id) {
        return entityManager.find(Gateway.class, id);
    }
}
