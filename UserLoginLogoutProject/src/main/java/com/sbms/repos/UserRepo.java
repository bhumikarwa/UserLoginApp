package com.sbms.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbms.entities.User;

import jakarta.transaction.Transactional;


public interface UserRepo extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmailAndPassword(String email, String password);
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByPassword(String password);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(" update User user set user.password=:password,user.is_acc_locked=:isAccountLocked where user.id=:userId ")
	public Integer updatePassword(@Param("userId")Integer userId,@Param("isAccountLocked")Integer isAccountLocked, @Param("password")String password);

}
