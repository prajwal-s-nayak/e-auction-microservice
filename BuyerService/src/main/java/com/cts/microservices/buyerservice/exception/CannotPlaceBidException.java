package com.cts.microservices.buyerservice.exception;

@SuppressWarnings("serial")
public class CannotPlaceBidException extends Exception {
	public CannotPlaceBidException(String msg) {
		super(msg);
	}
}
