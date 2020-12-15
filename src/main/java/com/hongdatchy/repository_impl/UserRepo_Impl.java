package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.*;
import com.hongdatchy.entities.payload.*;
import com.hongdatchy.repository.*;
import com.hongdatchy.security.SHA256Service;
import com.hongdatchy.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class UserRepo_Impl implements UserRepo {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    SlotRepo slotRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    SendMailService sendMailService;

    @Override
    public boolean login(LoginForm loginForm) {
        List<User> users = entityManager
                .createQuery("select u from User u where u.phone= :phone and u.password = :password")
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
            entityManager.merge(User.builder()
                    .id(null)
                    .phone(registerForm.getPhone())
                    .password(SHA256Service.getSHA256(registerForm.getPassword()))
                    .equipment(registerForm.getEquipment())
                    .idNumber(registerForm.getIdNumber())
                    .address(registerForm.getAddress())
                    .lastTimeAccess(new Date())
                    .build());
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
            entityManager.createQuery("select x from Contract x where x.userId =:id")
                    .setParameter("id", user.getId());
            entityManager.createQuery("select x from Tag x where x.userId =:id")
                    .setParameter("id", user.getId());
            entityManager.remove(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select x from User x").getResultList();
    }

    @Override
    public boolean book(BookPayload bookPayload, User user) {
        Contract contract = contractRepo.createAndUpdate(Contract.builder()
                .fieldId(bookPayload.getFieldId())
                .id(null)
                .timeInBook(bookPayload.getTimeInBook())
                .timeOutBook(bookPayload.getTimeOutBook())
                .timeCarIn(null)
                .timeCarOut(null)
                .userId(user.getId())
                .build());
        return contract != null;
    }

    @Override
    public boolean changePass(ChangePassForm changePassForm, User user) {
        if(!changePassForm.getPassword().equals(changePassForm.getRePassword())
        || !SHA256Service.getSHA256(changePassForm.getOldPassword()).equals(user.getPassword())){
            return false;
        }
        user.setPassword(SHA256Service.getSHA256(changePassForm.getPassword()));
        entityManager.merge(user);
        return true;
    }




}
