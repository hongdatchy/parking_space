package com.hongdatchy.repository;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.BookPayload;
import com.hongdatchy.entities.payload.ChangePassForm;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;

import java.util.List;

public interface UserRepo {

    boolean checkPhoneExisted(String phone);

    boolean login(LoginForm loginForm);

    boolean register(RegisterForm registerForm);

    User findByPhone(String phone);

    User createAndUpdate(User user);

    boolean delete(int id);

    List<User> findAll();

    boolean book(List<BookPayload> bookPayloads, User user);

    boolean changePass(ChangePassForm changePassForm, User user);
}
