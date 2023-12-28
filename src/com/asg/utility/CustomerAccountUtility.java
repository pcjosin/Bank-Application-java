package com.asg.utility;

import java.util.ArrayList;
import java.util.Scanner;

import com.asg.entity.Customer;
import com.asg.entity.Service;
import com.asg.entityless.Product;
import com.asg.services.CustomerAccountService;


public class CustomerAccountUtility {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("\n*********** WELCOME TO BANK *************\n");
		
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		ArrayList<Service> serviceList = new ArrayList<Service>();
		ArrayList<Product> productList = new ArrayList<Product>();

		int mainMenuChoice;
		
		do {
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++");

			System.out.println("\n1.Create Service\n"
					+ "2.Create Product\n"
					+ "3.Create Customer\n"
					+ "4.Manage Accounts\n"
					+ "5.Display Customer\n"
					+ "6.Exit\n\nEnter your choice : ");
			
			mainMenuChoice = scanner.nextInt();
			
			switch(mainMenuChoice) {
			
			case 1:
				char serviceChoice='y';
				do {
				
					Service service = CustomerAccountService.createService(serviceList);
					
					if (service!=null) {
						serviceList.add(service);
					}
					
					System.out.println("Do you want to add more Services ? (y/n)");
					serviceChoice=scanner.next().charAt(0);
					
				}while(serviceChoice=='y');
				
			break;
			
			
			case 2:
				
				if(serviceList.size()==0) {
					System.out.println("\nCreate service first!\n");
					break;
				}
				
				char productChoice='y';
				
				do {
				
					Product product = CustomerAccountService.createProduct(serviceList,productList);
					
					if (product!=null) {
						productList.add(product);
					}
					
					System.out.println("Do you want to add more Products ? (y/n)");
					productChoice=scanner.next().charAt(0);	
					
				}while(productChoice=='y');
				
				break;
				
				
			case 3:
				if(productList.size()==0) {
					System.out.println("\nCreate product first!\n");
					break;
				}
				
				Customer customer = CustomerAccountService.createCustomer(customerList,productList);
				
				if(customer!=null) {
					customerList.add(customer);
				}
				
				break;
				
				
			case 4: 
				if(customerList.size()==0) {
					System.out.println("\nCreate Customer first!\n");
					break;
				}
				
				CustomerAccountService.manageAccounts(customerList,productList,serviceList);
				
			break;
			
			
			case 5: 
				if(customerList.size()==0) {
					System.out.println("\nCreate Customer first!\n");
					break;
				}
				
				CustomerAccountService.displayCustomers(customerList);
				
			break;
			
			
			case 6: break;
			
			
			default: System.out.println("\nChoose a valid option :| \n");
			
			
			}

		}while(mainMenuChoice!=6);

		

	}

}
