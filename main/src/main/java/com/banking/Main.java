package com.banking;

import com.banking.Model.Customer;
import com.banking.service.Bank;
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
            System.out.println("7. View Transaction");
            System.out.println("=============================");

            System.out.println("Enter your choice");

            int choice = sc.nextInt();

            switch(choice){

                case 1: 
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

                case 2: 
                    System.out.println("Create Account selected");
                    break;

                case 3:
                    System.out.println("Deposit selected");
                    break;

                case 4:
                    System.out.println("Withdraw selected");
                    break;

                case 5:
                    System.out.println("Transfer selected");
                    break;

                case 6:
                    System.out.println("Check Balance selected");
                    break;

                case 7:
                    System.out.println("View Transaction selected");
                    break;

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