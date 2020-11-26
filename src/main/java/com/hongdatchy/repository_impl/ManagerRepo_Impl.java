package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.security.SHA256Service;
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
public class ManagerRepo_Impl implements ManagerRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepo userRepo;

    @Override
    public Manager createAndUpdate(Manager manager) {
        if(userRepo.checkPhoneExisted(manager.getPhone())){
            return null;
        }
        manager.setPass(SHA256Service.getSHA256(manager.getPass()));
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

    @Override
    public boolean login(LoginForm loginForm) {
        Query query = entityManager
                .createQuery("select m from Manager m where m.phone= :phone and m.pass = :password");
        List<Manager> managers = query
                .setParameter("phone", loginForm.getPhone())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        if(managers.size() != 0){
            managers.get(0).setLastTimeAccess(new Date());
            return true;
        }
        return false;
    }

    @Override
    public Manager findByPhone(String phone) {
        Query query = entityManager
                .createQuery("select m from Manager m where m.phone= :phone");
        List<Manager> managers = query.setParameter("phone", phone).getResultList();
        if(managers.size() == 1){
            return managers.get(0);
        }
        return null;
    }
}
