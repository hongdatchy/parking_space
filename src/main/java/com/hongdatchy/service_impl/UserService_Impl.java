package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;
import com.hongdatchy.entities.payload.UserPayload;
import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService_Impl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Override
    public boolean login(LoginForm loginForm) {
        return userRepo.login(loginForm);
    }

    @Override
    public boolean register(RegisterForm registerForm) {
        return userRepo.register(registerForm);
    }

    @Override
    public boolean logout(String token) {
        blackListRepo.create(token);
        return true;
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

    public User payload2Data(UserPayload userPayload){
        return User.builder()
                .id(userPayload.getId())
                .address(userPayload.getAddress())
                .tag(userPayload.getTag())
                .idNumber(userPayload.getIdNumber())
                .equipment(userPayload.getEquipment())
                .phone(userPayload.getPhone())
                .password(userPayload.getPassword())
                .lastTimeAccess(null)
                .build();
    }
}
