package com.cts.microservices.sellerservice.exception;

@SuppressWarnings("serial")
public class CannotDeleteProductException extends Exception {
	public CannotDeleteProductException(String msg) {
		super(msg);
	}
}
