package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${HealScheduleApp.jwtLoginSecret}")
    private String jwtLoginSecret;
    @Value("${HealScheduleApp.jwtLoginExpirationMs}")
    private long jwtLoginExpirationMs;
    @Value("${HealScheduleApp.jwtResetPasswordSecret}")
    private String jwtResetPasswordSecret;
    @Value("${HealScheduleApp.jwtResetPasswordExpirationMs}")
    private long jwtResetPasswordExpirationMs;

    private String jwtSecret;
    private long jwtExpirationMs;
    private void setJwtSecret(String url){
        if (url.equals("/forgotPassword") || url.equals("/changePassword")){
            jwtSecret = jwtResetPasswordSecret;
            jwtExpirationMs = jwtResetPasswordExpirationMs;
        } else {
            jwtSecret = jwtLoginSecret;
            jwtExpirationMs = jwtLoginExpirationMs;
        }
        System.out.println("jwtSecret: "+jwtSecret);
    }

    /**
     * Tạo token khi đăng nhập vào hệ thống
     * @param user
     * @return
     */
    public String createToken(User user, String url) {
        setJwtSecret(url);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    //     Lấy thông tin user từ jwt
    public String getUserNameFromJwtToken(String token, String url) {
        setJwtSecret(url);
        System.out.println("token: "+token);
        try {
            // Phân tích token dựa trên khóa bí mật đã được lưu trữ an toàn
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret) // Sử dụng khóa bí mật đã được tạo
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Lấy tên người dùng từ trường subject của token
            System.out.println("Username from token: "+claims.getSubject());
            return claims.getSubject();
        } catch (JwtException e) {
            // Xử lý lỗi nếu token không hợp lệ
            throw new JwtException("Invalid JWT token");
        }
    }
    public boolean validateToken(String authToken, String url) {
        setJwtSecret(url);
        try{
            Jwts.parser().setSigningKey(jwtSecret).build().parse(authToken);
            return true;
        } catch (JwtException jex){
            System.out.println("Token validation failed: "+ jex.getMessage());
            return false;
        }
    }
}
