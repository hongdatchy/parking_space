package com.hongdatchy.service_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.social.GooglePojo;
import com.hongdatchy.repository.UserRepo;

import com.hongdatchy.service.SocialService;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleService implements SocialService {

    @Autowired
    private UserRepo userRepo;

    @Value("${google.appId}")
    private String appId;
    @Value("${google.appSecret}")
    private String appSecret;
    @Value("${google.redirect}")
    private String redirect;
    @Value("${google.user_info}")
    private String linkUser;
    @Value("${google.scope}")
    private String scope;

    @Override
    public String createAuthorizationURL() {
        GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId, appSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(redirect);
        params.setScope(scope);
        return oauthOperations.buildAuthorizeUrl(params);
    }

    @Override
    public String createAccessToken(String code) {
        GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId, appSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, redirect, null);
        return accessGrant.getAccessToken();
    }

    @Override
    public User getUser(String token) throws IOException {
        String link = linkUser + token; // tạo link api
        String response = Request.Get(link).execute().returnContent().asString(); // call api
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo pojo = mapper.readValue(response, GooglePojo.class); // map với entity
        String email = pojo.getEmail();
        User user = userRepo.findByEmail(email);
        if (user != null) return user;
        User newUser = User.builder()
                .id(null)
                .lastTimeAccess(null)
                .password("")
                .equipment("")
                .idNumber(0)
                .address("")
                .email(email)
                .build();
        return userRepo.createAndUpdate(newUser);
    }
}
