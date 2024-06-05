package com.security.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errorCode;
		if (exception instanceof BadCredentialsException) {
			errorCode = "아이디 또는 비밀번호가 일치하지 않습니다.";
			errorCode = "4001";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorCode = "존재하지 않는 아이디입니다.";
			errorCode = "4002";
		} else {
			errorCode = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
			errorCode = "4003";
		}

		errorCode = URLEncoder.encode(errorCode, "UTF-8");
		
		System.out.println(errorCode);
		
		response.sendRedirect("/auth/login?code=" + errorCode);
	}
}