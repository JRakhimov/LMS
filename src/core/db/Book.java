package core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Book {
    public void initBookTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE Books(" +
                            "id INT GENERATED ALWAYS AS IDENTITY, " +
                            "isbn INTEGER NOT NULL UNIQUE, " +
                            "title VARCHAR(30) NOT NULL, " +
                            "author VARCHAR(30) NOT NULL, " +
                            "publishDate TIMESTAMP NOT NULL, " +
                            "expiresAt TIMESTAMP, " +
                            "reservedBy INT, " +
                            "borrower INT, " +
                            "PRIMARY KEY (id, isbn), " +
                            "FOREIGN KEY (borrower) REFERENCES Users, " +
                            "FOREIGN KEY (reservedBy) REFERENCES Users)";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public void createBook(int isbn, String title, String author, int publishDate) {
        Database db = Database.getInstance();

        try {
            PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO Book(ISBN, Title, Author, PublishDate) VALUES (?, ?, ?, ?)");
            psInsert.setInt(1, isbn);
            psInsert.setString(2, title);
            psInsert.setString(3, author);
            psInsert.setInt(4, publishDate);

            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("Inserted: " + title );
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

     public void deleteBook(String ISBN){
         Database db = Database.getInstance();

         try {
             Statement st = db.connection.createStatement();
             ResultSet rs = st.executeQuery("DELETE FROM Book WHERE ISBN = " + ISBN);

             rs.close();
             st.close();
         } catch (SQLException e) {
             Database.printSQLException(e);
         }
     }

    public void fetchBooks(int isbn){
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Books WHERE ISBN = " + ISBN);
            rs.close();
            st.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }
}
