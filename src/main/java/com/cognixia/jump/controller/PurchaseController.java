package com.cognixia.jump.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.NotEnoughStockException;
import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.repository.PurchaseRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetails;
import com.cognixia.jump.service.PurchaseService;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	@Autowired
	PurchaseService service;
	
	@Autowired
	PurchaseRepository repo;

	@Autowired
	JwtRequestFilter filter;
	
	@GetMapping()
	public List<Purchase> getPurchases() {
		return repo.findAll();
	}
	
	@Operation(summary = "Purchase A Game Using Id", 
			   description = "Purchase a game using the user id of the principal "
			   		+ "logged in user and the game id passed as a request parameter. "
			   		+ "Each purchase has a quantity, total price, time created/modified,"
			   		+ "and unique id in addition to game id and unique id")
	@PostMapping()
	public ResponseEntity<?> purchaseGameById(@RequestParam(name="game_id") Long game_id) 
		throws Exception {
	
		Purchase completed = service.purchaseGameIdAndQty(game_id, 1);
		
		return ResponseEntity.status(201).body(completed);
		
		
	}
	
	@Operation(summary = "Purchase A Game Using Id And Qty", 
			   description = "Purchase a game using the user id of the principal "
			   		+ "logged in user and the game id and quantity passed as request parameters. "
			   		+ "Each purchase has a quantity, total price, time created/modified,"
			   		+ "and unique id in addition to game id and unique id.")
	@PostMapping("/qty")
	public ResponseEntity<?> purchaseGameIdsAndQty(@RequestParam(name="game_id") Long game_id, 
			@RequestParam(name="qty") int qty) 
		throws Exception {
		
		Purchase completed = service.purchaseGameIdAndQty(game_id, qty);
		
		return ResponseEntity.status(201).body(completed);
		
	}
	
	@GetMapping("/game")
	public ResponseEntity<?> getGameById(@RequestParam(name="game_id") Long game_id) 
		throws Exception {
	
		List<Purchase> found = service.findByGameId(game_id);
		
		return ResponseEntity.status(201).body(found);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUserById() 
		throws Exception {
	
		List<Purchase> found = service.findByUser();
		
		return ResponseEntity.status(201).body(found);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updatePurchase(@RequestBody Purchase purchase) 
		throws Exception {
	
		Purchase updated = service.updatePurchase(purchase);
		
		return ResponseEntity.status(201).body(updated);
		
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deletePurchaseById(@RequestParam(name="pur_id") Long id) 
		throws Exception {
	
		Purchase deleted = service.deleteById(id);
		
		return ResponseEntity.status(201).body(deleted);
		
		
	}
	
	
	
	
	
	
	
}
