package core.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BorrowedBooks {
    public void createBorrowedBooks(int ISBN, String ID) {
        Database db = Database.getInstance();

        try {
            PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO BorrowedBooks(ISBN, ID) VALUES (?,?)");
            psInsert.setInt(1, ISBN);
            psInsert.setString(2, ID);
          ;

            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("Inserted: " + ISBN + ", " + ID);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }



}
