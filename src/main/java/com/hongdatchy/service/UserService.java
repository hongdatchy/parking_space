package com.hongdatchy.service;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.BookPayload;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;
import com.hongdatchy.entities.payload.UserPayload;

import java.util.List;

public interface UserService {

    boolean login(LoginForm loginForm);

    boolean register(RegisterForm registerForm);

    User createAndUpdate(UserPayload userPayload);

    boolean delete(int id);

    List<User> findAll();

    boolean book(List<BookPayload> bookPayloads, String phone);
}
