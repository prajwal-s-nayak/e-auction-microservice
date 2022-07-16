package com.cts.microservices.sellerservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.microservices.sellerservice.entity.Seller;

public interface SellerRepo extends JpaRepository<Seller, Integer>{

	public List<Seller> findByUserid(Integer userid);
	public Optional<Seller> findByProductid(Integer productid);
}
