package com.cts.microservices.buyerservice.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceBidRequest {

	@NotNull
	@Valid
	private UserRequest userRequest;
	
	@NotNull
	private int productId;
	
	@NotNull
	@Min(value = 0L)
	private int bidAmount;
}
