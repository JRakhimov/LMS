package core.models;

import core.db.Subjects;

import java.sql.Timestamp;

public class Book {
    private int id;
    private int ISBN;
    private String title;
    private String author;
    private String subject;
    private Timestamp publishDate;

    private Timestamp expiresAt;
    private Student borrower;
    private Student reservedBy;

    public Book(int id, int isbn, String title, String author, int subject, Timestamp publishDate, Timestamp expiresAt) {
        this.id = id;
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = new Subjects().fetchSubject(subject).title;
        this.publishDate = publishDate;

        this.expiresAt = expiresAt;
    }

    public Book(int id, int isbn, String title, String author, int subject, Timestamp publishDate) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = new Subjects().fetchSubject(subject).title;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() { return "Title: " + this.title; }

    public int getId() {
        return id;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public Student getBorrower() {
        return borrower;
    }

    public Student getReservedBy() {
        return reservedBy;
    }
}
