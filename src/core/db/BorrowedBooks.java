package core.db;

import core.models.Book;

import java.sql.*;

public class BorrowedBooks {
    public static void initBorrowedBooksTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE BorrowedBooks(" +
                            "id INT GENERATED ALWAYS AS IDENTITY UNIQUE, " +
                            "student INT NOT NULL, " +
                            "book INT NOT NULL, " +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (student) REFERENCES USERS (ID), " +
                            "FOREIGN KEY (book) REFERENCES BOOKS (ID))";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public void createBorrowedBooks(int student, int book) throws SQLException {
        Database db = Database.getInstance();

        PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO BORROWEDBOOKS(STUDENT, book) VALUES (?,?)");
        psInsert.setInt(1, student);
        psInsert.setInt(2, book);

        psInsert.executeUpdate();
        psInsert.close();
        System.out.println("Connected: " + student + " <-> " + book + " (BORROWEDBOOKS)");
    }

    public Book[] fetchBorrowedBooksByStudent(int student) throws SQLException {
        Database db = Database.getInstance();

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM BORROWEDBOOKS WHERE STUDENT = " + student);

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Book[] books = new Book[count];
        String sql = "SELECT * FROM BOOKS WHERE ID in (SELECT BOOK FROM BORROWEDBOOKS WHERE STUDENT = ?)";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        pst.setInt(1, student);
        ResultSet studentBooks = pst.executeQuery();

        while (studentBooks.next()) {
            int id = studentBooks.getInt("id");
            int isbn = studentBooks.getInt("ISBN");
            int subject = studentBooks.getInt("subject");
            String title = studentBooks.getString("title");
            String author = studentBooks.getString("author");
            Timestamp publishDate = studentBooks.getTimestamp("publishDate");

            books[i] = new Book(id, isbn, title, author, subject, publishDate);

            i++;
        }

        studentBooks.close();
        pst.close();

        return books;
    }

    public void deleteBorrowedBooks(int id) throws SQLException {
        Database db = Database.getInstance();

        String query = "DELETE FROM BORROWEDBOOKS WHERE ID = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("Relation with id: " + id + " deleted (BORROWEDBOOKS)");
    }
}