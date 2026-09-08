package com.banking;

import com.banking.Model.Customer;
import com.banking.service.Bank;
import com.banking.Model.Account;
import com.banking.Exceptions.DuplicateAccountException;
import com.banking.Exceptions.DuplicateCustomerException;

import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while(running){
            System.out.println("\n===========================");
            System.out.println("        BANKING SYSTEM");
            System.out.println("=============================");
            System.out.println("1. Create Customer");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Check Balance");
            System.out.println("7. View Transactions");
            System.out.println("=============================");

            System.out.println("Enter your choice");

            int choice = sc.nextInt();

            switch(choice){

                case 1: {
                    System.out.println("Enter Customer ID:");
                    int CustomerId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Customer Name:");
                    String name = sc.nextLine();

                    System.out.println("Enter Customer Email");
                    String email = sc.nextLine();

                    Customer obj = new Customer(CustomerId, name, email);

                    try{
                        bank.createCustomer(obj);
                        System.out.println("Customer created successfully");
                    }
                    catch(DuplicateCustomerException e){
                        System.out.println(e.getMessage());
                    }
                    catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                }

                case 2: {
                    System.out.println("Enter Customer ID:");
                    int customerId = sc.nextInt();

                    Customer customer = bank.findCustomer(customerId);

                    if(customer == null){
                        System.out.println("Customer not found");
                        break;
                    }

                    System.out.println("Enter Account number");
                    int accountNumber = sc.nextInt();

                    System.out.println("Enter initial balance:");
                    double balance = sc.nextDouble();

                    Account acc = new Account(accountNumber, customer, balance);
                    
                    try{
                        bank.createAccount(acc);
                        System.out.println("Account created successfully");
                    }

                    catch(DuplicateAccountException e){
                        System.out.println(e.getMessage());
                    }

                    catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 3:{
                    System.out.println("Enter Account Number");
                    int accountNumber = sc.nextInt();

                    System.out.println("Enter deposit amount");
                    double amount =  sc.nextDouble();

                    try{
                        bank.deposit(accountNumber, amount);
                        System.out.println("Amount deposited successfully!!");

                        Account account = bank.findAccount(accountNumber);
                        System.out.println("Current Balance:" + account.getBalance());
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }


                case 4:{
                    System.out.println("Enter Account Number");
                    int accountNumber = sc.nextInt();

                    System.out.println("Enter withdraw amount");
                    double amount = sc.nextDouble();

                    try{
                        bank.withdraw(accountNumber, amount);
                        System.out.println("Amount withdraw successfully!!");

                        Account account = bank.findAccount(accountNumber);
                        System.out.println("Current balance:" + account.getBalance());
                    }

                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 5:{
                    
                System.out.println("Enter Sender Account Number");
                int fromAccountNumber = sc.nextInt();

                System.out.println("Enter Receiver Account Number");
                int toAccountNumber = sc.nextInt();

                System.out.println("Enter Transfer amount");
                double amount = sc.nextDouble();

                try{
                    bank.transfer(fromAccountNumber, toAccountNumber, amount);
                    System.out.println("Transfer Successfully!!");

                    Account sender = bank.findAccount(toAccountNumber);
                    Account receiver = bank.findAccount(toAccountNumber);

                    System.out.println("Sender balance:" + sender.getBalance());
                    System.out.println("Receiver balance" + receiver.getBalance());
                }

                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }

                case 6:{
                    System.out.println("Enter account number");
                    int accountNumber = sc.nextInt();

                    Account account = bank.findAccount(accountNumber);

                    if(account == null){
                        System.out.println("Account not found");
                        break;
                    }

                    System.out.println("Current Balance:" + account.getBalance());
                    break;
            }

                case 7:{
                    System.out.println("Enter Account number");
                    int accountNumber = sc.nextInt();

                    Account account = bank.findAccount(accountNumber);

                    if(account == null){
                        System.out.println("Account not found");
                        break;
                    }

                    System.out.println("Transactions History");
                    account.showTransaction();
                    break;
            }

                case 8:
                    running = false;
                    System.out.println("thank you for using banking system");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

        sc.close();
    }
}