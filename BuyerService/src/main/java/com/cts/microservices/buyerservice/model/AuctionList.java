package com.cts.microservices.buyerservice.model;

import java.util.List;

import com.cts.microservices.buyerservice.entity.Auction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionList {
	    private List<Auction> auctionList;
}
