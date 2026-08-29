package com.banking.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TransactionTest {
    @Test
    void testGetAmount() {
        
        Transaction acc = new Transaction(1, "DEPOSIT", 100000);

        assertEquals(100000, acc.getAmount());
    }
    
    @Test
    void testGetTransactionId() {
        
        Transaction acc = new Transaction(1, "DEPOSIT", 100000);
        
        assertEquals(1, acc.getTransactionId());
    }
    
    @Test
    void testGetType() {
        
        Transaction acc = new Transaction(1, "DEPOSIT", 100000);
        
        assertEquals("DEPOSIT", acc.getType());
        
    }

    @Test
    void testToString() {
        Transaction acc = new Transaction(1, "DEPOSIT", 100000);
        String result = acc.toString();

        assertEquals("TransactionId:1Type:DEPOSITAmount:100000.0", result);
    }
}
