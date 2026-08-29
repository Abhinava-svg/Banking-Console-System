package com.banking.Model;

import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;

public class Account {
    private final int accountNumber;
    private Customer customer;
    private double balance;

    public Account(int accountNumber, Customer customer, double balance){
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double amount) throws InvalidAmountException{
        if(amount > 0){
            this.balance += amount;
        }
        else
            throw new InvalidAmountException("Amount should be greater than zero");
    }

    public void withdraw(double amount) throws InsufficientBalanceException, InvalidAmountException {
        if(amount <= 0 ){
            throw new InvalidAmountException("Amount should be greater than zero");
        }
        else if (amount > balance) {
            throw new InsufficientBalanceException("Amount should not be greater than balance");
        }
        else{
            this.balance -= amount;
        }
          
    }
    @Override
    public String toString(){
        return "accountNumber:" + accountNumber + "Customer:" + customer + "balance:" + balance;
    }

}
