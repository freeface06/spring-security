package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.vo.UserVo;


@Service // 빈등록
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private com.security.repository.UserRepository UserRepository;

	// 스프링이 로그인 요청을 가로챌 떄, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 함.
	// username이 DB에 있는지만 확인해주면 됨

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVo userEntity = UserRepository.findByUsername(username);
		System.out.println(username);
		if (userEntity != null) {
			return new PrincipalDetail(userEntity);
		}
		return null;
	}
}