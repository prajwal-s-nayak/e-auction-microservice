package com.cts.microservices.sellerservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cts.microservices.sellerservice.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	@NotNull
	@Size(min = 5, max = 30)
	private String name;

	private String shortDesc;

	private String detailedDesc;
	
	private ProductCategory category;

	@Min(value = 0L)
	private int startingPrice;
	
	private String bidEndDate;
}
