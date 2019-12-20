package core.db;

import core.models.Administrator;
import core.models.Librarian;
import core.models.Student;
import core.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
CREATE TABLE BorrowedBooks
(
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
studentID VARCHAR(50) NOT NULL,
bookID VARCHAR(50) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (studentID) REFERENCES Users
FOREIGN KEY (bookID) REFERENCES Books
);
* */

public class Users {
    public static void initUsersTable() {
        Database db = Database.getInstance();
        Roles.initRolesTable();

        try {
            Statement st = db.connection.createStatement();
            String sql =
                    "CREATE TABLE Users(" +
                            "id INT GENERATED ALWAYS AS IDENTITY, " +
                            "login VARCHAR(20) NOT NULL UNIQUE, " +
                            "password VARCHAR(20) NOT NULL, " +
                            "role int NOT NULL, " +
                            "fine DOUBLE DEFAULT 0.0, " +
                            "PRIMARY KEY (id, login), " +
                            "FOREIGN KEY (role) REFERENCES Roles)";


            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }

    private int getRoleInt(core.enums.Roles role) {
        switch (role) {
            case ADMINISTRATOR: {
                return 0;
            }

            case LIBRARIAN: {
                return 1;
            }

            case STUDENT: {
                return  2;
            }

            default: {
                throw new Error("Wrong role enum!");
            }
        }
    }
    private core.enums.Roles getRoleEnum(int role) {
        switch (role) {
            case 0: {
                return core.enums.Roles.ADMINISTRATOR;
            }

            case 1: {
                return core.enums.Roles.LIBRARIAN;
            }

            case 2: {
                return core.enums.Roles.STUDENT;
            }

            default: {
                throw new Error("Wrong role int!");
            }
        }
    }

    public void createUser(String login, String password, core.enums.Roles role) {
        Database db = Database.getInstance();

        try {
            int roleInt = this.getRoleInt(role);

            String sql = "INSERT INTO USERS(login, password, role, fine) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = db.connection.prepareStatement(sql);

            psInsert.setString(1, login.toUpperCase());
            psInsert.setString(2, password);
            psInsert.setInt(3, roleInt);
            psInsert.setDouble(4, 0.0);
            psInsert.executeUpdate();
            psInsert.close();

            System.out.println("Inserted: " + login + ", " + password);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void deleteUser(int id) {
        Database db = Database.getInstance();

        try {
            String sql = "DELETE FROM Users WHERE id = " + id;
            Statement st = db.connection.createStatement();

            int number = st.executeUpdate(sql);

            System.out.println("Number of deleted rows: " + number);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void deleteUser(String login) {
        Database db = Database.getInstance();

        try {
            String sql = "DELETE FROM Users WHERE login = ?";
            PreparedStatement pst = db.connection.prepareStatement(sql);

            pst.setString(1, login.toUpperCase());
            int number = pst.executeUpdate();

            System.out.println("Number of deleted rows: " + number);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void fetchUsers(core.enums.Roles role) {
        Database db = Database.getInstance();
        User[] users;

        try {
            int roleInt = this.getRoleInt(role);

            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM Users");

            rs.next();
            int count = rs.getInt(1);
            int i = 0;
            rs.close();

            users = new User[count];

            String sql = "SELECT * FROM Users WHERE ROLE = ?";
            PreparedStatement pst = db.connection.prepareStatement(sql);

            pst.setInt(1, roleInt);
            ResultSet rsUsers = pst.executeQuery();

            if (rsUsers.next()) {
                String login = rsUsers.getString("login");
                String password = rsUsers.getString("password");

                switch (this.getRoleEnum(roleInt)) {
                    case ADMINISTRATOR: {
                        users[i] = new Administrator();
                        break;
                    }

                    case LIBRARIAN: {
                        users[i] = new Librarian();
                        break;
                    }

                    case STUDENT: {
                        users[i] = new Student(login, password);
                        break;
                    }
                }

                i++;
            }

            System.out.println(users[0].toString());

            rsUsers.close();
            pst.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void printUsers() {
        Database db = Database.getInstance();

        try {
            Statement st = db.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Users");

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
