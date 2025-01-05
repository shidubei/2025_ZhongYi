package com.example.demo.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



public class Coins {
	
	@NotNull(message="target can not be null")
	private String target;
	
	@NotNull(message="coinList can not be null")
	private List<String> coinList;
	
	public Coins() {}
	
	public Coins(String target,List<String> coinList) {
		this.target=target;
		this.coinList=coinList;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public void setCoinList(List<String> coinList) {
		this.coinList=coinList;
	}
	
	public List<String> getCoinList(){
		return this.coinList;
	}
}
