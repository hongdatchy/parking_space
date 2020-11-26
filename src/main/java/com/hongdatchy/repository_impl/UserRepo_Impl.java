package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.*;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;
import com.hongdatchy.repository.InvoiceRepo;
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
public class UserRepo_Impl implements UserRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Override
    public boolean login(LoginForm loginForm) {
        Query query = entityManager
                .createQuery("select u from User u where u.phone= :phone and u.password = :password");
        List<User> users = query
                .setParameter("phone", loginForm.getPhone())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        if(users.size() != 0){
            users.get(0).setLastTimeAccess(new Date());
            return true;
        }
        return false;
    }

    @Override
    public boolean register(RegisterForm registerForm) {
        if(!checkPhoneExisted(registerForm.getPhone())){
            User newUser = entityManager.merge(User.builder()
                    .id(null)
                    .phone(registerForm.getPhone())
                    .password(SHA256Service.getSHA256(registerForm.getPassword()))
                    .equipment(registerForm.getEquipment())
                    .idNumber(registerForm.getIdNumber())
                    .address(registerForm.getAddress())
                    .tag(registerForm.getTag())
                    .lastTimeAccess(new Date())
                    .build());
            entityManager.merge(newUser);
            return true;
        }
        return false;
    }

    @Override
    public User findByPhone(String phone) {
        Query query = entityManager
                .createQuery("select u from User u where u.phone= :phone");
        List<User> users = query.setParameter("phone", phone).getResultList();
        if(users.size() == 1){
            return users.get(0);
        }
        return null;
    }





    @Override
    public boolean checkPhoneExisted(String phone){
        Query query = entityManager
                .createQuery("select u from User u where u.phone= :phone");
        List<User> users = query.setParameter("phone", phone).getResultList();

        Query query2 = entityManager
                .createQuery("select m from Manager m where m.phone= :phone");
        List<Manager>managers = query2.setParameter("phone", phone).getResultList();

        Query query3 = entityManager
                .createQuery("select a from Admin a where a.phone= :phone");
        List<Admin>admins = query3.setParameter("phone", phone).getResultList();
        if(users.size() == 0 && managers.size() == 0 && admins.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public User createAndUpdate(User user) {
        user.setPassword(SHA256Service.getSHA256(user.getPassword()));
        if(checkPhoneExisted(user.getPhone())){
            return null;
        }
        return entityManager.merge(user);
    }

    @Override
    public boolean delete(int id) {
        User user = entityManager.find(User.class, id);
        if(user != null){
            Query query = entityManager.createQuery("select x FROM Invoice x where x.userId =:id");
            query.setParameter("id", user.getId());
            List<Invoice> invoices = query.getResultList();
            for (Invoice invoice: invoices) {
                invoiceRepo.delete(invoice.getId());
            }
            entityManager.remove(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select x from User x").getResultList();
    }

}
