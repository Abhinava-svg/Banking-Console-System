package com.banking.service;

import com.banking.Model.Customer;
import com.banking.Exceptions.DuplicateAccountException;
import com.banking.Exceptions.DuplicateCustomerException;
import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;
import com.banking.Model.Account;
import com.banking.Exceptions.AccountNotFoundException;
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

    public void createCustomer(Customer customer) throws DuplicateCustomerException{

        if(customersMap.containsKey(customer.getCustomerId())){
            throw new DuplicateCustomerException("Customer ID already exists");
        }
        customers.add(customer);
        customersMap.put(customer.getCustomerId(), customer);
    }

    public Customer findCustomer(int customerId){
        return customersMap.get(customerId);
    }

    public void createAccount(Account account) throws DuplicateAccountException{

        if(accountsMap.containsKey(account.getAccountNumber())){
            throw new DuplicateAccountException("Account number already exist");
        }
        accounts.add(account);
        accountsMap.put(account.getAccountNumber(), account);
    }

    public Account findAccount(int accountNumber){
        return accountsMap.get(accountNumber);
    }

    public void deposit(int accountNumber, double amount)throws InvalidAmountException, AccountNotFoundException{
        Account acc = findAccount(accountNumber);

        if(acc == null){
            throw new AccountNotFoundException("Account not Found");
        }
        acc.deposit(amount);
    }

    public void withdraw(int accountNumber, double amount) throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException{
        Account acc = findAccount(accountNumber);

        if(acc == null){
            throw new AccountNotFoundException("Account not found");
        }
        acc.withdraw(amount);
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException{

        Account fromAcc = findAccount(fromAccountNumber);
        Account toAcc = findAccount(toAccountNumber);

        if(fromAcc == null){
            throw new AccountNotFoundException("Account not found");
        }

        if(toAcc == null){
            throw new AccountNotFoundException("Account not found");
        }
        
        if(fromAccountNumber == toAccountNumber){
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
    }

}


