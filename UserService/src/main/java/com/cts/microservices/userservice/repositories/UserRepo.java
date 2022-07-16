package com.cts.microservices.userservice.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.microservices.userservice.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	public Optional<User> findById(Integer id);
	public Optional<User> findByEmail(String email);
}
