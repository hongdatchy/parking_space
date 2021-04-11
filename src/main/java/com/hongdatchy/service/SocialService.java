package com.hongdatchy.service;

import com.hongdatchy.entities.data.User;

public interface SocialService {

    String createAuthorizationURL();

    String createAccessToken(String code) throws Exception;

    User getUser(String token) throws Exception;

}
