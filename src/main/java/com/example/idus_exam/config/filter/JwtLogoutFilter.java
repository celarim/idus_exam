package com.example.idus_exam.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtLogoutFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().equals("/logout")) {
            ResponseCookie cookie = ResponseCookie
                    .from("ATOKEN", null)
                    .maxAge(0)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
