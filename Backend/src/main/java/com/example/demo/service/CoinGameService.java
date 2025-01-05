package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Coins;

/*CoinGameService:
 * the service that controller use to provide the game
*/
@Service
public class CoinGameService {
	// Default COIN_LIST
	List<Double> COIN_LIST = Arrays.asList(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0,10.0,50.0,100.0,1000.0);
	
	public Map<Boolean,List<String>> calculateTarget(Coins coins) {
		// Store the back data
		Map<Boolean,List<String>> result = new HashMap<>();
		List<String> coinResult = new ArrayList<>();
		List<String> message = new ArrayList<>();
		
	    
		// Get the data
		String target = coins.getTarget();
		double targetNum = Double.parseDouble(target);
		List<String> coinList = coins.getCoinList();
		List<Double> doubleCoinList = coinList.stream().map(coin -> Double.valueOf(coin)).collect(Collectors.toList());
		
		System.out.println(targetNum);
		System.out.println(doubleCoinList);
		// Check the data
		if(!COIN_LIST.containsAll(doubleCoinList)) {
			message.add("Invalid coinList,can choose in [0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0]");
			result.put(false, message);
			return result;
		}
		
		
		if(targetNum<0 || targetNum>10000) {
			message.add("Invalid target,must between 0-10000");
			result.put(false,message);
			return result;
		}else if(target.split("\\.").length>1&&target.split("\\.")[1].length()>2) {
			message.add("The number of decimal places of the target must be less than or equal to 2");
			result.put(false,message);
			return result;
		}
		
		int targetInt = (int) Math.round(targetNum*100);
		
		int[] dp = new int[targetInt+1];
		int[] coinUsed = new int[targetInt+1];
		
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0]=0;
		
		for(int i=0;i<doubleCoinList.size();i++) {
			int coinValue = (int) Math.round(doubleCoinList.get(i)*100);
			
			for(int j = coinValue;j<=targetInt;j++) {
				if(dp[j-coinValue] != Integer.MAX_VALUE && dp[j-coinValue]+1<dp[j]) {
					dp[j] = dp[j-coinValue]+1;
					coinUsed[j]=coinValue;
				}
			}
		}
		
		if(dp[targetInt] == Integer.MAX_VALUE) {
	        message.add("Target cannot be made up of the coinList given");
	        result.put(false, message);
	        return result;
		}
		
	    // Backtrack to find the coins used
	    int amount = targetInt;
	    while (amount > 0) {
	        int coin = coinUsed[amount];
	        coinResult.add(String.format("%.2f", coin / 100.0)); // convert coin back to double format
	        amount -= coin;
	    }
	
	    // Reverse the result to show coins in the order they were added
	    Collections.reverse(coinResult);
	
	    // Return the result
	    result.put(true, coinResult);
	    return result;
		
	}
}
