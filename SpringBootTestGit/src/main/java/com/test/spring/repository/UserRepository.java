package com.test.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.spring.pojo.User;

import jakarta.validation.constraints.Email;

public interface UserRepository extends JpaRepository<User, Email> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> getUserByemail(@Param("email") String email);

	@Modifying
	@Query("Delete FROM User u WHERE u.email = :email")
	String deleteUserByemail(@Param("email") String email);

	Optional<User> findUserByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	User getFindUserByEmail(@Param(value = "email") String email);
}