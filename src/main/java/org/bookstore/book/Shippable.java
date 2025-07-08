package org.bookstore.book;

// Shippable Interface to provide a common interface for any Quantable
// and Shippable Books in the future like
// PaperBooks

public interface Shippable {
    String getShippingDetails();
    int getStock();
    void reduceStock(int quantity);
    void addStock(int quantity);
}
