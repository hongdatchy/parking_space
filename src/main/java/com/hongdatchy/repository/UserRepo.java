package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.BookPayload;
import com.hongdatchy.entities.payload.ChangePassForm;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;

import java.util.List;

public interface UserRepo {

    boolean checkEmailExisted(String email);

    User login(LoginForm loginForm);

    boolean register(RegisterForm registerForm);

    User findByEmail(String email);

    User createAndUpdate(User user);

    boolean delete(int id);

    List<User> findAll();

    Contract book(BookPayload bookPayload, User user);

    boolean changePass(ChangePassForm changePassForm, User user);

    boolean verifyAccount(String mail, String code);



}
