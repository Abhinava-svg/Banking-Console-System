package com.banking.service;

import com.banking.Model.Customer;
import com.banking.Model.Account;
import java.util.*;

public class Bank {
    
    private List<Account> accounts;
    private List<Customer> customers;

    private Map<Integer, Account> accountsMap;
    private Map<Integer, Customer> customersMap;

    public Bank(){
        accounts = new ArrayList<>();
        accountsMap = new HashMap<>();

        customers = new ArrayList<>();
        customersMap = new HashMap<>();
    }

    public void createCustomer(Customer customer){
        customers.add(customer);
        customersMap.put(customer.getCustomerId(), customer);
    }

    public Customer findCustomer(int customerId){
        return customersMap.get(customerId);
    }

    public void createAccount(Account account){
        accounts.add(account);
        accountsMap.put(account.getAccountNumber(), account);
    }

    public Account findAccountNumber(int accountNumber){
        return accountsMap.get(accountNumber);
    }
}
