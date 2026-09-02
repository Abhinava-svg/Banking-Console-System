package com.banking.service;

import com.banking.Model.Customer;
import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;
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

    public Account findAccount(int accountNumber){
        return accountsMap.get(accountNumber);
    }

    public void deposit(int accountNumber, double amount) throws InvalidAmountException{
        Account account = findAccount(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(int accountNumber, double amount) throws InvalidAmountException, InsufficientBalanceException{
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) throws InvalidAmountException, InsufficientBalanceException{

        Account fromAcc = findAccount(fromAccountNumber);
        Account toAcc = findAccount(toAccountNumber);
        
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }
}
