package org.bookstore.service;

import org.bookstore.book.Shippable;

public class ShippingService {
    public void ship(Shippable book, String address) {
        System.out.println("Quantum book store - Shipping " + book.getShippingDetails() + " to " + address);
    }
}