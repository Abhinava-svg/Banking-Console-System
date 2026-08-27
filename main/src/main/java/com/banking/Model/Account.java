package com.banking.Model;

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

    public void deposit(double amount){
        if(amount > 0){
            this.balance += amount;
        }
    }

    public void withdraw(double amount){
        if(amount > 0 && amount <= balance){
            this.balance -= amount;
        }
    }

    @Override
    public String toString(){
        return "accountNumber:" + accountNumber + "Customer:" + customer + "balance:" + balance;
    }

}
