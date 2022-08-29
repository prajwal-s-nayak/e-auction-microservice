package com.cts.microservices.sellerservice.model;

import java.util.List;

import com.cts.microservices.sellerservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowBidResponse {
	
	private Product product;
	
	private List<AuctionResponse> auctionData;
}
