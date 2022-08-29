package com.cts.microservices.buyerservice.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column
	private String address;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private int pin;

	@Column
	private String phone; // string else we would have to use long. Lets validate if its a number from UI.

	@Column
	private String email;


}
