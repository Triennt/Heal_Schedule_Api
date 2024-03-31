package com.asm3.HealScheduleApp.security;

import com.asm3.HealScheduleApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserService userService;
	@Autowired
	private UnauthorizedHandler unauthorizedHandler;

	// Cấu hình bộ lọc bảo mật
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(configurer ->
				configurer
						.requestMatchers(HttpMethod.GET, "/").permitAll()
						.requestMatchers(HttpMethod.POST, "/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/forgotPassword").permitAll()
						.requestMatchers(HttpMethod.PUT, "/changePassword").hasAnyRole("USER","DOCTOR","ADMIN")
						.requestMatchers(HttpMethod.GET, "/home/**").permitAll()
						.requestMatchers(HttpMethod.GET,"/user/**").hasRole("USER")
						.requestMatchers(HttpMethod.POST,"/user/**").hasRole("USER")
						.requestMatchers(HttpMethod.GET, "/doctor/**").hasRole("DOCTOR")
						.requestMatchers(HttpMethod.PUT,"/doctor/**").hasRole("DOCTOR")
						.requestMatchers("/admin/**").hasRole("ADMIN")

		);

		// Cung cấp AuthenticationProvider và thêm bộ lọc JWT trước UsernamePasswordAuthenticationFilter
		http.authenticationProvider(authenticationProvider(userService));
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		// Xử lý các ngoại lệ không được xác thực
		http.exceptionHandling((exception) ->
				exception.authenticationEntryPoint(unauthorizedHandler));

		// Vô hiệu hóa CSRF vì REST API thường không cần nó
		http.csrf(csrf -> csrf.disable());

		return http.build();
	}

	// Bean để tạo AuthTokenFilter để xác thực token JWT
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	// Bean để tạo AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	// Bean để tạo BCryptPasswordEncoder để mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Bean để cung cấp DaoAuthenticationProvider để xác thực người dùng từ cơ sở dữ liệu
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserService userService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	  
}






