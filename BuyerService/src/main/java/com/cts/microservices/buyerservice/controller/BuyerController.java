package com.cts.microservices.buyerservice.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.microservices.buyerservice.entity.Auction;
import com.cts.microservices.buyerservice.exception.CannotPlaceBidException;
import com.cts.microservices.buyerservice.kafka.AuctionProducer;
import com.cts.microservices.buyerservice.model.AuctionList;
import com.cts.microservices.buyerservice.model.PlaceBidRequest;
import com.cts.microservices.buyerservice.model.PlaceBidRequestForKarfa;
import com.cts.microservices.buyerservice.model.Product;
import com.cts.microservices.buyerservice.model.User;
import com.cts.microservices.buyerservice.service.AuctionService;

@RestController
@RequestMapping("buyer")
public class BuyerController {
	
	@Autowired
	private AuctionProducer auctionProducer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AuctionService auctionService;
	
	public BuyerController(AuctionProducer auctionProducer) {
		this.auctionProducer = auctionProducer;
	}
	
	@PostMapping("/place-bid")
	public ResponseEntity<Object> placeBid(@RequestBody @Valid PlaceBidRequest placeBidRequest) throws CannotPlaceBidException,ConstraintViolationException{
		PlaceBidRequestForKarfa placeBidRequestForKarfa = PlaceBidRequestForKarfa.build(placeBidRequest.getUserRequest(), placeBidRequest.getProductId(), placeBidRequest.getBidAmount(),false,0);
		User existingUser = restTemplate.getForObject(
				"http://USER-SERVICE/user/email/" + placeBidRequest.getUserRequest().getEmail(), User.class);
		if(existingUser!=null) {
			placeBidRequestForKarfa.setUserId(existingUser.getId());
			Auction existingAuctionDetail =  auctionService.getAuctionDetail(existingUser.getId(), placeBidRequest.getProductId());
			if(existingAuctionDetail!=null) {
				throw new CannotPlaceBidException("You have already bid for this product. You can't bid again for the same product.");
			}		
		}else {
			placeBidRequestForKarfa.setNewUser(true);
		}
		
		Product product =  restTemplate.getForObject(
				"http://SELLER-SERVICE/seller/get/" + placeBidRequest.getProductId(), Product.class);
		if(product == null) {
			throw new CannotPlaceBidException("No product details are found for the productId mentioned in auction request.");
		}
		if(Calendar.getInstance().getTime().after( product.getBidEndDate())) {
			throw new CannotPlaceBidException("Auction for this product has ended. You can't bid now.");
		}
		
		auctionProducer.sendAuctionData(placeBidRequestForKarfa);
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	
	@PatchMapping(path = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<Auction> updateBid(@PathVariable Integer productId,@PathVariable String buyerEmailId, @PathVariable Integer newBidAmount ) throws CannotPlaceBidException{
		
		Product product = restTemplate.getForObject(
				"http://SELLER-SERVICE/seller/get/" + productId, Product.class);
		if(product == null) {
			throw new CannotPlaceBidException("No product details are found for the productId mentioned in auction request.");
		}
		if(Calendar.getInstance().getTime().after( product.getBidEndDate())) {
			throw new CannotPlaceBidException("Auction for this product has ended. You can't update bid amount now.");
		}
		
		User existingUser = restTemplate.getForObject(
				"http://USER-SERVICE/user/email/" + buyerEmailId, User.class);
		if(existingUser==null) {
			throw new CannotPlaceBidException("User details are not found for this email id. Please provide valid email id.");
		}
		
		if (newBidAmount < 0 ) {
			throw new CannotPlaceBidException("Bid amount can't be negative.");
		}
		
		Auction auction = auctionService.getAuctionDetail(existingUser.getId(), product.getId());
		if(auction==null) {
			throw new CannotPlaceBidException("You haven't bid on this product.");
		}
		auction.setBidAmount(newBidAmount);
		
		Auction updatedAuction =auctionService.updateAuction(auction);
		
		return new ResponseEntity<>(updatedAuction,HttpStatus.OK);
	}
	
	@GetMapping(path = "/get/{productId}")
	public ResponseEntity<Object> getAuctionDetailsByProductId(@PathVariable Integer productId){
		//List<Auction> list = auctionService.getAuctionDetails(productId);
		List<Auction> list= auctionService.getAuctionDetailsWithPagination(productId, 0, 5,"bidAmount");
		return new ResponseEntity<> (list.size(),HttpStatus.OK);
	}

	@GetMapping(path = "/getauction/{productId}")
	public ResponseEntity<Object> getAuctionDetailsByProductId(@PathVariable Integer productId,@RequestParam(required = false, defaultValue = "9999") int pagesize , @RequestParam(required = false,defaultValue = "0") int pagenumber,@RequestParam(required = false, defaultValue = "bidAmount") String field){
		AuctionList list = new AuctionList();
		List<Auction> auctions= auctionService.getAuctionDetailsWithPagination(productId, pagenumber, pagesize,field);
		list.setAuctionList(auctions);
		return new ResponseEntity<> (list,HttpStatus.OK);
	}
}
