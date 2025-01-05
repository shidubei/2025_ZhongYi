package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Coins;
import com.example.demo.service.CoinGameService;

import jakarta.validation.Valid;

@RestController
public class GetMinCoinListController {
	
	@Autowired
	private CoinGameService coinGameService;
	
	List<Double> COIN_LIST = Arrays.asList(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);
	
    @GetMapping("/")
    public ResponseEntity<Void> redirectToIndex() {
        return ResponseEntity.status(HttpStatus.FOUND)  
                .header("Location", "/index.html")   
                .build();
    }

	@PostMapping("/Coin-game")
	public ResponseEntity getCoinList(@RequestBody @Valid Coins coins){
		Boolean isSuccess = null;
		List<String> message = null;
		
		Map<Boolean,List<String>> result = coinGameService.calculateTarget(coins);
		
		for(Map.Entry<Boolean, List<String>> entry: result.entrySet()) {
			isSuccess = entry.getKey();
			message = entry.getValue();
		}
		
		if(isSuccess && isSuccess !=null) {
			return new ResponseEntity<>(message,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
	} 
}
