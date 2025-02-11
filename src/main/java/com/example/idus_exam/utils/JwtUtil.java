package com.example.idus_exam.utils;

import com.example.idus_exam.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int EXP = 30 * 60 * 1000;

    public static String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("idx", user.getIdx());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return false;
        }
        return true;
    }

    public static User getuser(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return User.builder()
                    .idx(claims.get("idx", Long.class))
                    .email(claims.get("email", String.class))
                    .role(claims.get("role", String.class))
                    .build();
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return null;
        }
    }
}
