package com.asg.services;

import java.util.ArrayList;
import java.util.Scanner;

import com.asg.entity.Service;
import com.asg.entityless.Product;
import com.asg.entity.Account;
import com.asg.entity.CurrentAccount;
import com.asg.entity.Customer;
import com.asg.entity.LoanAccount;
import com.asg.entity.SavingsMaxAccount;

public class CustomerAccountService {
	
	private static long idCounter = 0; 
	//to generate unique id for account no

	public static synchronized String createID(){
	    return String.valueOf(idCounter++);
	} 

	
	// to create service
	
	public static Service createService(ArrayList<Service> serviceList) {
		
		System.out.println("\n*********** CREATE SERVICE *************\n");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter Service code : ");
		String serviceCode=scanner.next();
		
		
		if(serviceList.size()>0) {
			for (Service service:serviceList) {
				if(service.getServiceCode().compareTo(serviceCode)==0) {
					System.out.println("\nDuplicate Service code found!\n");
					return null;
	
				}
			}
		}
		
		System.out.println("Enter Service name : ");
		String serviceName=scanner.next();
		
		System.out.println("Enter Service rate : ");
		double serviceRate=scanner.nextDouble();
		
		Service service = new Service(serviceCode,serviceName,serviceRate);
		
		return service;
	}
	
	
	
	// to create products

	public static Product createProduct(ArrayList<Service> serviceList, ArrayList<Product> productList) {
		System.out.println("\n*********** CREATE PRODUCT *************\n");
	
		Scanner scanner = new Scanner(System.in);
		
		ArrayList<Service> chosenServiceList = new ArrayList<Service>();
		Product product = null;
		
		String productCode;
		String productName;
		
		String productNames[][]= {{"SavingsMaxAccount","pr01"},{"CurrentAccount","pr02"},{"LoanAccount","pr03"}};
		
		if(productList.size()==productNames.length) {
			System.out.println("\nAll possible products already created!\n");
			return null;

		}
		
		System.out.println("\n----------- Choose a product-------------\n");
		
		for(String possibleProductNames[] :productNames) {	
			
			boolean productExist=false;
			
			for (Product existingProduct : productList) {	
				if(possibleProductNames[0].compareTo(existingProduct.getProductName())==0) {
					productExist=true;
				}	
			}
			
			if(productExist==false) {
				System.out.println(possibleProductNames[1]+" - "+possibleProductNames[0]);
			}
		}
		
		System.out.println("\n\nEnter product Id:");
		
		String productChoice=scanner.next();
		
		for (Product existingProduct :productList) {
			if(existingProduct.getProductCode().compareTo(productChoice)==0) {
				System.out.println("\nInvalid Choice!\n");
				return null;

			}
		}
		
		System.out.println("\n----------- Add services -------------\n");
		
		int i=1;
		for(Service service : serviceList) {
			System.out.println(i+" "+service.getServiceName());
			i++;
		}
		
		System.out.println("\nChoose the services you want to add to this product \n(input format : 1,2,3) :");
		String serviceChoice = scanner.next();
		
		String serviceChoiceArray[]=serviceChoice.split("[,]",0);
		
		for(String choice: serviceChoiceArray) {
			chosenServiceList.add((serviceList).get(Integer.parseInt(choice)-1));
		}
		
		switch (productChoice) {
		
		case "pr01": 
			productCode ="pr01";
			productName = "SavingsMaxAccount";
			
			product=new SavingsMaxAccount(productCode,productName,chosenServiceList);
			break;
			
		case "pr02": 
			productCode ="pr02";
			productName = "CurrentAccount";
			
			product=new CurrentAccount(productCode,productName,chosenServiceList);
			break;
			
		case "pr03":
			productCode ="pr03";
			productName = "LoanAccount";
			
			product=new LoanAccount(productCode,productName,chosenServiceList);
			break;
			
		default: 
			System.out.println("\nInvalid Choice!\n");
			break;		
		}
		
		return product;
	}
	
	
	
	
	//to create customer

	public static Customer createCustomer(ArrayList<Customer> customerList, ArrayList<Product> productList) {
		
		System.out.println("\n********* CREATE CUSTOMER *************\n\n");
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter customer code :");
		String customerCode = scanner.next();
		
		
		for (Customer existingCustomer:customerList) {
			if(existingCustomer.getCustomerCode().compareTo(customerCode)==0) {
				System.out.println("\nDuplicate customer code found!\n");
				return null;
			}
		}
		
		System.out.println("Enter customer name :");
		String customerName = scanner.next();
		
		Account account = createAccount(productList);   //every customer should have an account
		ArrayList<Account> accountList = new ArrayList<Account>();
		accountList.add(account);
	
		Customer customer = new Customer(customerCode,customerName,accountList);

		return customer;
	}
	
	
	
