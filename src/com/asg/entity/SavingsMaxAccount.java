package com.asg.entity;

import java.util.ArrayList;

import com.asg.entityless.Product;

public class SavingsMaxAccount extends Product {
	
	private double minimumBalance=1000;

	public double getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public SavingsMaxAccount(String productCode, String productName, ArrayList<Service> serviceList) {
		super(productCode, productName, serviceList);
		// TODO Auto-generated constructor stub
	}
	

}
