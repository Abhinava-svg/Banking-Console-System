package com.banking;

import com.banking.Model.Account;
import com.banking.Model.Customer;
import com.banking.service.Bank;
public class Main {
    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        bank.createCustomer(obj);

        Account acc = new Account(54875, obj, 100000);
        bank.createAccount(acc);

        System.out.println("Customer created:" + obj);
        System.out.println("Account created:" + acc);
    }
}
