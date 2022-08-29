package com.cts.microservices.buyerservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.microservices.buyerservice.model.PlaceBidRequestForKarfa;
import com.cts.microservices.buyerservice.service.AuctionService;

@Service
public class AuctionConsumer {
	
	@Autowired
	private AuctionService auctionService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AuctionConsumer.class);

	@KafkaListener(topics = "auction_topic", groupId = "cosumergroup1")
	public void consume(PlaceBidRequestForKarfa placeBidRequest) {
		LOGGER.info(String.format("Auction data-> %s", placeBidRequest.toString()));
		auctionService.addAuction(placeBidRequest);
	}
	
}
