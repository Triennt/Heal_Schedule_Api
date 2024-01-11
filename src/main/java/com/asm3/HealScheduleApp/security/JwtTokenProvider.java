package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "CreateacollectionforyourrequestsThesigningkeyssizeis48bitswhichisnotsecureenoughfortheHS512algorithm";
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
    // Tạo ra jwt từ thông tin user
    public String generateToken(User user ) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

//     Lấy thông tin user từ jwt
    public String getUserNameFromJwtToken(String token) {
        System.out.println("token: "+token);
        try {
            // Phân tích token dựa trên khóa bí mật đã được lưu trữ an toàn
            Claims claims = Jwts.parser()
                    .setSigningKey(key()) // Sử dụng khóa bí mật đã được tạo
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Lấy tên người dùng từ trường subject của token
            System.out.println("Username form token: "+claims.getSubject());
            return claims.getSubject();
        } catch (JwtException e) {
            // Xử lý lỗi nếu token không hợp lệ
//            throw new InvalidJwtAuthenticationException("Invalid JWT token");
            throw new JwtException("Invalid JWT token");
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException ex) {
//            log.error("Invalid JWT token");
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
//            log.error("Expired JWT token");
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
//            log.error("Unsupported JWT token");
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
//            log.error("JWT claims string is empty.");
            System.out.println("JWT claims string is empty.");
        } catch (SignatureException ex){
            System.out.println("JWT signature does not match locally computed signature.");
        }
        return false;
    }
}
