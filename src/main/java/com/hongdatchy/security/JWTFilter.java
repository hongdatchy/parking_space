package com.hongdatchy.security;

import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@AllArgsConstructor
public class JWTFilter implements Filter {
    private UserRepo userRepository;
    private JWTService jwtService;
    private BlackListRepo blackListRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse rs = (HttpServletResponse) servletResponse;
        String token = servletRequest.getParameter("token");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String tokenManager = request.getHeader("tokenManager");
        System.out.println(tokenManager);
        if (token != null) {
            String phone = jwtService.decode(token);
            if (phone != null && userRepository.findByPhone(phone) != null && blackListRepo.findByToken(token).size() ==0)
                filterChain.doFilter(servletRequest, servletResponse);
            else rs.setStatus(401);
        } else rs.setStatus(401);
    }
}
