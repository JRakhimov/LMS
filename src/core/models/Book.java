package core.models;

import java.sql.Timestamp;

public class Book {
    private int id;
    private int ISBN;
    private String title;
    private String author;
    private int subject;
    private Timestamp publishDate;

    private Timestamp expiresAt;
    private Student borrower;
    private Student reservedBy;

    public Book(int id, int isbn, String title, String author, int subject, Timestamp publishDate, Timestamp expiresAt) {
        this.id = id;
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;

        this.expiresAt = expiresAt;
    }

    public Book(int id, int isbn, String title, String author, int subject, Timestamp publishDate) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() { return "Title: " + this.title; }
}
