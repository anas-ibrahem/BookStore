package org.bookstore.service;

import org.bookstore.book.Mailable;

public class MailService {
    public void mail(Mailable book, String email) {
        System.out.println("Quantum book store - Sending " + book.getMailingDetails() + " to " + email);
    }
}
