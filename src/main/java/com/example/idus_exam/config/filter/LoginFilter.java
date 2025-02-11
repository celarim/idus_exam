package com.example.idus_exam.config.filter;

import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import com.example.idus_exam.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter() {
    }
    public LoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    //jackson으로 json을 풀어서 전달
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            UsernamePasswordAuthenticationToken authRequest;

            try {
                UserDto.LoginRequest dto = new ObjectMapper().readValue(request.getInputStream(), UserDto.LoginRequest.class);
                authRequest = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    //jwt token 생성 및 쿠키 세팅
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String jwtToken = JwtUtil.generateToken(user);

        ResponseCookie cookie = ResponseCookie
                .from("ATOKEN", jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofHours(1))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        //super.successfulAuthentication(request, response, chain, authResult);
    }

}
