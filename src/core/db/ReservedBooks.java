package core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservedBooks {
    public static void initReservedBooksTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE RESERVEDBOOKS(" +
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

    public void createReservedBooks(int student, int book) throws SQLException {
        Database db = Database.getInstance();

        PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO RESERVEDBOOKS(STUDENT, BOOK) VALUES (?, ?)");
        psInsert.setInt(1, student);
        psInsert.setInt(2, book);

        psInsert.executeUpdate();
        psInsert.close();
        System.out.println("Connected: " + student + " <-> " + book + " (RESERVEDBOOKS)");
    }

    public void deleteReservedBooks(int id) throws SQLException {
        Database db = Database.getInstance();

        String query = "DELETE FROM RESERVEDBOOKS WHERE ID = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("Relation with id: " + id + " deleted (RESERVEDBOOKS)");
    }
}
