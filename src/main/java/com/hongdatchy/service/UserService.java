package com.hongdatchy.service;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.*;

import java.util.List;

public interface UserService {

    boolean login(LoginForm loginForm);

    boolean register(RegisterForm registerForm);

    User createAndUpdate(UserPayload userPayload);

    boolean delete(int id);

    List<User> findAll();

    boolean book(BookPayload bookPayload, String email);

    public boolean changePass(ChangePassForm changePassForm, String phone);

    public boolean verifyAccount(String mail, String code);

}
