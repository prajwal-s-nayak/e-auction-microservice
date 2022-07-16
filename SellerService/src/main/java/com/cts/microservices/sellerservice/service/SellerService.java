package com.cts.microservices.sellerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.microservices.sellerservice.entity.Seller;
import com.cts.microservices.sellerservice.repositories.SellerRepo;

@Service
public class SellerService {

	@Autowired
	private SellerRepo repo;
	
	public List<Seller> getSellerByUserid(Integer userId){
		List <Seller> sellerList = repo.findByUserid(userId);
		return sellerList;
	}
	
	public Seller addSeller(Seller newSeller) {
		Seller seller = repo.save(newSeller);
		return seller;
	}
	
	public Seller getSellerByProductid(Integer productId){
		Optional<Seller> seller = repo.findByProductid(productId);
		return seller.get();
	}
	
	public Seller deleteSeller(Seller newSeller) {
		repo.delete(newSeller);
		return newSeller;
	}
}

