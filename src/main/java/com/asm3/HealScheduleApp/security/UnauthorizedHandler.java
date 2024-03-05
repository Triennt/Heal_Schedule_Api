package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * Phương thức này được gọi khi một yêu cầu không được xác thực.
     *
     * @param request          HttpServletRequest: Đối tượng chứa thông tin về yêu cầu HTTP.
     * @param response         HttpServletResponse: Đối tượng chứa thông tin để tạo phản hồi HTTP.
     * @param authException    AuthenticationException: Ngoại lệ xảy ra khi xác thực thất bại.
     * @throws IOException      Ném ra khi có lỗi xảy ra trong quá trình xử lý luồng dữ liệu vào hoặc ra.
     * @throws ServletException Ném ra khi có lỗi trong quá trình xử lý yêu cầu.
     */
    @Override
    @ResponseBody
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Thiết lập loại nội dung phản hồi là JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Thiết lập mã trạng thái phản hồi là 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized error", authException.getMessage());

        // Sử dụng ObjectMapper để chuyển đổi đối tượng ErrorResponse thành dữ liệu JSON và ghi vào luồng đầu ra của phản hồi
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), error);
    }
}
