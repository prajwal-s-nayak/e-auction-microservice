package com.cts.microservices.buyerservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.cts.microservices.buyerservice.model.PlaceBidRequestForKarfa;

@Service
public class AuctionProducer {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AuctionProducer.class); 

	private KafkaTemplate<String, PlaceBidRequestForKarfa> kafkaTemlate;

	
	public AuctionProducer(KafkaTemplate<String, PlaceBidRequestForKarfa> kafkaTemplate) {
		this.kafkaTemlate = kafkaTemplate;
	}
	
	public void sendAuctionData(PlaceBidRequestForKarfa placeBidRequest) {
		LOGGER.info(String.format("Message sent %s", placeBidRequest.toString()));
		Message<PlaceBidRequestForKarfa> message = MessageBuilder.withPayload(placeBidRequest).setHeader(KafkaHeaders.TOPIC,"auction_topic").build();
		kafkaTemlate.send(message); 
	}

}
