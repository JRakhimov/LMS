package core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDB {
    public void initBookTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE Book(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), ISBN INTEGER NOT NULL UNIQUE, Title VARCHAR(30) NOT NULL, Author VARCHAR(50) NOT NULL, Subject VARCHAR (50) NOT NULL, PublishDate INTEGER NOT NULL, ExpiresAt INTEGER NOT NULL, PRIMARY KEY (id, isbn))";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public void createBook(int ISBN, String Title, String Author, String Subject, int PublishDate, int ExpiresAt) {
        Database db = Database.getInstance();

        try {
            PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO Book(ISBN, Title,Author, Subject, PublishDate,ExpiresDate) VALUES (?,?,?,?,?,?)");
            psInsert.setInt(1, ISBN);
            psInsert.setString(2, Title);
            psInsert.setString(3, Author);
            psInsert.setString(4, Subject);
            psInsert.setInt(5, PublishDate);
            psInsert.setInt(6, ExpiresAt);

            psInsert.executeUpdate();
            psInsert.close();
            //System.out.println("Inserted: " + login + ", " + password);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void printBook(int isbn, String title, String author, String subject, int publishDate, int expiresAt) {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Book");

            if (rs.next()) {

                 int ISBN = rs.getInt("ISBN");
                 String Title = rs.getString("Title");
                 String Author = rs.getString("Author");
                 String Subject = rs.getString("Subject");
                 int PublishDate = rs.getInt("PublishDate");
                 int ExpiresAt = rs.getInt("ExpiresAt");

                System.out.println("ISBN: " + ISBN);
                System.out.println("Title: " + Title);
                System.out.println("Author:" + Author);
                System.out.println("Subject" + Subject);
                System.out.println("PublishDate" + PublishDate);
                System.out.println("ExpiresAt" + ExpiresAt);
            }

            rs.close();
            st.close();
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
 public void fetchBookBy(String ID){
     Database db = Database.getInstance();

     try {
         Statement st = db.connection.createStatement();
         String sql =("SELECT * FROM Book WHERE ID = " + ID);
         rs.close();
         st.close();
     } catch (SQLException e) {
         Database.printSQLException(e);
     }
 }
}
    public void fetchBookBy(int ISBN){
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT *FROM Book WHERE ISBN = " + ISBN);
            rs.close();
            st.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }
}
