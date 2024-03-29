package com.cts.microservices.sellerservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.microservices.sellerservice.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

	public Optional<Product> findById(Integer id);
	public List<Product> findAll();
}
