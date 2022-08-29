package com.cts.microservices.buyerservice.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.microservices.buyerservice.entity.Auction;

public interface AuctionRepo extends JpaRepository<Auction, Integer> {

	public List<Auction> findByUserId(Integer userid);
	//public List<Auction> findByProductId(Integer productId);
	//public Page<Auction> findAllByProductId(Integer productId,Pageable peagable);
	public List<Auction> findAllByProductId(Integer productId,Pageable peagable);
	public Auction findByUserIdAndProductId(Integer userid,Integer productId);
}
