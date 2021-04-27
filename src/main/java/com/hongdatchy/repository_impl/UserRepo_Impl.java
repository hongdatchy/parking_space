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
import java.sql.Timestamp;
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
                .createQuery("select u from User u where u.email= :phone and u.password = :password")
                .setParameter("phone", loginForm.getEmail())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        if(users.size() != 0){
            users.get(0).setLastTimeAccess(new Timestamp(new Date().getTime()));
            return true;
        }
        return false;
    }

    @Override
    public boolean register(RegisterForm registerForm) {

        String code = getRandomCode();  
        boolean b = sendMailService.sendMail(registerForm.getEmail()
                , "welcome to parking space system"
                , "To verify your account, please enter this code to register page: " + code);

        if(b) entityManager.merge(VerifyTable.builder()
                .address(registerForm.getAddress())
                .code(code)
                .equipment(registerForm.getEquipment())
                .idNumber(registerForm.getIdNumber())
                .lastTimeAccess(null)
                .email(registerForm.getEmail())
                .pass(SHA256Service.getSHA256(registerForm.getPassword()))
                .birth(registerForm.getBirth())
                .image(registerForm.getImage())
                .phone(registerForm.getPhone())
                .sex(registerForm.getSex())
                .build());
        return b;
    }

    @Override
    public User findByEmail(String phone) {
        Query query = entityManager
                .createQuery("select u from User u where u.email= :phone");
        List<User> users = query.setParameter("phone", phone).getResultList();
        if(users.size() == 1){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean checkEmailExisted(String email){
        Query query = entityManager
                .createQuery("select u from User u where u.email= :email");
        List<User> users = query.setParameter("email", email).getResultList();

        Query query2 = entityManager
                .createQuery("select m from Manager m where m.email= :email");
        List<Manager>managers = query2.setParameter("email", email).getResultList();

        Query query3 = entityManager
                .createQuery("select a from Admin a where a.email= :email");
        List<Admin>admins = query3.setParameter("email", email).getResultList();
        if(users.size() == 0 && managers.size() == 0 && admins.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public User createAndUpdate(User user) {
        user.setPassword(SHA256Service.getSHA256(user.getPassword()));
        if(checkEmailExisted(user.getEmail())){
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
                .id(-1)
                .timeInBook(bookPayload.getTimeInBook())
                .timeOutBook(bookPayload.getTimeOutBook())
                .carNumber(bookPayload.getCarNumber())
                .dtCreate(new Timestamp(new Date().getTime()))
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

    @Override
    public boolean verifyAccount(String email, String code) {
        List<VerifyTable> verifyTables = entityManager.createQuery("select x from VerifyTable x where x.email = : email")
                .setParameter("email", email).getResultList();
        if(verifyTables.size() == 0 || !verifyTables.get(verifyTables.size() -1).getCode().equals(code)){
            return false;
        }

        if(!checkEmailExisted(email)){
            entityManager.merge(User.builder()
                    .id(null)
                    .email(verifyTables.get(verifyTables.size() -1).getEmail())
                    .password(verifyTables.get(verifyTables.size() -1).getPass())
                    .equipment(verifyTables.get(verifyTables.size() -1).getEquipment())
                    .idNumber(verifyTables.get(verifyTables.size() -1).getIdNumber())
                    .address(verifyTables.get(verifyTables.size() -1).getAddress())
                    .lastTimeAccess(verifyTables.get(verifyTables.size() -1).getLastTimeAccess())
                    .phone(verifyTables.get(verifyTables.size() -1).getPhone())
                    .sex(verifyTables.get(verifyTables.size() -1).getSex())
                    .image(verifyTables.get(verifyTables.size() -1).getImage())
                    .birth(verifyTables.get(verifyTables.size() -1).getBirth())
                    .build());
        }
        return true;
    }
    public String getRandomCode(){
        String rs="";
        for (int i=0; i< 6; i++){
            rs += String.valueOf((int) (Math.random() * 10));
        }
        return rs;
    }

}
