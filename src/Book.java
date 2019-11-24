public class Book {
    private int ISBN;
    private int title;
    private String author;
    private String subject;
    private int publishDate;

    private int expiresAt;
//    private Student borrower;
//    private Student reservedBy;

    Book(int isbn, int title, String author, String subject, int publishDate, int expiresAt) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;

        this.expiresAt = expiresAt;
    }

    Book(int isbn, int title, String author, String subject, int publishDate) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publishDate = publishDate;
    }
}
