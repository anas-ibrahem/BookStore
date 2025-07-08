package org.bookstore;

import org.bookstore.inventory.Inventory;
import org.bookstore.book.*;

import java.util.Scanner;


// Tester Class with 2 modes Manual and Automated
public class Tester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Quantum Book Store Tester ===");
        System.out.println("Select mode:");
        System.out.println("1. Manual Mode (interactive)");
        System.out.println("2. Automated Test Cases");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            manualMode(scanner);
        } else {
            automatedMode();
        }
    }

    private static void manualMode(Scanner scanner) {
        Inventory inventory = new Inventory();
        System.out.println("\n--- Manual Mode ---");
        System.out.println("You can test the system interactively.");

        while (true) {
            System.out.println("Available commands:");
            System.out.println("1. Add new book");
            System.out.println("2. Add stock to paper book");
            System.out.println("3. Search for book");
            System.out.println("4. Buy book");
            System.out.println("5. Remove book");
            System.out.println("6. Remove outdated books");
            System.out.println("7. Exit");
            System.out.print("\nEnter command number: ");
            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "1" -> {
                    System.out.print("Enter type (paper/ebook/demo): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    String fileType = null;
                    int stock = 0;
                    if (type.equalsIgnoreCase("ebook")) {
                        System.out.print("Enter file type (e.g., pdf): ");
                        fileType = scanner.nextLine();
                    }
                    if (type.equalsIgnoreCase("paper")) {
                        System.out.print("Enter stock: ");
                        stock = Integer.parseInt(scanner.nextLine());
                    }
                    try {
                        inventory.addNewBook(type, isbn, title, author, year, price, fileType, stock);
                        inventory.searchBook(isbn);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "2" -> {
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter quantity to add: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    try {
                        inventory.addQuantity(isbn, qty);
                        inventory.searchBook(isbn);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    inventory.searchBook(isbn);
                }
                case "4" -> {
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter your address: ");
                    String address = scanner.nextLine();
                    try {
                        inventory.buyBook(isbn, qty, email, address);
                        inventory.searchBook(isbn);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.print("Enter ISBN to remove: ");
                    String isbn = scanner.nextLine();
                    inventory.removeBook(isbn);
                }
                case "6" -> {
                    System.out.print("Remove books older than how many years? ");
                    int years = Integer.parseInt(scanner.nextLine());
                    inventory.removeOutdatedBooks(years);
                }
                case "7" -> {
                    System.out.println("Exiting manual mode.");
                    return;
                }
                default -> System.out.println("Unknown command. Please enter a number from 1 to 7.");
            }
        }
    }

    private static void automatedMode() {
        System.out.println("\n--- Automated Test Cases ---");
        Inventory inventory = new Inventory();

        System.out.println("\n[TEST] Adding books...");
        inventory.addNewBook("paper", "PB001", "The Great Gatsby", "F. Scott Fitzgerald", 1925, 15.99, null, 10);
        inventory.addNewBook("ebook", "EB001", "Digital Fortress", "Dan Brown", 1998, 12.99, "pdf", 0);
        inventory.addNewBook("demo", "DB001", "Sample Book", "John Doe", 2023, 0.0, null, 0);
        inventory.addNewBook("paper", "PB002", "1984", "George Orwell", 1949, 13.50, null, 5);
        inventory.addNewBook("ebook", "EB002", "The Pragmatic Programmer", "Andy Hunt", 1999, 25.99, "epub", 0);
        inventory.addNewBook("paper", "PB003", "Old Book", "Ancient Author", 2000, 5.99, null, 3);

        System.out.println("\n[TEST] Adding stock...");
        inventory.addQuantity("PB001", 5);
        inventory.addQuantity("PB002", 10);

        System.out.println("\n[TEST] Searching for books...");
        inventory.searchBook("PB001");
        inventory.searchBook("EB001");
        inventory.searchBook("DB001");
        inventory.searchBook("INVALID");

        System.out.println("\n[TEST] Buying paper book...");
        try {
            inventory.buyBook("PB001", 2, "customer@email.com", "123 Main St, City");
            inventory.searchBook("PB001");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n[TEST] Buying ebook...");
        try {
            inventory.buyBook("EB001", 1, "customer@email.com", "123 Main St, City");
            inventory.searchBook("EB001");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n[TEST] Buying demo book (should fail)...");
        try {
            inventory.buyBook("DB001", 1, "customer@email.com", "123 Main St, City");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n[TEST] Buying more than available stock...");
        try {
            inventory.buyBook("PB002", 100, "customer@email.com", "123 Main St, City");
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n[TEST] Removing outdated books (older than 20 years)...");
        inventory.removeOutdatedBooks(20);

        System.out.println("\n[TEST] Removing specific books...");
        inventory.removeBook("DB001");
        inventory.removeBook("NONEXISTENT");

        System.out.println("\n[TEST] Interface implementation verification...");
        PaperBook paperBook = new PaperBook("TEST001", "Test Paper", "Test Author", 2023, 10.99, 5);
        if (paperBook instanceof Shippable) {
            Shippable shippableBook = paperBook;
            System.out.println("Shippable details: " + shippableBook.getShippingDetails());
        }
        Ebook ebook = new Ebook("TEST002", "Test Ebook", "Test Author", 2023, 8.99, "pdf");
        if (ebook instanceof Mailable) {
            Mailable mailableBook = ebook;
            System.out.println("Mailable details: " + mailableBook.getMailingDetails());
        }
        DemoBook demoBook = new DemoBook("TEST003", "Test Demo", "Test Author", 2023, 0.0);
        System.out.println("Demo book is Shippable: " + (demoBook instanceof Shippable));
        System.out.println("Demo book is Mailable: " + (demoBook instanceof Mailable));

        System.out.println("\n--- Automated Test Cases Completed ---");
    }
}
