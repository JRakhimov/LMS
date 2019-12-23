package core.db.Users;

import core.db.Database;
import core.db.Roles;
import core.models.Administrator;
import core.models.Librarian;
import core.models.Student;
import core.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public int createUser(String login, String password, core.enums.Roles role) throws SQLException {
        Database db = Database.getInstance();
        int roleInt = this.getRoleInt(role);
        int id = -1;

        String sql = "INSERT INTO USERS(login, password, role, fine) VALUES (?, ?, ?, ?)";
        PreparedStatement psInsert = db.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        psInsert.setString(1, login.toUpperCase());
        psInsert.setString(2, password);
        psInsert.setInt(3, roleInt);
        psInsert.setDouble(4, 0.0);

        psInsert.executeUpdate();
        ResultSet rs = psInsert.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        psInsert.close();

        System.out.println("Inserted: " + login);

        return id;
    }

    public void deleteUser(UsersPrimaryKeys key, String value) throws SQLException {
        Database db = Database.getInstance();

        String primaryKey;
        if (key == UsersPrimaryKeys.ID) {
            primaryKey = "ID";
        } else if (key == UsersPrimaryKeys.LOGIN) {
            primaryKey = "LOGIN";
        } else {
            throw new Error("Wrong key!");
        }

        String query = "DELETE FROM USERS WHERE " + primaryKey + " = ?";
        PreparedStatement pst = db.connection.prepareStatement(query);

        if (key == UsersPrimaryKeys.ID) {
            pst.setInt(1, Integer.parseInt(value));
        } else {
            pst.setString(1, value);
        }

        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("User with " + primaryKey + " " + value + " deleted");
    }

    public Administrator[] fetchAdmins() throws SQLException {
        Database db = Database.getInstance();

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM Users WHERE ROLE = 0");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Administrator[] users = new Administrator[count];
        String sql = "SELECT * FROM Users WHERE ROLE = 0";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        ResultSet rsUsers = pst.executeQuery();

        while (rsUsers.next()) {
            int id = rsUsers.getInt("id");
            double fine = rsUsers.getDouble("fine");
            String login = rsUsers.getString("login");
            String password = rsUsers.getString("password");
            boolean isBlocked = rsUsers.getBoolean("isBlocked");

            users[i] = new Administrator(id, login, password);

            i++;
        }

        System.out.println(users[0].toString());

        rsUsers.close();
        pst.close();

        return users;
    }

    public Librarian[] fetchLibrarians() throws SQLException {
        Database db = Database.getInstance();

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM Users WHERE ROLE = 1");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Librarian[] users = new Librarian[count];
        String sql = "SELECT * FROM Users WHERE ROLE = 1";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        ResultSet rsUsers = pst.executeQuery();

        while (rsUsers.next()) {
            int id = rsUsers.getInt("id");
            double fine = rsUsers.getDouble("fine");
            String login = rsUsers.getString("login");
            String password = rsUsers.getString("password");
            boolean isBlocked = rsUsers.getBoolean("isBlocked");

            users[i] = new Librarian(id, login, password);

            i++;
        }

        System.out.println(users[0].toString());

        rsUsers.close();
        pst.close();

        return users;
    }

    public Student[] fetchStudents() throws SQLException {
        Database db = Database.getInstance();

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM Users WHERE ROLE = 2");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        Student[] users = new Student[count];
        String sql = "SELECT * FROM Users WHERE ROLE = 2";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        ResultSet rsUsers = pst.executeQuery();

        while (rsUsers.next()) {
            int id = rsUsers.getInt("id");
            double fine = rsUsers.getDouble("fine");
            String login = rsUsers.getString("login");
            String password = rsUsers.getString("password");
            boolean isBlocked = rsUsers.getBoolean("isBlocked");

            users[i] = new Student(id, login, password, fine, isBlocked);

            i++;
        }

        System.out.println(users[0].toString());

        rsUsers.close();
        pst.close();

        return users;
    }

    public User fetchUser(int id) throws SQLException {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM Users WHERE ID = ?";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        pst.setInt(1, id);
        ResultSet rsUsers = pst.executeQuery();

        rsUsers.next();

        int role = rsUsers.getInt("role");
        double fine = rsUsers.getDouble("fine");
        String login = rsUsers.getString("login");
        String password = rsUsers.getString("password");
        boolean isBlocked = rsUsers.getBoolean("isBlocked");

        rsUsers.close();
        pst.close();

        switch (this.getRoleEnum(role)) {
            case ADMINISTRATOR: {
                return new Administrator(id, login, password);
            }

            case LIBRARIAN: {
                return new Librarian(id, login, password);
            }

            case STUDENT: {
                return new Student(id, login, password, fine, isBlocked);
            }
        }

        throw new Error("Incorrect id.");
    }

    public Object fetchUser(String login, String password) throws SQLException {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM Users WHERE LOGIN = ? AND PASSWORD = ?";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        pst.setString(1, login);
        pst.setString(2, password);
        ResultSet rsUsers = pst.executeQuery();

        rsUsers.next();

        int id = rsUsers.getInt("id");
        int role = rsUsers.getInt("role");
        double fine = rsUsers.getDouble("fine");
        boolean isBlocked = rsUsers.getBoolean("isBlocked");

        rsUsers.close();
        pst.close();

        switch (this.getRoleEnum(role)) {
            case ADMINISTRATOR: {
                return new Administrator(id, login, password);
            }

            case LIBRARIAN: {
                return new Librarian(id, login, password);
            }

            case STUDENT: {
                return new Student(id, login, password, fine, isBlocked);
            }
        }

        throw new Error("Incorrect login.");
    }
}
