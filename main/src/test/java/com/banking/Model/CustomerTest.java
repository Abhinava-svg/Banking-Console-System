package com.banking.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    void testGetCustomerId() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        assertEquals(101, obj.getCustomerId());
    }

    @Test
    void testGetEmail() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        assertEquals("rahul@gmail.com", obj.getEmail());
    }

    @Test
    void testGetName() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        assertEquals("Rahul", obj.getName());
    }

    @Test
    void testSetEmail() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        obj.setEmail("priya@gmail.com");

        assertEquals("priya@gmail.com", obj.getEmail());
    }

    @Test
    void testSetName() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        obj.setName("Priya");

        assertEquals("Priya", obj.getName());
    }

    @Test
    void testToString() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        String result = obj.toString();
        assertEquals("customerId :101name :Rahulemail :rahul@gmail.com", result);

    }
}
