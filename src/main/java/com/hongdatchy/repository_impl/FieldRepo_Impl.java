package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.data.ManagerField;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class FieldRepo_Impl implements FieldRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SlotRepo slotRepo;

    @Override
    public Field createAndUpdate(Field field) {
        return entityManager.merge(field);
    }

    @Override
    public boolean delete(int id) {
        Field field = entityManager.find(Field.class, id);
        if(field != null){
            entityManager.createQuery("delete from FieldGateway x where x.fieldId =:id")
                    .setParameter("id", id).executeUpdate();

            entityManager.createQuery("delete from ManagerField x where x.fieldId =:id")
                    .setParameter("id", id).executeUpdate();

            List<Slot> slots = entityManager.createQuery("select x from Slot x where x.fieldId =:id")
                    .setParameter("id", id).getResultList();
            for(Slot slot : slots) slotRepo.delete(slot.getId());
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

    @Override
    public List<Field> managerFind(Manager manager) {
        List <ManagerField> ManagerFields = entityManager
                .createQuery("select x from ManagerField x where x.managerId =:id")
                .setParameter("id", manager.getId()).getResultList();
        List<Field> fields = new ArrayList<>();
        for (ManagerField managerField: ManagerFields) {
            Field field = (Field) entityManager.createQuery("select x from Field x where x.id =:id")
                    .setParameter("id", managerField.getFieldId()).getResultList().get(0);
            fields.add(field);
        }
        return fields;
    }

    @Override
    public Field managerUpdate(Field field, Manager manager) {
        if(field == null || entityManager.find(Field.class, field.getId()) == null){
            return null;
        }
        return check(field, manager) ? entityManager.merge(field) : null;
    }

    @Override
    public boolean managerDelete(int id, Manager manager) {
        Field field = entityManager.find(Field.class, id);
        if (field == null) {
            return false;
        }else if(check(field, manager)){
            entityManager.remove(field);
            return true;
        }
        return false;
    }

    boolean check(Field field, Manager manager){
        List<Field> fields = managerFind(manager);
        for (Field f: fields){
            if(f.getId() == field.getId()) return true;
        }
        return false;
    }

}
