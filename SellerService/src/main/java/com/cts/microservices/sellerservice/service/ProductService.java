package com.cts.microservices.sellerservice.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.microservices.sellerservice.entity.Product;
import com.cts.microservices.sellerservice.exception.ProductNotFoundException;
import com.cts.microservices.sellerservice.model.ProductRequest;
import com.cts.microservices.sellerservice.repositories.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;
	
	public Product getProductById(Integer id) throws ProductNotFoundException{
		Optional<Product> product = repo.findById(id);
		if(product.isEmpty())
			return null;
		else
			return product.get();
	}
	
	public Product addProduct(ProductRequest newProductRequest) throws ConstraintViolationException {		
		Date bidEndDate = convertStringToTimestamp(newProductRequest.getBidEndDate());
		Product newProduct = Product.build(0, newProductRequest.getName(), newProductRequest.getShortDesc(), newProductRequest.getDetailedDesc(), newProductRequest.getCategory(), newProductRequest.getStartingPrice(),bidEndDate);
		Product product = repo.save(newProduct);
		return product;
	}
	
	public Integer deleteProduct(Product newProduct) {
		repo.delete(newProduct);
		return newProduct.getId();
	}
	
	public static Date convertStringToTimestamp(String strDate) {      
	    String format = "dd/MM/yyyy";
	    if(strDate.contains("-")) {
	        format = "yyyy-MM-dd";
	    }
	    DateTimeFormatter DATE_TME_FORMATTER =  
	        new DateTimeFormatterBuilder().appendPattern(format)
	        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
	        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
	        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
	        .toFormatter();
	    LocalDate dt = LocalDate.parse(strDate.substring(0, 10), DATE_TME_FORMATTER);
	  //  Timestamp timestamp = Timestamp.valueOf(dt.atStartOfDay());
	    Date date = Date.valueOf(dt);
	    return date;
	}
	
	public List<Integer> getAllProductIds(){
		
		List<Integer> idList = new ArrayList<>();
		List<Product> productList = repo.findAll();
		for(Product p : productList)
			idList.add(p.getId());
		return idList;
		
	}
}