	// to create account for the first time

	private static Account createAccount(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n----------- Create an account-------------\n");
		
		String accountNo=createID();
		
		System.out.println("Choose account type : ");
		String accountType;

		int i=1;
		for(Product product : productList) {
			System.out.println(i+" "+product.getProductName());
			i++;
		}
		int productChoice=scanner.nextInt();
		
		System.out.println("\n\nEnter your choice:");
		Product product = productList.get(productChoice-1);
		
		accountType=product.getProductName();
		
		double balance=0;

		
		System.out.println("Do you want to deposit money?");
		Account account = new Account(accountNo,accountType,balance,product);

		
		if(scanner.next().charAt(0)=='y')
		{
			account = depositCash(account); 
		}
		
		return account;
	}
	
	
	
	
	// to manage accounts

	public static void manageAccounts(ArrayList<Customer> customerList, ArrayList<Product> productList, ArrayList<Service> serviceList) {
		Scanner scanner = new Scanner(System.in);
		
		
		System.out.println("\n********* MANAGE ACCOUNTS *************\n\n");

		System.out.println("Enter customer ID : ");
		String customerCode=scanner.next();	
		
		Customer currentCustomer=null;
		
		for(Customer customer: customerList) {
			if (customer.getCustomerCode().compareTo(customerCode)==0) {
				currentCustomer=customer;
			}
		}
		
		if(currentCustomer==null) {
			System.out.println("\nCustomer not found! check the ID you entered!\n");
			return;
		}
		
		int functionChoice;
		
		do {

			System.out.println("\nChoose a function:\n\n1. Show customer details\n2. Do transactions \n3. Manage Existing accounts \n4. create new Account \n5.back");
			functionChoice= scanner.nextInt();
			
			switch(functionChoice) {
			
				case 1: showCustomerDetails(currentCustomer);
				break;
				
				case 2: doTransactions(currentCustomer,customerList);
				break;
				
				case 3 : manageExistingAccounts(currentCustomer,serviceList);
				break;
				
				case 4 : createAccount(productList,currentCustomer);
				break;
				
				case 5: break;
				
				default: System.out.println("\nEnter a valid choice! \n");
				break;
			}
			
		}while(functionChoice!=5);	
		
	}
	
	
	
	
	
	// to manage existing accounts from manage accounts - add or remove services 

	private static void manageExistingAccounts(Customer currentCustomer, ArrayList<Service> serviceList) {
		
		System.out.println("\n------------------ Manage Accounts ----------------\n\n");
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("Choose an account to change its properties : ");
		displayAccountDetails(currentCustomer.getAccountList());
		
		System.out.println("Enter account no : ");
		String accountNo=scanner.next();
		
		boolean accountExist=false;
		
		for(Account account: currentCustomer.getAccountList()) {
			
			if (account.getAccountNo().compareTo(accountNo)==0) {
				
				accountExist=true;
				
				System.out.println("1. add services\n2. remove services\n");
				int manageChoice=scanner.nextInt();
				
				switch(manageChoice) {
				
				case 1:
					
					System.out.println("Choose services you want to add : ");
					
					for(Service service : serviceList) {
						
						boolean serviceExist=false;
						
						for(Service activeService: account.getProduct().getServiceList()) {
							if(service==activeService) {
								serviceExist=true;
							}
						}
						
						if(serviceExist==false) {
							System.out.println(service.getServiceCode() +" - "+service.getServiceName());
						}
						
						else {
							serviceExist=false;
						}

					}
					
					System.out.println("Enter service codes (format: s1,s2) :");
					String serviceCodes = scanner.next();
					
					String serviceCodeArray[]=serviceCodes.split("[,]",0);
					
					boolean serviceExist=false;

					for(Service service : serviceList) {
						for(String serviceCode: serviceCodeArray) {
							if (service.getServiceCode().compareTo(serviceCode)==0) {
								serviceExist=true;
								account.getProduct().getServiceList().add(service);
							}
						}
						
					}
					
					if(serviceExist==false) {
						 System.out.println("\nEnter a valid service code\n");
						 break;
					}
					
					System.out.println("\nServices added successfully! Happy banking!\n");
					break;

				case 2:
					
					if(account.getProduct().getServiceList().size()<=1) {
						 System.out.println("\nAn account should have atleast one service!\n");
						 break;
					}
					
					System.out.println("Choose services you want to remove : ");
					
					int i=1;
					for(Service activeService: account.getProduct().getServiceList()) {
						System.out.println(i+" "+activeService.getServiceCode() +" - "+activeService.getServiceName());
						i++;
					}
					
					System.out.println("Enter service index (format: 1,2) :");
					String serviceChoices = scanner.next();
					
					String serviceChoiceArray[]=serviceChoices.split("[,]",0);
					
					try {
						for(String serviceChoice : serviceChoiceArray) {
							account.getProduct().getServiceList().remove(Integer.parseInt(serviceChoice)-1);
						}
					}
					
					catch(Exception e) {
						System.out.println("\nInvalid input!\n");
						break;	
					}
					
					System.out.println("\nServices removed successfully!\n");
					break;
					
					
				default: System.out.println("\nEnter a valid Choice!\n");


				}		
			}
		}
		
		if(accountExist==false) {
			 System.out.println("Enter a valid account number!");
		}
	}

		
	
	
	// to do transactions from manage accounts
	
