package com.cts.microservices.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.microservices.userservice.entity.User;
import com.cts.microservices.userservice.model.UserRequest;
import com.cts.microservices.userservice.repositories.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public User getUser(Integer id) {
		Optional<User> user = userRepo.findById(id);
		return user.get();
	}

	public User addUser(UserRequest newUserRequest) {
		User newUser = User.build(0, newUserRequest.getFirstname(), newUserRequest.getLastname(), newUserRequest.getAddress(), newUserRequest.getCity(), newUserRequest.getState(), newUserRequest.getPin(), newUserRequest.getPhone(), newUserRequest.getEmail());
		User user = userRepo.save(newUser);
		return user;
	}
	
	public User getUserByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isEmpty())
			return null;
		else 
		return user.get();	
	}
}
