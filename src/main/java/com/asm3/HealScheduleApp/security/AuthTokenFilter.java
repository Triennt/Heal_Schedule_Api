package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    /**
     * Phương thức này được gọi khi một yêu cầu HTTP được gửi đến ứng dụng. Nó thực hiện các bước xác thực và ủy quyền bằng cách xử lý token JWT.
     * Nếu token hợp lệ, nó sẽ xác định người dùng từ token và đặt thông tin xác thực vào SecurityContextHolder để ứng dụng có thể biết người dùng đó là ai và có quyền gì.
     * Nếu tài khoản người dùng không bị khóa, nó sẽ đặt thông tin xác thực vào SecurityContextHolder. Nếu tài khoản bị khóa, nó sẽ ghi log lỗi.
     *
     * @param request     HttpServletRequest chứa thông tin về yêu cầu gửi từ người dùng
     * @param response    HttpServletResponse được sử dụng để gửi phản hồi cho người dùng
     * @param filterChain FilterChain được sử dụng để tiếp tục chuỗi các bộ lọc
     * @throws ServletException Ném ra khi có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException      Ném ra khi có lỗi I/O xảy ra trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("request URL: "+request.getRequestURI());
            String jwt = getJwtFromRequest(request);

            // Kiểm tra xem token có hợp lệ không và có được xác thực cho đường dẫn cụ thể hay không
            if (jwt != null && jwtTokenProvider.validateToken(jwt, request.getRequestURI())) {

                // Lấy tên người dùng từ token
                String username = jwtTokenProvider.getUserNameFromJwtToken(jwt, request.getRequestURI());

                // Lấy chi tiết người dùng từ tên người dùng
                UserDetails userDetails = userService.loadUserByUsername(username);

                // Kiểm tra xem tài khoản người dùng có bị khóa không
                if (userDetails.isAccountNonLocked()){

                    // Nếu không bị khóa, đặt thông tin xác thực vào SecurityContextHolder
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.error("Account is locked");
                }

            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        // Tiếp tục chuỗi các bộ lọc
        filterChain.doFilter(request, response);
    }

    /**
     * Phương thức này được sử dụng để trích xuất token JWT từ yêu cầu HTTP.
     *
     * @param request HttpServletRequest chứa thông tin về yêu cầu gửi từ người dùng
     * @return Một chuỗi chứa token JWT nếu tồn tại trong yêu cầu, ngược lại trả về null
     */
    private String getJwtFromRequest(HttpServletRequest request) {

        // Lấy giá trị của header "Authorization" từ yêu cầu
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: "+bearerToken);

        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            // Trả về token JWT bằng cách loại bỏ phần "Bearer " từ giá trị của header
            return bearerToken.substring(7);
        }
        return null;
    }

}
