package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.dto.ChangePasswordRequest;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.CreateUserResponse;
import com.asm3.HealScheduleApp.response.Response;
import com.asm3.HealScheduleApp.security.JwtTokenProvider;
import com.asm3.HealScheduleApp.service.RoleService;
import com.asm3.HealScheduleApp.service.UserService;
import com.asm3.HealScheduleApp.utils.MyUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegistrationRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MyUtils myUtils;

    /**
     * Phương thức @InitBinder được sử dụng để thiết lập các bộ biên dịch dữ liệu trước khi dữ liệu được ràng buộc vào một đối tượng.
     * Trong trường hợp này, nó được sử dụng để cắt bỏ khoảng trắng từ các chuỗi đầu vào để tránh các vấn đề không mong muốn khi xử lý dữ liệu.
     *
     * @param dataBinder   Đối tượng WebDataBinder được sử dụng để ràng buộc dữ liệu từ yêu cầu HTTP vào các đối tượng Java.
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /**
     * Phương thức POST được sử dụng để đăng ký một người dùng mới.
     *
     * @param user           Đối tượng chứa thông tin người dùng cần đăng ký
     * @param bindingResult  Đối tượng BindingResult để kiểm tra lỗi hợp lệ của dữ liệu đầu vào
     * @return một đối tượng ResponseEntity chứa kết quả của việc đăng ký người dùng, bao gồm thông báo thành công hoặc các thông báo lỗi nếu có
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Registration failed.",
                    myUtils.getDescriptionErrors(bindingResult));

            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        User existing = userService.findByEmail(user.getEmail());
        if (existing != null){
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Registration failed.", "Email already exists");
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        User userCreated = userService.create(user);

        CreateUserResponse success = new CreateUserResponse(HttpStatus.CREATED.value(), "Sign Up Success.", userCreated);
        return new ResponseEntity<CreateUserResponse>(success, HttpStatus.CREATED);
    }

    /**
     * Phương thức GET được sử dụng để gửi email xác nhận cho việc quên mật khẩu.
     *
     * @param email   Địa chỉ email của người dùng cần đặt lại mật khẩu
     * @return một đối tượng ResponseEntity chứa kết quả của việc gửi email xác nhận, bao gồm thông báo gửi email thành công hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy người dùng với địa chỉ email đã cung cấp
     */
    @GetMapping("/forgotPassword")
    public ResponseEntity<Response> sendEmail(@RequestParam("email") String email){

        User tempUser = userService.findByEmail(email);

        if (tempUser == null){
            throw new CustomNotFoundException("Email does not exist.");
        }

        String jwt = jwtTokenProvider.createToken(tempUser, "/forgotPassword");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please verify your email");
        message.setText("Username: " +email+ " is changing the password. Use the token below for authentication when you change your password: " + jwt);
        mailSender.send(message);

        Response response = new Response(HttpStatus.OK.value(),"Email sent successfully: "+email);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    /**
     * Phương thức PUT được sử dụng để thay đổi mật khẩu của người dùng.
     *
     * @param changePasswordRequest   Đối tượng chứa thông tin yêu cầu thay đổi mật khẩu của người dùng
     * @param bindingResult           Đối tượng BindingResult để kiểm tra lỗi hợp lệ của dữ liệu đầu vào
     * @return một đối tượng ResponseEntity chứa kết quả của việc thay đổi mật khẩu, bao gồm thông báo thành công hoặc thông báo lỗi nếu có
     */
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Change password failed.",
                    myUtils.getDescriptionErrors(bindingResult));

            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        // Lấy thông tin xác thực của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.changePassword(username, changePasswordRequest);

        Response response = new Response(HttpStatus.OK.value(), "Account "+username+" has successfully changed its password.");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
