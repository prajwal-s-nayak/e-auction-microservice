package com.cts.microservices.sellerservice.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cts.microservices.sellerservice.entity.Product;
import com.cts.microservices.sellerservice.entity.Seller;
import com.cts.microservices.sellerservice.exception.CannotDeleteProductException;
import com.cts.microservices.sellerservice.exception.ProductNotFoundException;
import com.cts.microservices.sellerservice.model.AddProductRequest;
import com.cts.microservices.sellerservice.model.AddProductResponse;
import com.cts.microservices.sellerservice.model.AuctionList;
import com.cts.microservices.sellerservice.model.AuctionResponse;
import com.cts.microservices.sellerservice.model.ShowBidResponse;
import com.cts.microservices.sellerservice.model.User;
import com.cts.microservices.sellerservice.service.ProductService;
import com.cts.microservices.sellerservice.service.SellerService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("seller")
public class SellerController {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping(path = "/add-product")
	public ResponseEntity<Object> addUser(@RequestBody @Valid AddProductRequest addProductRequest)
			throws ConstraintViolationException {
		// restTemplate.getForObject("http://localhost:8081/user/add", List.class);
		long productId = 0;
		User existingUser = restTemplate.getForObject(
				"http://USER-SERVICE/user/email/" + addProductRequest.getUserRequest().getEmail(), User.class);
		if (existingUser != null) {
			Product product = productService.addProduct(addProductRequest.getProductRequest());
			if (product == null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				productId = product.getId();
				Seller newSeller = new Seller();
				newSeller.setUserid(existingUser.getId());
				newSeller.setProductid(product.getId());
				sellerService.addSeller(newSeller);
			}
			
		} else {
			User user = restTemplate.postForObject("http://USER-SERVICE/user/add", addProductRequest.getUserRequest(),
					User.class);
			if (user == null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				Product product = productService.addProduct(addProductRequest.getProductRequest());
				if (product == null) {
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					productId = product.getId();
					Seller newSeller = new Seller();
					newSeller.setUserid(user.getId());
					newSeller.setProductid(product.getId());
					sellerService.addSeller(newSeller);
				}
			}
		}
		
		return new ResponseEntity<>(new AddProductResponse(productId), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/delete/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Integer productId)
			throws ProductNotFoundException, CannotDeleteProductException {
		// Seller seller = sellerService.getSellerByProductid(productId);
		// if(seller.getUserid()==userId) {
		Product product = productService.getProductById(productId);
		if(product==null) {
			throw new ProductNotFoundException("Product not found with id:"+productId);
		}
		if (Calendar.getInstance().getTime().after(product.getBidEndDate())) {
			throw new CannotDeleteProductException("Can not delete the product after its end date for the bid ("
					+ product.getBidEndDate().toString() + " )");
		}
		
		Integer listSize = restTemplate.getForObject("http://BUYER-SERVICE/buyer/get/" + productId, Integer.class);
		if(listSize>0) {
			throw new CannotDeleteProductException("Can not delete the product as there is an active bidding on it.");
		}
		
		Integer deletedProductId = productService.deleteProduct(product);
		if (deletedProductId == productId) {
			Seller seller = sellerService.getSellerByProductid(productId);
			sellerService.deleteSeller(seller);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		// }
		return null;
	}
	
	@GetMapping(path = "/get/{productId}")
	public ResponseEntity<Object> getProductById(@PathVariable Integer productId) throws ProductNotFoundException{
		Product product = productService.getProductById(productId);
		return new ResponseEntity<> (product,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/show-bids/{productId}")
	public ResponseEntity<Object> showBidByProductId(@PathVariable Integer productId,@RequestParam(required = false, defaultValue = "9999") int pagesize , @RequestParam(required = false,defaultValue = "0") int pagenumber,@RequestParam(required = false, defaultValue = "bidAmount") String field) throws ProductNotFoundException{
		//List<ShowBidResponse> list = new ArrayList<>();
		Product product = productService.getProductById(productId);
		if(product==null) {
			throw new ProductNotFoundException("Product not found with id:"+productId);
		}
		ShowBidResponse showBidRespose = new ShowBidResponse();
		showBidRespose.setProduct(product);
		List<AuctionResponse> list = new ArrayList<>();
		
		String url = "http://BUYER-SERVICE/buyer/getauction/{productId}";
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("productId",Integer.toString(productId));
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("pagesize", Integer.toString(pagesize))
		        .queryParam("pagenumber", Integer.toString(pagenumber))
		        .queryParam("field", field);
		ResponseEntity<AuctionList> auctionsTemp = restTemplate.exchange(builder.buildAndExpand(urlParams).toUri() , HttpMethod.GET,null,AuctionList.class);
		AuctionList auctions = auctionsTemp.getBody();
		
		//AuctionList auctions = restTemplate.getForObject("http://BUYER-SERVICE/buyer/getauction/" + productId, AuctionList.class);
		auctions.getAuctionList().stream().forEach((auction) -> {
			User existingUser = restTemplate.getForObject(
					"http://USER-SERVICE/user/id/" + auction.getUserId(), User.class);
			if (existingUser != null) {
				AuctionResponse auctionResponse = AuctionResponse.build(auction.getBidAmount(), existingUser.getFirstname()+ " " + existingUser.getLastname(), existingUser.getEmail(), existingUser.getPhone());
				list.add(auctionResponse);
			}
		});
		showBidRespose.setAuctionData(list);
		return new ResponseEntity<> (showBidRespose,HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/product/get/")
	public ResponseEntity<Object> getAllProductIds(){
		List<Integer> list = productService.getAllProductIds();
		return new ResponseEntity<> (list,HttpStatus.OK);
	}
}
