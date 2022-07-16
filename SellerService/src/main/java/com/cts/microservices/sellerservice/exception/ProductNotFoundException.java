package com.cts.microservices.sellerservice.exception;

@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception {
	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