	private static void doTransactions(Customer currentCustomer, ArrayList<Customer> customerList) {
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n------------------ Transactions ----------------\n\n");
		
		String accountNo;

		int transactionChoice;
		
		do {
			
			System.out.println("1. display Balance\n2. Deposit \n3. withdraw \n4. Transfer cash \n5. Back\n\n");
			transactionChoice=scanner.nextInt();
			
			switch(transactionChoice) {
			case 1: displayBalance(currentCustomer.getAccountList());
			break;
			
			case 2:
				System.out.println("Choose an account to deposit cash into : ");
				displayBalance(currentCustomer.getAccountList());
				
				System.out.println("Enter Account number : ");
				accountNo=scanner.next();
				
				boolean accountExist=false;
	
				for(Account account: currentCustomer.getAccountList()) {
					if (account.getAccountNo().compareTo(accountNo)==0) {
						accountExist=true;
	
						account = depositCash(account);
					}
				}
				
				if(accountExist==false) {
					 System.out.println("\nEnter a valid account number!\n");
				}
				
			break;
			
			case 3:
				
				System.out.println("Choose an account to withdraw cash from : ");
				displayBalance(currentCustomer.getAccountList());
				
				accountExist=false;
	
				System.out.println("Enter Account number : ");
				accountNo=scanner.next();
				
				for(Account account: currentCustomer.getAccountList()) {
					if (account.getAccountNo().compareTo(accountNo)==0) {
						accountExist=true;
	
						account=withdrawCash(account);
					}
				}
				
				if(accountExist==false) {
					 System.out.println("\nEnter a valid account number!\n");
				}
				
			break;
			
			case 4: 
				
				transferCash(currentCustomer,customerList);
			break;
			
			case 5: break;
			
			default : System.out.println("\nEnter a valid choice! \n");
			
			}
		
		}while(transactionChoice!=5);
	}
	
	
	
	//to display balance

	private static void displayBalance(ArrayList<Account> accountList) {
		
		System.out.println("Account N0 | Account Type | Account Bal");
		
		for (Account account: accountList) {
			System.out.println(account.getAccountNo()+" | "+account.getAccountType()+" | "+account.getBalance());
		}	
	}
	
	
	
	//to transfer cash between two accounts

	private static void transferCash(Customer currentCustomer, ArrayList<Customer> customerList) {
		
		System.out.println("\n----- Cash transfer --------------\n");
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nEnter the account number you want to transfer cash to : ");
		String toAccountNo=scanner.next();
		
		boolean toAccountExist=false;

		for(Customer customer : customerList) {
			
			for(Account toAccount: customer.getAccountList()) {
				
				if(toAccount.getAccountNo().compareTo(toAccountNo)==0) {
					toAccountExist=true;

					System.out.println("Enter the amount you want to transfer :");
					double transferAmount=scanner.nextDouble();
					
					System.out.println("Choose an account to transfer cash from : ");
					displayBalance(currentCustomer.getAccountList());
					
					System.out.println("Enter Account number : ");
					String accountNo=scanner.next();
					
					boolean accountExist=false;

					for(Account account: currentCustomer.getAccountList()) {
						
						if (account.getAccountNo().compareTo(accountNo)==0) {
							accountExist=true;
							

							if(account.getProduct() instanceof SavingsMaxAccount) {
								
								double minimumBalance = ((SavingsMaxAccount) account.getProduct()).getMinimumBalance();
								
								if ((account.getBalance()-transferAmount)<minimumBalance) {
									System.out.println("\nMinimum balance of 1000 should be kept!\n");
									return;
								}
								
								else {
									account.setBalance(account.getBalance()-transferAmount);
								}
							}
							
							
							else {
								if ((account.getBalance()-transferAmount)<0) {
									System.out.println("\nNo sufficient balance!\n");
									return;
								}
								
								else {
									account.setBalance(account.getBalance()-transferAmount);
								}
								
							}	
							toAccount.setBalance(toAccount.getBalance()+transferAmount);	
						}
						
						
					}
					
					if(accountExist==false) {
						 System.out.println("\nYou entered an invalid account number! transaction failed!\n");
					}
				}
			}
		}
		
		
		if(toAccountExist==false) {
			 System.out.println("\nThe account doesnt exist! transaction failed!\n");
		}

	}
	
	
	
