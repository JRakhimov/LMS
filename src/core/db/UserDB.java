package core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB {
    public void initUsersTable() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE user(id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), login VARCHAR(20) NOT NULL, password VARCHAR(20) NOT NULL, PRIMARY KEY (id, login))";

            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    public void createUser(String login, String password) {
        Database db = Database.getInstance();

        try {
            PreparedStatement psInsert = db.connection.prepareStatement("insert into user values (?, ?)");
            psInsert.setString(1, login);
            psInsert.setString(2, password);
            psInsert.executeUpdate();
            psInsert.close();
            System.out.println("Inserted: " + login + ", " + password);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void printUsers() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

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
