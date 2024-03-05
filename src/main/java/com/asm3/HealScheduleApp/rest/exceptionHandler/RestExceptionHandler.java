package com.asm3.HealScheduleApp.rest.exceptionHandler;

import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.Response;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

	/**
	 * Phương thức @ExceptionHandler được sử dụng để xử lý các ngoại lệ cụ thể và trả về một ResponseEntity chứa thông báo lỗi tương ứng.
	 * Trong trường hợp này, nó được sử dụng để xử lý ngoại lệ BadCredentialsException, được ném ra khi xác thực người dùng thất bại do email hoặc mật khẩu không hợp lệ.
	 *
	 * @param exc  Đối tượng BadCredentialsException ném ra khi xác thực người dùng thất bại do email hoặc mật khẩu không hợp lệ
	 * @return một đối tượng ResponseEntity chứa thông báo lỗi và mã HTTP tương ứng
	 */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exc){

		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"login unsuccessful.","Invalid email or password.");

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

	}

	/**
	 * Phương thức @ExceptionHandler được sử dụng để xử lý các ngoại lệ cụ thể và trả về một ResponseEntity chứa thông báo lỗi tương ứng.
	 * Trong trường hợp này, nó được sử dụng để xử lý ngoại lệ MailAuthenticationException, được ném ra khi xảy ra lỗi trong quá trình xác thực email.
	 *
	 * @param exc  Đối tượng MailAuthenticationException ném ra khi xảy ra lỗi trong quá trình xác thực email
	 * @return một đối tượng ResponseEntity chứa thông báo lỗi và mã HTTP tương ứng
	 */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(MailAuthenticationException exc){

		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"Email sending failed.",exc.getMessage());

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

	}

	/**
	 * Phương thức @ExceptionHandler được sử dụng để xử lý các ngoại lệ không được xử lý riêng biệt và trả về một ResponseEntity chứa thông báo lỗi tương ứng.
	 * Trong trường hợp này, nó được sử dụng để xử lý mọi loại ngoại lệ chưa được xác định trước, bằng cách sử dụng Exception.class.
	 *
	 * @param exc  Đối tượng Exception ném ra khi có lỗi không được xử lý riêng biệt
	 * @return một đối tượng ResponseEntity chứa thông báo lỗi và mã HTTP tương ứng
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception exc){

		Response error = new Response(HttpStatus.BAD_REQUEST.value(),exc.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
