package com.banking.Model;

public class Transaction{
    private final int transactionId;
    private final String type;
    private final double amount;
    
    public Transaction(int transactionId, String type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        
    }

    public int getTransactionId(){
        return transactionId;
    }

    public String getType(){
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
    

