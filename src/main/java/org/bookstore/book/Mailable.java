package org.bookstore.book;

// Shippable Interface to provide a common interface for any Mailable( Sent by Email )
// in the future like
// Ebooks

public interface Mailable {
    String getMailingDetails();
}
