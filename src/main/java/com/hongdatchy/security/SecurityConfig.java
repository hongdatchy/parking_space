package com.hongdatchy.security;


import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepo userRepository;
    private final JWTService jwtService;
    private BlackListRepo blackListRepo;

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilter() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilter(userRepository,jwtService, blackListRepo));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        //stateless
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
