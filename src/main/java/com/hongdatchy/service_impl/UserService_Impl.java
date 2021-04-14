package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.json.FieldJson;
import com.hongdatchy.entities.payload.*;
import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.service.FieldService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService_Impl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Autowired
    FieldService fieldService;

    @Override
    public boolean login(LoginForm loginForm) {
        return userRepo.login(loginForm);
    }

    @Override
    public boolean register(RegisterForm registerForm) {
        return userRepo.register(registerForm);
    }

    @Override
    public User createAndUpdate(UserPayload userPayload) {
        return userRepo.createAndUpdate(payload2Data(userPayload));
    }

    @Override
    public boolean delete(int id) {
        return userRepo.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public boolean book(BookPayload bookPayload, String email) {
        User user = userRepo.findByEmail(email);
        FieldJson fieldJson = fieldService.data2Json(new Field(bookPayload.getFieldId(),"","","",""));
        if(fieldJson.getTotalSlot() < fieldJson.getBusySlot() + fieldJson.getTotalBook()){
            return false;
        }
        return user != null && userRepo.book(bookPayload, user);
    }

    @Override
    public boolean changePass(ChangePassForm changePassForm, String phone) {
        User user = userRepo.findByEmail(phone);
        return user != null && userRepo.changePass(changePassForm, user);
    }

    public User payload2Data(UserPayload userPayload){
        return User.builder()
                .id(userPayload.getId())
                .address(userPayload.getAddress())
                .idNumber(userPayload.getIdNumber())
                .equipment(userPayload.getEquipment())
                .email(userPayload.getEmail())
                .password(userPayload.getPassword())
                .lastTimeAccess(null)
                .build();
    }

    @Override
    public boolean verifyAccount(String mail, String code) {
        return userRepo.verifyAccount(mail, code);
    }
}
