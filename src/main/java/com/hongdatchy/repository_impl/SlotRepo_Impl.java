package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class SlotRepo_Impl implements SlotRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Slot createAndUpdate(Slot slot) {
        return entityManager.merge(slot);
    }

    @Override
    public boolean delete(int id) {
        Slot slot = entityManager.find(Slot.class, id);
        if(slot != null){
            Query query = entityManager.createQuery("delete from Detector x where x.slotId =:id");
            query.setParameter("id", id).executeUpdate();

            Query query2 = entityManager.createQuery("delete from Contract x where x.slotId =:id");
            query2.setParameter("id", id).executeUpdate();

            entityManager.remove(slot);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Slot> findAll() {
        return entityManager.createQuery("select s from Slot s").getResultList();
    }

    @Override
    public Slot findById(int id){
        return entityManager.find(Slot.class, id);
    }

    @Override
    public Integer getIdSlotFree(int fieldId) {
        List<Slot> slots= entityManager.
                createQuery("select x from Slot x where x.status= false and x.fieldId =:fieldId")
                .setParameter("fieldId", fieldId)
                .getResultList();
        return slots.size()==0 ? null : slots.get(0).getId();
    }
}
