package core.models;

import core.db.AddedBooks;
import core.db.Books.Books;
import core.db.Books.BooksFetchBy;
import core.db.Books.BooksPrimaryKeys;
import core.db.Database;
import core.db.Users.Users;
import core.db.Users.UsersPrimaryKeys;
import core.enums.Roles;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Administrator extends User {
    private Book[] addedBooks;
    private Roles role = Roles.ADMINISTRATOR;
    private Books booksDB = new Books();
    private Users usersDB = new Users();
    private AddedBooks addedBooksDB = new AddedBooks();

    public Administrator(int id, String login, String password) {
        super(id, login, password);

        try {
            this.addedBooks = this.addedBooksDB.fetchAddedBooksByUser(id);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public Librarian createLibrarian(String login, String password) throws SQLException {
        int id = this.usersDB.createUser(login, password, Roles.LIBRARIAN);

        return new Librarian(id, login, password);
    }

    public Librarian[] fetchLibrarians() throws SQLException {
        return this.usersDB.fetchLibrarians();
    }

    public Student createStudent(String login, String password) throws SQLException {
        int id = this.usersDB.createUser(login, password, Roles.STUDENT);

        return new Student(id, login, password, 0.0, false);
    }

    public Student[] fetchStudents() throws SQLException {
        return this.usersDB.fetchStudents();
    }

    public void deleteUser(UsersPrimaryKeys key, String value) throws SQLException {
        this.usersDB.deleteUser(key, value);
    }

    public Book createBook(int isbn, String title, String author, Timestamp publishDate, int subject) throws SQLException {
        return this.booksDB.createBook(isbn, title, author, publishDate, subject);
    }

    public Book[] fetchBooks(BooksFetchBy key, String value) throws SQLException {
        return this.booksDB.fetchBooks(key, value);
    }

    public Book[] fetchBooks(BooksFetchBy key, int value) throws SQLException {
        return this.booksDB.fetchBooks(key, value);
    }

    public void deleteBook(BooksPrimaryKeys key, String value) throws SQLException {
        this.booksDB.deleteBook(key, value);
    }
}
