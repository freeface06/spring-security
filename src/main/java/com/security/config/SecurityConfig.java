package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.security.handler.LoginFailureHandler;
import com.security.handler.LoginSuccessHandler;

@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC관리)
@EnableWebSecurity // 시큐리티 필터로 등록가 등록이 된다.
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable();

		http.headers().frameOptions().sameOrigin();

		http.authorizeRequests().requestMatchers("/**").permitAll().anyRequest().permitAll().and().logout((logout) -> // 로그아웃
		logout.deleteCookies("remove") // 쿠키 초기화
				.invalidateHttpSession(true) // 세션초기화 기본값 true 생략가능
				.logoutSuccessUrl("/auth/login")).formLogin().loginPage("/auth/login") // 로그인이 안됬는데 로그인 권한이 필요한페이지를
																						// 호출했을때 이동할 로그인페이지
				.loginProcessingUrl("/auth/login/api") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.failureHandler(customFailurHandler()) // 로그인 실패 핸들러
				.successHandler(customSuccessHandler()); // 로그인 성공 핸들러

		http.sessionManagement().maximumSessions(1); // 최대세션 사용 갯수(중복 로그인 수)
		return http.build();
	}

	// 해쉬값으로 비밀번호 암호화
	@Bean // IoC가 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	private AuthenticationFailureHandler customFailurHandler() { // 로그인 실패 핸들러
		return new LoginFailureHandler();
	}

	private AuthenticationSuccessHandler customSuccessHandler() { // 로그인 성공 핸들러
		return new LoginSuccessHandler();
	}

}
