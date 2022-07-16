package com.cts.microservices.buyerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor (staticName = "build")
@NoArgsConstructor
public class PlaceBidRequestForKarfa {

	private UserRequest userRequest;
	
	private int productId;
	
	private int bidAmount;
	
	private boolean newUser;
	
	private int userId;
}
