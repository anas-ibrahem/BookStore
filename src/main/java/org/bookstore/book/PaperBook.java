package org.bookstore.book;

public class PaperBook extends Book implements Shippable {
    private int stock;

    public PaperBook(String ISBN, String title, String author, int year, double price , int stock) {
        super(ISBN, title, author, year, price);
        this.stock = stock;
    }


    public int getStock() {
        return stock;
    }

    public void addStock(int q) {
        stock += q;
    }

    public void reduceStock(int q) {
        if (stock < q)
            throw new IllegalArgumentException("Not enough stock");
        stock -= q;
    }

    @Override
    public String getShippingDetails() {
        return "Paper book: " + title + " by " + author + " (ISBN: " + ISBN + ")";
    }
}
