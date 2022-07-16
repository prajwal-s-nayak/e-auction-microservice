package com.cts.microservices.sellerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class AuctionResponse {

	private int bidAmount;
	private String buyerName;
	private String buyerEmail;
	private String buyerPhoneNo;
}
