package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.security.vo.UserVo;

public interface UserRepository extends JpaRepository<UserVo, Integer>, JpaSpecificationExecutor<UserVo> {

	@Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
	public UserVo findByUsername(String username);
	
}
