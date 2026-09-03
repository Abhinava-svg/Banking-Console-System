package com.banking.Exceptions;

public class DuplicateAccountException extends Exception{
    public DuplicateAccountException(String message){
        super(message);
    }    
}
