package core.db.Books;

import core.db.Database;
import core.models.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Books {
    public static void initBooksTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE BOOKS(" +
                            "id INT GENERATED ALWAYS AS IDENTITY UNIQUE, " +
                            "isbn INTEGER NOT NULL UNIQUE, " +
                            "title VARCHAR(30) NOT NULL, " +
                            "author VARCHAR(30) NOT NULL, " +
                            "publishDate TIMESTAMP NOT NULL, " +
                            "expiresAt TIMESTAMP, " +
                            "reservedBy INT, " +
                            "borrower INT, " +
                            "PRIMARY KEY (id, isbn))";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public Book createBook(int isbn, String title, String author, int publishDate, int subject) throws SQLException {
        Database db = Database.getInstance();
        int id = -1;

        String sql = "INSERT INTO BOOKS(ISBN, Title, Author, PublishDate, SUBJECT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psInsert = db.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        psInsert.setInt(1, isbn);
        psInsert.setString(2, title);
        psInsert.setString(3, author);
        psInsert.setInt(4, publishDate);
        psInsert.setInt(5, subject);

        psInsert.executeUpdate();
        ResultSet rs = psInsert.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        psInsert.close();

        if (id < 0) {
            throw new Error("Not created.");
        }

        System.out.println("Inserted: " + title);

        return new Book(id, isbn, title, author, subject, publishDate);
    }

    public void deleteBook(BooksPrimaryKeys key, String value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == BooksPrimaryKeys.ID) {
            primaryKey = "ID";
        } else if (key == BooksPrimaryKeys.ISBN) {
            primaryKey = "ISBN";
        } else {
            throw new Error("Wrong key!");
        }

        String query = "DELETE FROM BOOKS WHERE " + primaryKey + " = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setString(1, value);
        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("Book with " + primaryKey + " " + value + " deleted");
    }

    public Book[] fetchBooks(BooksFetchBy key, String value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == BooksFetchBy.TITLE) {
            primaryKey = "TITLE";
        } else if (key == BooksFetchBy.AUTHOR) {
            primaryKey = "AUTHOR";
        } else {
            throw new Error("Wrong key!");
        }

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM BOOKS");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Book[] books = new Book[count];

        String query = "SELECT * FROM BOOKS WHERE " + primaryKey + " = ?";

        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setString(1, value);
        ResultSet rsBooks = pst.executeQuery();

        if (rsBooks.next()) {
            int id = rsBooks.getInt("id");
            int isbn = rsBooks.getInt("isbn");
            int subject = rsBooks.getInt("subject");
            String title = rsBooks.getString("title");
            String author = rsBooks.getString("author");
            int publishDate = rsBooks.getInt("publishDate");

            books[i] = new Book(id, isbn, title, author, subject, publishDate);

            i++;
        }

        return books;
    }

    public Book[] fetchBooks(BooksFetchBy key, int value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == BooksFetchBy.ID) {
            primaryKey = "ID";
        } else if (key == BooksFetchBy.ISBN) {
            primaryKey = "ISBN";
        }  else if (key == BooksFetchBy.SUBJECT) {
            primaryKey = "SUBJECT";
        } else if (key == BooksFetchBy.PUBLISH_DATE) {
            primaryKey = "PUBLISH_DATE";
        } else {
            throw new Error("Wrong key!");
        }

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM BOOKS");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Book[] books = new Book[count];

        String query = "SELECT * FROM BOOKS WHERE " + primaryKey + " = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setInt(1, value);
        ResultSet rsBooks = pst.executeQuery();

        if (rsBooks.next()) {
            int id = rsBooks.getInt("id");
            int isbn = rsBooks.getInt("isbn");
            int subject = rsBooks.getInt("subject");
            String title = rsBooks.getString("title");
            String author = rsBooks.getString("author");
            int publishDate = rsBooks.getInt("publishDate");

            books[i] = new Book(id, isbn, title, author, subject, publishDate);

            i++;
        }

        return books;
    }

    public Book fetchBook(BooksFetchBy key, String value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == BooksFetchBy.TITLE) {
            primaryKey = "TITLE";
        } else if (key == BooksFetchBy.AUTHOR) {
            primaryKey = "AUTHOR";
        } else {
            throw new Error("Wrong key!");
        }

        String query = "SELECT * FROM BOOKS WHERE " + primaryKey + " = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setString(1, value);
        ResultSet rsBooks = pst.executeQuery();

        rsBooks.next();

        int id = rsBooks.getInt("id");
        int isbn = rsBooks.getInt("isbn");
        int subject = rsBooks.getInt("subject");
        String title = rsBooks.getString("title");
        String author = rsBooks.getString("author");
        int publishDate = rsBooks.getInt("publishDate");

        return new Book(id, isbn, title, author, subject, publishDate);
    }

    public Book fetchBook(BooksFetchBy key, int value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == BooksFetchBy.ID) {
            primaryKey = "ID";
        } else if (key == BooksFetchBy.ISBN) {
            primaryKey = "ISBN";
        }  else if (key == BooksFetchBy.SUBJECT) {
            primaryKey = "SUBJECT";
        } else if (key == BooksFetchBy.PUBLISH_DATE) {
            primaryKey = "PUBLISH_DATE";
        } else {
            throw new Error("Wrong key!");
        }

        String query = "SELECT * FROM BOOKS WHERE " + primaryKey + " = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setInt(1, value);
        ResultSet rsBooks = pst.executeQuery();

        rsBooks.next();

        int id = rsBooks.getInt("id");
        int isbn = rsBooks.getInt("isbn");
        int subject = rsBooks.getInt("subject");
        String title = rsBooks.getString("title");
        String author = rsBooks.getString("author");
        int publishDate = rsBooks.getInt("publishDate");

        return new Book(id, isbn, title, author, subject, publishDate);
    }
}
