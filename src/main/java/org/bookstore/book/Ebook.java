package org.bookstore.book;

public class Ebook extends Book implements Mailable {
    private String fileType;

    public Ebook(String ISBN, String title, String author, int year, double price , String fileType) {
        super(ISBN, title, author, year, price);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String getMailingDetails() {
        return "E-book: " + title + " by " + author + " (ISBN: " + ISBN + ", Format: " + fileType + ")";
    }
}
