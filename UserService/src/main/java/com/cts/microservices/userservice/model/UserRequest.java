package com.cts.microservices.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
 
public class UserRequest {

	private String firstname;

	private String lastname;

	private String address;

	private String city;

	private String state;

	private int pin;
	
	private String phone; //string else we would have to use long. Lets validate if its a number from UI.

	private String email;

}
