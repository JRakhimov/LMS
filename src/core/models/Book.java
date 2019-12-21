package core.models;

public class Book {
    private int id;
    private int ISBN;
    private String title;
    private String author;
    private int subject;
    private int publishDate;

    private int expiresAt;
    private Student borrower;
    private Student reservedBy;

    public Book(int id, int isbn, String title, String author, int subject, int publishDate, int expiresAt) {
        this.id = id;
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;

        this.expiresAt = expiresAt;
    }

    public Book(int id, int isbn, String title, String author, int subject, int publishDate) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;
    }
}
