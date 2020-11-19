package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.repository.FieldRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class FieldRepo_Impl implements FieldRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Field createAndUpdate(Field field) {
        return entityManager.merge(field);
    }

    @Override
    public boolean delete(int id) {
        Field field = entityManager.find(Field.class, id);
        if(field != null){
            Query query = entityManager.createQuery("delete from FieldGateway x where x.fieldId =:id");
            query.setParameter("id", id).executeUpdate();

            Query query2 = entityManager.createQuery("delete from ManagerField x where x.fieldId =:id");
            query2.setParameter("id", id).executeUpdate();

            entityManager.remove(field);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Field> findAll() {
        return entityManager.createQuery("select x from Field x").getResultList();
    }

}
