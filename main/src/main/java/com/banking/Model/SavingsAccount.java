package com.banking.Model;

public class SavingsAccount extends Account{

    private double interestRate;

    public SavingsAccount(int accountNumber, Customer customer, double balance, double interestRate){
        super(accountNumber, customer, balance);

        this.interestRate = interestRate;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }
}
