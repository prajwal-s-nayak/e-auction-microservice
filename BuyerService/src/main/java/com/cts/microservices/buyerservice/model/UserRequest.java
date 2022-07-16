package com.cts.microservices.buyerservice.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
 
public class UserRequest {
	@NotNull
	@Size(min = 5, max = 30)
	private String firstname;

	@NotNull
	@Size(min = 3, max = 25)
	private String lastname;

	private String address;

	private String city;

	private String state;

	private int pin;
	
	@NotNull
	@Digits(fraction = 0,integer = 10)
	private String phone; //string else we would have to use long. Lets validate if its a number from UI.

	@NotNull
	@NotEmpty
	@Email
	private String email;

}
