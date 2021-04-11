package com.hongdatchy.controller;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service_impl.GoogleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class SocialController {

    private final JWTService jwtService;
    private final GoogleService googleService;

    @GetMapping("login-google")
    public String loginGoogle() {
        return String.format("redirect:%s", googleService.createAuthorizationURL());
    }

    @GetMapping("google-callback")
    public ResponseEntity<Object> googleCallback(@RequestParam(name = "code") String code) throws Exception {
        String accessToken = googleService.createAccessToken(code);
        User user = googleService.getUser(accessToken);
        return ResponseEntity.ok(jwtService.getToken(user.getEmail()));
    }
}