	// to withdraw cash

	private static Account withdrawCash(Account account) {
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n----- Cash deposit--------------\n");
		
		System.out.println("\nEnter the amount you want to withdraw : ");
		double withdraw=scanner.nextDouble();
		
		if(account.getProduct() instanceof SavingsMaxAccount) {
			
			double minimumBalance = ((SavingsMaxAccount) account.getProduct()).getMinimumBalance();
			
			if ((account.getBalance()-withdraw)<minimumBalance) {
				System.out.println("\nMinimum balance of 1000 should be kept!\n");
			}
			
			else {
				account.setBalance(account.getBalance()-withdraw);
			}
		}
		
		else {
			
			if ((account.getBalance()-withdraw)<0) {
				System.out.println("\nNo sufficient balance!\n");
			}
			
			else {
				account.setBalance(account.getBalance()-withdraw);
			}
			
		}
		return account;		
	}
	
	
	
	// to deposit cash into an account

	private static Account depositCash(Account account) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n----- Cash deposit--------------\n");
		
		System.out.println("\nEnter the amount you want to deopsit : ");
		double deposit=scanner.nextDouble();
		
		if(account.getProduct() instanceof LoanAccount) {
			
			System.out.println("\nChoose the type of deposit \n1. cash deposit\n2. cheque deposit\n\nEnter your choice: ");
			int depositChoice=scanner.nextInt();
			
			switch(depositChoice) {
			
			case 1: account.setBalance(account.getBalance()+deposit);
			break;
			
			case 2: account.setBalance(account.getBalance()+(deposit-(deposit*0.003)));
			break;
			
			default: System.out.println("\nInvalid choice !\n");
			break;
			}
		}
		
		else {
			account.setBalance(account.getBalance()+deposit);
		}
		
		return account;		
		
	}
	
	
	
	
	// to show the details of a customer

	private static void showCustomerDetails(Customer currentCustomer) {
		System.out.println("\n------------------ Customer details ----------------\n\n");
		
		System.out.println("Customer code :" + currentCustomer.getCustomerCode());
		System.out.println("Customer name :" + currentCustomer.getCustomerName());
		
		displayAccountDetails(currentCustomer.getAccountList());
		
	}
	
	
	
	// to show account details

	private static void displayAccountDetails(ArrayList<Account> accountList) {		
		System.out.println("\n------------------ Account Details ----------------\n\n");

		System.out.println("Account N0 | Account Type | Account Bal");
		
		for (Account account: accountList) {
			
			System.out.println(account.getAccountNo()+" | "+account.getAccountType()+" | "+account.getBalance());
			System.out.print("\nServices available : ");

			for(Service service: account.getProduct().getServiceList()) {
				System.out.print(service.getServiceName() + ", ");
			}
			
			System.out.print("\n");

		}
		
	}

	
	
	// to create new account for an exixting customer
	
	private static void createAccount(ArrayList<Product> productList, Customer currentCustomer) { //overloaded method
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n----------- Create an account-------------\n");
		
		if(productList.size()==currentCustomer.getAccountList().size()) {
			System.out.println("\nMaximum number of accounts already created!\n");
			return;

		}
		
		String accountNo=createID();
		
		System.out.println("Choose account type : ");
		String accountType;
		Product chosenProduct=null;
		
		for(Product product : productList) {
			
			boolean productExist=false;
			
			for(Account existingAccount: currentCustomer.getAccountList()) {
				if(existingAccount.getProduct()==product) {
					productExist=true;
				}
			}
			
			if(productExist==false) {
					System.out.println(product.getProductCode()+" "+product.getProductName());
			}
	
			else {
				productExist=false;
			}

		}

		String productCodeChosen=scanner.next();
		
		for (Product product : productList) {
			if(product.getProductCode().compareTo(productCodeChosen)==0) {
				chosenProduct=product;
			}
		}
		
		for(Account existingAccount: currentCustomer.getAccountList()) {  

			if(chosenProduct==null || existingAccount.getProduct()==chosenProduct) {  //even though a product is not shown, it could be chosen if code is entered!
				System.out.println("\nChoose a valid product!\n");
				return;
			}
		}
		
		accountType=chosenProduct.getProductName();
		double balance=0;

		System.out.println("Do you want to deposit money?");
		Account account = new Account(accountNo,accountType,balance,chosenProduct);

		
		if(scanner.next().charAt(0)=='y')
		{
			account = depositCash(account); 
		}
		
		currentCustomer.getAccountList().add(account);
		
	}
	
	
	
	// to display the customers and their details

	public static void displayCustomers(ArrayList<Customer> customerList) {
		
		System.out.println("\n***************** Display customers ********************\n");
		
		for(Customer customer : customerList) {
			showCustomerDetails(customer);
		}			
	}
}
