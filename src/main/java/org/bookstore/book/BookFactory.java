package org.bookstore.book;

public class BookFactory {
    public static Book create(String type, String ISBN, String title, String author, int year, double price, String fileType, int stock) {
        return switch (type.toLowerCase()) {
            case "paper" -> new PaperBook(ISBN, title, author, year, price, stock);
            case "ebook" -> new Ebook(ISBN, title, author, year, price, fileType);
            case "demo"  -> new DemoBook(ISBN, title, author, year, price);
            default -> throw new IllegalArgumentException("Unsupported book type: " + type);
        };
    }
}
