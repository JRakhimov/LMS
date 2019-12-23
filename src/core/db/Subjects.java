package core.db;
import core.models.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Subjects {
    public static void initSubjectsTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE SUBJECTS(" +
                            "id INT GENERATED ALWAYS AS IDENTITY UNIQUE, " +
                            "title VARCHAR(30) NOT NULL, " +
                            "PRIMARY KEY (id))";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public Subject createSubjects(String title) throws SQLException {
        Database db = Database.getInstance();
        int id = -1;

        PreparedStatement psInsert = db.connection.prepareStatement("INSERT INTO SUBJECTS(TITLE) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        psInsert.setString(1, title);

        psInsert.executeUpdate();
        ResultSet rs = psInsert.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        psInsert.close();
        rs.close();

        System.out.println("Inserted subject with title: " + title);

        return new Subject(id, title);
    }

    public void deleteSubjects(int id) throws SQLException {
        Database db = Database.getInstance();

        String query = "DELETE FROM SUBJECTS WHERE ID = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("Subject with id: " + id + " deleted");
    }

    public Subject[] fetchSubjects() throws SQLException {
        Database db = Database.getInstance();

        Statement countSt = db.connection.createStatement();
        ResultSet rs = countSt.executeQuery("SELECT COUNT(*) AS rowsCount FROM SUBJECTS");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Subject[] subjects = new Subject[count];

        String query = "SELECT * FROM SUBJECTS";
        Statement fetchSt = db.connection.createStatement();
        ResultSet rsSubjects = fetchSt.executeQuery(query);

        while (rsSubjects.next()) {
            int id = rsSubjects.getInt("id");
            String title = rsSubjects.getString("title");

            subjects[i] = new Subject(id, title);

            i++;
        }

        rsSubjects.close();

        return subjects;
    }

    public Subject fetchSubject(int id) {
        try {
            Database db = Database.getInstance();

            String query = "SELECT * FROM SUBJECTS WHERE ID = ?";
            PreparedStatement pst = db.connection.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rsSubjects = pst.executeQuery();

            rsSubjects.next();

            String title = rsSubjects.getString("title");

            rsSubjects.close();

            return new Subject(id, title);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public Subject fetchSubject(String title) {
        try {
            Database db = Database.getInstance();

            String query = "SELECT * FROM SUBJECTS WHERE TITLE = ?";
            PreparedStatement pst = db.connection.prepareStatement(query);
            pst.setString(1, title);
            ResultSet rsSubjects = pst.executeQuery();

            rsSubjects.next();

            int id = rsSubjects.getInt("id");

            rsSubjects.close();

            return new Subject(id, title);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
