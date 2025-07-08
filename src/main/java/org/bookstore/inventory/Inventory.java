package org.bookstore.inventory;

import org.bookstore.book.*;
import org.bookstore.service.MailService;
import org.bookstore.service.ShippingService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Inventory {
    private HashMap<String, Book> inventory = new HashMap<String, Book>();
    private MailService mailService = new MailService();
    private ShippingService shippingService = new ShippingService();

    /**
     * Adds a new book to the inventory.
     * @param type      Book type: "paper", "ebook", or "demo"
     * @param ISBN      Unique book identifier
     * @param title     Book title
     * @param author    Book author
     * @param year      Year of publication
     * @param price     Book price
     * @param fileType  Only for ebooks (e.g., "pdf"), null for others
     * @param stock     Only for paper books, 0 for others
     */
    public void addNewBook(String type, String ISBN, String title, String author, int year, double price, String fileType, int stock) {
        if (inventory.containsKey(ISBN)) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = BookFactory.create(type, ISBN, title, author, year, price, fileType, stock);
        inventory.put(ISBN, book);
        System.out.println("Quantum book store - Added book: " + ISBN);
    }

    /**
     * Increases stock quantity for a book.
     * @param ISBN      Target book's ISBN
     * @param quantity  Quantity to add
     */
    public void addQuantity(String ISBN, int quantity) {
        Book entry = inventory.get(ISBN);
        if (entry instanceof Shippable) {
            Shippable p = (Shippable) entry;
            p.addStock(quantity);
            System.out.println("Quantum book store - Added " + quantity + " to " + ISBN + " (New quantity: " + p.getStock() + ")");
        }
        else
            throw new IllegalArgumentException("ISBN does not exist or not a paper book");
    }

    /**
     * Removes a book by ISBN.
     * @param ISBN      Unique identifier to remove
     */
    public void removeBook(String ISBN) {
        Book removed = inventory.remove(ISBN);
        if (removed != null)
            System.out.println("Quantum book store - Removed book: " + ISBN);
        else
            System.out.println("Quantum book store - Book not found: " + ISBN);
    }

    /**
     * Decreases stock quantity for a paper book.
     * @param ISBN      Target book's ISBN
     * @param quantity  Quantity to reduce
     */
    public void reduceQuantity(String ISBN, int quantity) {
        Book entry = inventory.get(ISBN);
        if (entry instanceof Shippable) {
            Shippable p = (Shippable) entry;
            p.reduceStock(quantity);
            System.out.println("Quantum book store - Reduced " + quantity + " of " + ISBN + " (New quantity: " + p.getStock() + ")");
        }
        else
            throw new IllegalArgumentException("ISBN does not exist or not a paper book");
    }

    /**
     * Removes all books that are older than the given max age.
     * @param maxAge Maximum age (in years) a book is allowed to remain in inventory
     */
    public void removeOutdatedBooks(int maxAge) {
        int currentYear = LocalDate.now().getYear();
        Iterator<Map.Entry<String, Book>> iterator = inventory.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Book> entry = iterator.next();
            int bookYear = entry.getValue().getYear();

            if ((currentYear - bookYear) > maxAge) {
                System.out.println("Quantum book store - Removed outdated book: " + entry.getKey());
                iterator.remove();
            }
        }
    }

    /**
    /**
     * Searches for a book by ISBN and prints all details.
     * @param ISBN The ISBN of the book to search
     */
    public void searchBook(String ISBN) {
        Book entry = inventory.get(ISBN);
        if (entry == null) {
            System.out.println("Quantum book store - No book found with ISBN: " + ISBN);
            return;
        }

        entry.printInfo();
        if (entry instanceof Shippable) {
            Shippable p = (Shippable) entry;
            System.out.println("Quantum book store - Stock: " + p.getStock());
        }
    }


    public Book getBook(String isbn) {
        if (inventory.containsKey(isbn))
            return inventory.get(isbn);
        else
            throw new IllegalArgumentException("Book not found");
    }

    /**
     * Buy a book from the inventory
     * @param ISBN The ISBN of the book to buy
     * @param quantity The quantity to buy
     * @param email Customer email for ebooks
     * @param address Customer address for paper books
     * @return The total amount paid
     */
    public double buyBook(String ISBN, int quantity, String email, String address) {
        Book book = getBook(ISBN);
        
        if (book instanceof DemoBook) {
            throw new IllegalArgumentException("Demo books are not for sale");
        }
        
        double totalAmount = book.getPrice() * quantity;
        
        if (book instanceof Shippable) {
            Shippable s = (Shippable) book;
            s.reduceStock(quantity);
            System.out.println("Quantum book store - Purchased " + quantity + " copy(ies) of " + book.getTitle());
            
            if (book instanceof Shippable) {
                shippingService.ship((Shippable) book, address);
            }
        } else if (book instanceof Ebook) {
            System.out.println("Quantum book store - Purchased " + quantity + " copy(ies) of " + book.getTitle());
            
            if (book instanceof Mailable) {
                for (int i = 0; i < quantity; i++) {
                    mailService.mail((Mailable) book, email);
                }
            }
        }
        
        System.out.println("Quantum book store - Total amount paid: $" + totalAmount);
        return totalAmount;
    }
}
