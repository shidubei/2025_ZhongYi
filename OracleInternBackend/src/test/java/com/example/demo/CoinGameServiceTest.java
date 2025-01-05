package com.example.demo;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Coins;
import com.example.demo.service.CoinGameService;

@SpringBootTest
public class CoinGameServiceTest {
	
	@Autowired
	private CoinGameService coinGameService;
	
	// Test Valid Input
	@Test
	public void testValidInput() {
		Coins coins = new Coins();
		coins.setTarget("7.03");
		coins.setCoinList(List.of("0.01", "0.5", "1", "5", "10"));
		Map<Boolean,List<String>> result = coinGameService.calculateTarget(coins);
		
		Assertions.assertTrue(result.containsKey(true));
		List<String> expectedResult = List.of("0.01","0.01","0.01","1.00","1.00","5.00");
		
		Assertions.assertEquals(expectedResult, result.get(true));
	}
	
	// Test Invalid Input - target can not less than 0 or bigger than 10000
	@Test
	public void testInvalidTargetLessThan0Input() {
		Coins coins = new Coins();
		coins.setTarget("-1");
		coins.setCoinList(List.of("0.01", "0.5", "1", "5", "10"));
		
		Map<Boolean,List<String>> result = coinGameService.calculateTarget(coins);
		Assertions.assertTrue(result.containsKey(false));
		Assertions.assertEquals("Invalid target,must between 0-10000", result.get(false).get(0));
	}
	
	@Test
	public void testInvalidTargetBiggerThan10000Input() {
		Coins coins = new Coins();
		coins.setTarget("100001");
		coins.setCoinList(List.of("0.01", "0.5", "1", "5", "10"));
		
		Map<Boolean,List<String>> result = coinGameService.calculateTarget(coins);
		Assertions.assertTrue(result.containsKey(false));
		Assertions.assertEquals("Invalid target,must between 0-10000", result.get(false).get(0));
	}
	
	// Test Invalid Input - target's decimal place can not more than 2
    @Test
    public void testInvalidTargetDecimalPlaces() {
        Coins coins = new Coins();
        coins.setTarget("7.555"); // More than 2 decimal places
        coins.setCoinList(List.of("5.0", "2.0", "0.5"));

        Map<Boolean, List<String>> result = coinGameService.calculateTarget(coins);


        Assertions.assertTrue(result.containsKey(false));
        Assertions.assertEquals("The number of decimal places of the target must be less than or equal to 2", 
                result.get(false).get(0));
    }
    
    // Test Invalid Input - CoinList invalid
    @Test
    public void testInvalidCoinList() {
        Coins coins = new Coins();
        coins.setTarget("7.55");
        coins.setCoinList(List.of("5.0", "2.0", "0.3")); // Invalid coin (0.3)

        Map<Boolean, List<String>> result = coinGameService.calculateTarget(coins);

        Assertions.assertTrue(result.containsKey(false));
        Assertions.assertEquals("Invalid coinList,can choose in [0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0]",
                result.get(false).get(0));
    }
    
    // Test Target Can not be made
    @Test
    public void testTargetCannotBeMade() {
        Coins coins = new Coins();
        coins.setTarget("1.03"); // Cannot be made with given coins
        coins.setCoinList(List.of("0.5", "0.2"));
   
        Map<Boolean, List<String>> result = coinGameService.calculateTarget(coins);

        Assertions.assertTrue(result.containsKey(false));
        Assertions.assertEquals("Target cannot be made up of the coinList given", result.get(false).get(0));
    }

}
