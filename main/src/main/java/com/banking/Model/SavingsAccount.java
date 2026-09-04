package com.banking.Model;

import com.banking.Exceptions.InvalidAmountException;

public class SavingsAccount extends Account{

    private double interestRate;

    public SavingsAccount(int accountNumber, Customer customer, double balance, double interestRate){
        super(accountNumber, customer, balance);

        if(interestRate <= 0 || interestRate > 100){
            throw new IllegalArgumentException("Invalid Interest rate");
        }
        this.interestRate = interestRate;
    }

    public void setInterestRate(double interestRate){

        if(interestRate <= 0 || interestRate > 100){
            throw new IllegalArgumentException("Invalid Interest rate");
        }
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public void applyInterest() throws InvalidAmountException{
        double interest = getBalance() * interestRate/100;
        deposit(interest);
    }
}
