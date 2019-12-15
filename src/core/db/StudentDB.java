package core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDB {
    public void initStudentsTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();

            st.execute("create table students(login varchar(8), password varchar(20))");
            st.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void createStudent(String login, String password) {
        Database db = Database.getInstance();

        try {
            PreparedStatement psInsert = db.connection.prepareStatement("insert into students values (?, ?)");
            psInsert.setString(1, login);
            psInsert.setString(2, password);
            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("Inserted: " + login + ", " + password);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void printStudents() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            if (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");

                System.out.println("login: " + login);
                System.out.println("password: " + password);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }
}
