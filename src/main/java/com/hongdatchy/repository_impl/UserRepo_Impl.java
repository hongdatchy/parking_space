package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.*;
import com.hongdatchy.entities.payload.BookPayload;
import com.hongdatchy.entities.payload.ChangePassForm;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;
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
    InvoiceRepo invoiceRepo;

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    SendMailService sendMailService;

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
        String code = getRandomCode();
        String extension = registerForm.getPhone().substring(registerForm.getPhone().lastIndexOf("@"));
        if(!extension.equals("@gmail.com")){
            return false;
        }
        boolean b = sendMailService.sendMail(registerForm.getPhone()
                        , "welcome to parking space system"
                        , "To verify your account, please enter this code to register page: " + code);

        if(b) entityManager.merge(VerifyTable.builder()
                .address(registerForm.getAddress())
                .code(code)
                .equipment(registerForm.getEquipment())
                .idNumber(registerForm.getIdNumber())
                .lastTimeAccess(new Date())
                .mail(registerForm.getPhone())
                .pass(SHA256Service.getSHA256(registerForm.getPassword()))
                .tag(registerForm.getTag())
                .build());
        return b;

//        if(!checkPhoneExisted(registerForm.getPhone())){
//            User newUser = entityManager.merge(User.builder()
//                    .id(null)
//                    .phone(registerForm.getPhone())
//                    .password(SHA256Service.getSHA256(registerForm.getPassword()))
//                    .equipment(registerForm.getEquipment())
//                    .idNumber(registerForm.getIdNumber())
//                    .address(registerForm.getAddress())
//                    .tag(registerForm.getTag())
//                    .lastTimeAccess(new Date())
//                    .build());
//            entityManager.merge(newUser);
//            return true;
//        }

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

    @Override
    public boolean book(List<BookPayload> bookPayloads, User user) {
        Invoice invoice = invoiceRepo.createAndUpdate(
                Invoice.builder()
                        .id(null)
                        .userId(user.getId())
                        .build()
        );

        List<Integer> id =new ArrayList<>();
        for (BookPayload bookPayload: bookPayloads) {
            Contract newContract =contractRepo.createAndUpdate(Contract.builder()
                    .timeInBook(bookPayload.getTimeInBook())
                    .timeOutBook(bookPayload.getTimeOutBook())
                    .timeCarOut(null)
                    .timeCarIn(null)
                    .slotId(bookPayload.getSlotId())
                    .invoiceId(invoice.getId())
                    .id(null)
                    .build());
            if(newContract == null){
                invoiceRepo.delete(invoice.getId());
                for (Integer integer : id) {
                    contractRepo.delete(integer);
                }
                return false;
            }else{
                id.add(newContract.getId());
            }
        }
        return true;
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
    public boolean verifyAccount(String mail, String code) {
        VerifyTable verifyTable = entityManager.find(VerifyTable.class, mail);
        if(verifyTable == null || !verifyTable.getCode().equals(code)){
            return false;
        }
        List<User> oldUser = entityManager.createQuery("select x from User x where x.phone = :mail")
                .setParameter("mail", mail).getResultList();
        if(oldUser.size() == 0){
            entityManager.merge(User.builder()
                    .id(null)
                    .phone(verifyTable.getMail())
                    .password(verifyTable.getPass())
                    .equipment(verifyTable.getEquipment())
                    .idNumber(verifyTable.getIdNumber())
                    .address(verifyTable.getAddress())
                    .tag(verifyTable.getTag())
                    .lastTimeAccess(verifyTable.getLastTimeAccess())
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
