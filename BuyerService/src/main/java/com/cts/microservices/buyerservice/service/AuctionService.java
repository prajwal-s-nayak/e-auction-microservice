package com.cts.microservices.buyerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.microservices.buyerservice.entity.Auction;
import com.cts.microservices.buyerservice.model.PlaceBidRequestForKarfa;
import com.cts.microservices.buyerservice.model.User;
import com.cts.microservices.buyerservice.repositories.AuctionRepo;

@Service
public class AuctionService {
	
	@Autowired
	private AuctionRepo auctionRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void addAuction(PlaceBidRequestForKarfa placeBidRequest) {
		int userId = placeBidRequest.getUserId();
		if(placeBidRequest.isNewUser()) {
			User user = restTemplate.postForObject("http://USER-SERVICE/user/add", placeBidRequest.getUserRequest(),
					User.class);
			userId=user.getId();
		}
		Auction auction = Auction.build(0, userId, placeBidRequest.getProductId(), placeBidRequest.getBidAmount());
		auctionRepo.save(auction);		
	}
	
	public Auction getAuctionDetail(Integer userId, Integer productId) {
		Auction auction = auctionRepo.findByUserIdAndProductId(userId, productId);
		return auction;
	}
	
	public Auction updateAuction(Auction auction) {
		Auction updatedAuction = auctionRepo.save(auction);
		return updatedAuction;
	}
	
//	public List<Auction> getAuctionDetails(Integer productId) {
//		List<Auction> list = auctionRepo.findByProductId(productId);
//		return list;
//	}
	
	public List<Auction> getAuctionDetailsWithPagination(Integer productId,int offset, int pagesize, String field){
		//Page<Auction> auctions = auctionRepo.findAllByProductId(productId, PageRequest.of(offset, pagesize).withSort(Sort.by(Direction.DESC, field)));
		List<Auction> auctions = auctionRepo.findAllByProductId(productId, PageRequest.of(offset, pagesize).withSort(Sort.by(Direction.DESC, field)));
		return auctions;
	}
}
