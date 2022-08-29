package com.cts.microservices.sellerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor (staticName = "build")
@NoArgsConstructor
public class Auction {

	private int id;

	private int userId;

	private int productId;

	private int bidAmount;
	
	
}
