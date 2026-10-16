package com.banking.service;

import com.banking.Model.Customer;
import com.banking.Model.Transaction;
import com.banking.Exceptions.DuplicateAccountException;
import com.banking.Exceptions.DuplicateCustomerException;
import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;
import com.banking.Model.Account;
import com.banking.Exceptions.AccountNotFoundException;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import com.banking.Dao.CustomerDAO;
import com.banking.Dao.AccountDAO;
import com.banking.Dao.TransactionDAO;
import com.banking.util.DBConnection;
import com.banking.Enums.TransactionType;

public class Bank {
    
    private List<Account> accounts;
    private List<Customer> customers;

    private Map<Integer, Account> accountsMap;
    private Map<Integer, Customer> customersMap;

    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public Bank(){
        accounts = new ArrayList<>();
        accountsMap = new HashMap<>();

        customers = new ArrayList<>();
        customersMap = new HashMap<>();

        customerDAO = new CustomerDAO();
        accountDAO = new AccountDAO();
        transactionDAO = new TransactionDAO();
    }

    public void createCustomer(Customer customer) throws DuplicateCustomerException{

        if(customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        
        if(customer.getCustomerId() <= 0){
            throw new IllegalArgumentException("CustomerId must be greater than zero");
        }
        
        if(customer.getName() == null || customer.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        
        if(customer.getEmail() == null || customer.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("Customer Email cannot be empty");
        }
        
        if(customersMap.containsKey(customer.getCustomerId())){
            throw new DuplicateCustomerException("Customer ID already exists");
        }


        customers.add(customer);
        customersMap.put(customer.getCustomerId(), customer);
    }

    public void saveCustomer(Customer customer) throws SQLException {
        customerDAO.save(customer);
    }

    public Customer findCustomer(int customerId){
        return customersMap.get(customerId);
    }

    public void createAccount(Account account) throws DuplicateAccountException{

        if(account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

        if(accountsMap.containsKey(account.getAccountNumber())){
            throw new DuplicateAccountException("Account number already exist");
        }

        if(!customersMap.containsKey(account.getCustomer().getCustomerId())){
            throw new IllegalArgumentException("Customer does not exist");
        }

        accounts.add(account);
        accountsMap.put(account.getAccountNumber(), account);
    }

    public void saveAccount(Account account) throws SQLException{
        accountDAO.save(account);
    }

    public void saveTransaction(Account account, Transaction transaction) throws SQLException{
        transactionDAO.save(account,transaction);
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

    public void depositToDatabase(int accountNumber, double amount) throws SQLException, InvalidAmountException, AccountNotFoundException{
        Account account = findAccount(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found"); 
        }

        if(amount <= 0){
            throw new InvalidAmountException("Amount should be greater than zero");
        }

        double oldBalance = account.getBalance();
        double newBalance = oldBalance + amount;

        Connection connection = null;

        try{
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            accountDAO.updateBalance(connection, accountNumber, newBalance);

            Transaction transaction = new Transaction(newTransactionId(), TransactionType.DEPOSIT, amount);

            transactionDAO.save(connection, account, transaction);

            connection.commit();
            account.deposit(amount);
            
        }

        catch(SQLException e){

            if(connection != null){
                connection.rollback();
            }
            throw e;
        }

        finally{

            if(connection != null){
                connection.close();
            }
        }
    }

    public void withdrawToDatabase(int accountNumber, double amount) throws SQLException, InvalidAmountException, AccountNotFoundException, InsufficientBalanceException {
        Account account = findAccount(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account not found");
        }

        if(amount <= 0){
            throw new InvalidAmountException("Amount should be greater than zero");
        }

        
        double oldBalance = account.getBalance();

        if(amount > oldBalance){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        
        double newBalance = oldBalance - amount;

        Connection connection = null;

        try{
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            accountDAO.updateBalance(connection, accountNumber, newBalance);

            Transaction transaction = new Transaction(newTransactionId(), TransactionType.WITHDRAW, amount);

            transactionDAO.save(connection, account, transaction);

            connection.commit();
            account.withdraw(amount);
        }

        catch(SQLException e){

            if(connection != null){
                connection.rollback();
            }
            throw e;
        }

        finally{

            if(connection != null){
                connection.close();
            }
        }
    }

    private void transferToDatabase(int fromAccountNumber, int toAccountNumber, double amount) throws SQLException, InvalidAmountException, AccountNotFoundException, InsufficientBalanceException{
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if(fromAccount == null){
            throw new AccountNotFoundException("Source account not found");
        }

        if(toAccount == null){
            throw new AccountNotFoundException("Destination account not found");
        }

        if(amount <= 0){
            throw new InvalidAmountException("Amount should be greater than zero");
        }

        if(fromAccountNumber == toAccountNumber){
            throw new IllegalArgumentException("cannot transfer to the same account");
        }

        double fromOldBalance = fromAccount.getBalance();

        if(amount > fromOldBalance){
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        double fromNewBalance = fromOldBalance - amount;
        double toNewBalance = toAccount.getBalance() + amount;

        Connection connection = null;

        try{
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            accountDAO.updateBalance(connection, fromAccountNumber, fromNewBalance);

            accountDAO.updateBalance(connection, toAccountNumber, toNewBalance);

            Transaction withdrawTransaction = new Transaction(newTransactionId(), TransactionType.WITHDRAW, amount);

            transactionDAO.save(connection, fromAccount, withdrawTransaction);

            Transaction deposiTransaction = new Transaction(newTransactionId(), TransactionType.DEPOSIT, amount);

            transactionDAO.save(connection, toAccount, deposiTransaction);

            connection.commit();

            fromAccount.withdraw(amount);
            toAccount.deposit(amount);

        }

        catch(SQLException e){

            if(connection != null){
                connection.rollback();
            }
            throw e;
        }

        finally{

            if(connection != null){
                connection.close();
            }
        }
    } 

    private int newTransactionId(){
        return (int) (Math.random() * 900000) + 100000;
    }

}


