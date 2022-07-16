package com.cts.microservices.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservices.userservice.entity.User;
import com.cts.microservices.userservice.model.UserRequest;
import com.cts.microservices.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping(path="/id/{id}")
	public ResponseEntity<User> getUsersByid(@PathVariable Integer id){		
		User user = userService.getUser(id);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping(path="/email/{email}")
	public ResponseEntity<User> getUsersByEmail(@PathVariable String email){		
		User user = userService.getUserByEmail(email);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@PostMapping(path="/add")
	public ResponseEntity<User> addUser(@RequestBody UserRequest newUser){		
		User user = userService.addUser(newUser);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
}
