package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.repository.ManagerRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class ManagerRepo_Impl implements ManagerRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Manager createAndUpdate(Manager manager) {
        return entityManager.merge(manager);
    }

    @Override
    public boolean delete(int id) {
        Manager manager = entityManager.find(Manager.class, id);
        if(manager != null){
            Query query = entityManager.createQuery("delete from ManagerField x where x.fieldId =:id");
            query.setParameter("id", id).executeUpdate();

            entityManager.remove(manager);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Manager> findAll() {
        return entityManager.createQuery("select x from Manager x").getResultList();
    }
}
