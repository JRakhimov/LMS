package core.models;

import core.db.Books.Books;
import core.db.Books.BooksFetchBy;
import core.db.BorrowedBooks;
import core.db.Database;
import core.db.ReservedBooks;
import core.enums.Course;
import core.enums.Department;
import core.enums.Roles;
import org.apache.derby.client.am.SqlException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class Student extends User {
    private Books booksDB = new Books();
    private BorrowedBooks borrowedBooksDB = new BorrowedBooks();
    private ReservedBooks reservedBooksDB = new ReservedBooks();

    private Course course;
    private Department department;

    private Book[] books;
    private Book[] reservedBooks;

    private double fine = 0;
    private boolean isBlocked = false;
    private Roles role = Roles.STUDENT;

    public Student(int id, String login, String password, double fine, Boolean isBlocked) {
        super(id, login, password);
        this.isBlocked = isBlocked;

        // 0 if they are equal
        if (login.charAt(5) == '1') {
            this.department = Department.SOL;
        } else {
            this.department = Department.SOCIE;
        }

        this.course = Student.getCourse(login);

        try {
            this.books = borrowedBooksDB.fetchBorrowedBooksByStudent(id);
            this.reservedBooks = reservedBooksDB.fetchReservedBooksByStudent(id);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public static Course getCourse(String login) {
        int studentYear = Integer.parseInt("20" + login.substring(1, 2));

        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        int currentMonth = localDate.getMonth().getValue();
        int currentYear = localDate.getYear();

        int difference = currentYear - studentYear;

        if (currentMonth <= 6) {
            difference = difference - 1;
        }

        switch (difference) {
            case 0: return Course.FRESHMAN;
            case 1: return Course.SOPHOMORE;
            case 2: return Course.JUNIOR;
            case 3: return Course.SENIOR;
        }

        return Course.NOT_FOUND;
    }

    public Book[] fetchBooks(BooksFetchBy key, String value) throws SQLException {
        return this.booksDB.fetchBooks(key, value);
    }

    public Book[] fetchBooks(BooksFetchBy key, int value) throws SQLException {
        return this.booksDB.fetchBooks(key, value);
    }

    @Override
    public String toString() {
        return "Login: " + this.login + "\nRole: " + this.role;
    }
}
