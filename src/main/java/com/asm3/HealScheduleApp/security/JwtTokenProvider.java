package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserService userService;
    private String jwtSecret;
    private long jwtExpirationMs;

    /**
     * Phương thức này được sử dụng để thiết lập giá trị của khóa bí mật (JWT secret) dựa trên đường dẫn (URL) của yêu cầu.
     * Đối với các yêu cầu liên quan đến quên mật khẩu hoặc thay đổi mật khẩu, nó sẽ sử dụng khóa bí mật được đặt trước cho việc đặt lại mật khẩu.
     * Đối với các yêu cầu khác, nó sẽ sử dụng khóa bí mật được đặt trước cho quá trình đăng nhập.
     *
     * @param url Đường dẫn (URL) của yêu cầu
     */
    private void setJwtSecret(String url){
        if (url.equals("/forgotPassword") || url.equals("/changePassword")){
            jwtSecret = jwtResetPasswordSecret;
            jwtExpirationMs = jwtResetPasswordExpirationMs;
        } else {
            jwtSecret = jwtLoginSecret;
            jwtExpirationMs = jwtLoginExpirationMs;
        }
    }

    /**
     * Phương thức này được sử dụng để tạo ra một token JWT cho người dùng và lưu nó trong cơ sở dữ liệu.
     * Token được tạo ra dựa trên thông tin của người dùng và thời gian hết hạn của token được xác định bởi thời gian hết hạn của JWT.
     * Khóa bí mật (JWT secret) được sử dụng để ký và xác minh token JWT.
     *
     * @param user Đối tượng User chứa thông tin của người dùng được sử dụng để tạo token JWT
     * @param url  Đường dẫn (URL) của yêu cầu, được sử dụng để xác định loại token cần tạo (cho việc đăng nhập hoặc đặt lại mật khẩu)
     * @return Một chuỗi chứa token JWT được tạo ra cho người dùng
     */
    public String createToken(User user, String url) {
        // Thiết lập khóa bí mật (JWT secret) dựa trên đường dẫn của yêu cầu
        setJwtSecret(url);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String jwt = Jwts.builder()
                .setSubject(user.getEmail()) //Đặt chủ đề của token là email của người dùng
                .setIssuedAt(now) // Đặt thời gian phát hành của token là thời gian hiện tại
                .setExpiration(expiryDate) // Đặt thời gian hết hạn của token
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Ký token bằng cách sử dụng khóa bí mật
                .compact(); // Kết xuất token thành một chuỗi

        // Lưu token trong trường sessionToken của đối tượng người dùng
        user.setSessionToken(jwt);
        userService.save(user);

        return jwt;
    }

    /**
     * Phương thức này được sử dụng để trích xuất tên người dùng từ token JWT.
     * Khóa bí mật (JWT secret) được sử dụng để xác minh tính hợp lệ của token JWT.
     *
     * @param token Chuỗi chứa token JWT cần trích xuất thông tin người dùng
     * @param url   Đường dẫn (URL) của yêu cầu, được sử dụng để xác định loại token được sử dụng (cho đăng nhập hoặc đặt lại mật khẩu)
     * @return Một chuỗi chứa tên người dùng được trích xuất từ token JWT
     * @throws JwtException Nếu token JWT không hợp lệ
     */
    public String getUserNameFromJwtToken(String token, String url) {

        // Thiết lập khóa bí mật (JWT secret) dựa trên đường dẫn của yêu cầu
        setJwtSecret(url);

        try {

            // Phân tích token JWT để lấy các thông tin trong payload
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Lấy tên người dùng từ trường subject của token
            System.out.println("Username from token: "+claims.getSubject());
            return claims.getSubject();
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    /**
     * Phương thức này được sử dụng để xác minh tính hợp lệ của token JWT.
     * Khóa bí mật (JWT secret) được sử dụng để xác minh tính hợp lệ của token JWT.
     * Nó cũng kiểm tra xem token có tương ứng với người dùng trong cơ sở dữ liệu hay không.
     *
     * @param authToken Chuỗi chứa token JWT cần được xác minh tính hợp lệ
     * @param url       Đường dẫn (URL) của yêu cầu, được sử dụng để xác định loại token được sử dụng (cho đăng nhập hoặc đặt lại mật khẩu)
     * @return true nếu token là hợp lệ và tương ứng với người dùng trong cơ sở dữ liệu, ngược lại trả về false
     */
    public boolean validateToken(String authToken, String url) {

        // Thiết lập khóa bí mật (JWT secret) dựa trên đường dẫn của yêu cầu
        setJwtSecret(url);

        try{

            // Phân tích token JWT để lấy các thông tin trong payload
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();

            // Lấy thông tin người dùng từ cơ sở dữ liệu dựa trên email trong token
            User user = userService.findByEmail(claims.getSubject());

            if(user == null){
                throw new CustomNotFoundException("No found user with email " + claims.getSubject());
            }

            // Kiểm tra xem token có tương ứng với session token của người dùng hay không
            if (user.getSessionToken() != null && user.getSessionToken().equals(authToken))
                return true;
            else
                throw new JwtException("The token's session has expired");
        } catch (Exception e){
            System.out.println("Token validation failed: "+ e.getMessage());
            return false;
        }
    }
}
