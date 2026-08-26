package com.banking.Model;

public class Customer {
    
    private final int customerId;
    private String name;
    private String email;


    public Customer(int customerId, String name, String email){
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId(){
        return customerId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString(){
        return "customerId :" + customerId + "name :" + name + "email :" + email;
    }
}

