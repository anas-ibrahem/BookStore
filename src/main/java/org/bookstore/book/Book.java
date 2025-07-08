package org.bookstore.book;

public abstract class Book {
    protected String ISBN;
    protected String title;
    protected String author;
    protected int year;
    protected double price;

    public Book(String ISBN, String title, String author, int year, double price) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }

    public void printInfo(){
        System.out.println("Quantum book store - ISBN: " + ISBN);
        System.out.println("Quantum book store - Title: " + title);
        System.out.println("Quantum book store - Author: " + author);
        System.out.println("Quantum book store - Year: " + year);
        System.out.println("Quantum book store - Price: " + price);
    }

    public String getISBN() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
