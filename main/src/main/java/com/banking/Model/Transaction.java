package com.banking.Model;

import com.banking.Enums.TransactionType;

public class Transaction{
    private final int transactionId;
    private final TransactionType type;
    private final double amount;
    
    public Transaction(int transactionId, TransactionType type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        
    }

    public int getTransactionId(){
        return transactionId;
    }

    public TransactionType getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    @Override
    public String toString(){
        return "TransactionId:" + transactionId + "Type:" + type + "Amount:" + amount;
    }
}
    

