package com.cts.microservices.buyerservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;

import com.cts.microservices.buyerservice.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor (staticName = "build")
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String name;

	@Column
	private String shortDesc;

	@Column
	private String detailedDesc;

	@Column
	private ProductCategory category;

	@Column
	private int startingPrice;

	@Column
	@Future
	private Date bidEndDate;

}
