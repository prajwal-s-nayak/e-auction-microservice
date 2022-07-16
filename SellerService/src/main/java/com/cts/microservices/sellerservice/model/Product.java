package com.cts.microservices.sellerservice.model;

import java.sql.Date;

import com.cts.microservices.sellerservice.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Product {

	private int id;

	private String name;

	private String shortDesc;

	private String detailedDesc;

	private ProductCategory category;

	private int startingPrice;

	private Date bidEndDate;

}
