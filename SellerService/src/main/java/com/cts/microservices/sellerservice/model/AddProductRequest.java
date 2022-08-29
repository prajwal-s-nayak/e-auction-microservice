package com.cts.microservices.sellerservice.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
	
	@NotNull
	@Valid
	private UserRequest userRequest;
	@NotNull
	@Valid
	private ProductRequest productRequest;
}
