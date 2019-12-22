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

    public User createUser(String login, String password, core.enums.Roles role) throws SQLException {
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

        if (role == core.enums.Roles.ADMINISTRATOR) {
            return new Administrator(id, login, password);
        }

        if (role == core.enums.Roles.LIBRARIAN) {
            return new Librarian(id, login, password);
        }

        if (role == core.enums.Roles.STUDENT) {
            return new Student(id, login, password, 0.0, false);
        }

        throw new Error("Incorrect role.");
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

        pst.setString(1, value);
        ResultSet rs = pst.executeQuery();

        rs.close();
        pst.close();
        System.out.println("User with " + primaryKey + " " + value + " deleted");
    }

    public User[] fetchUsers(core.enums.Roles role) throws SQLException {
        Database db = Database.getInstance();
        int roleInt = this.getRoleInt(role);

        Statement st = db.connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rowsCount FROM Users");

        rs.next();
        int count = rs.getInt(1);
        int i = 0;
        rs.close();

        User[] users = new User[count];
        String sql = "SELECT * FROM Users WHERE ROLE = ?";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        pst.setInt(1, roleInt);
        ResultSet rsUsers = pst.executeQuery();

        while (rsUsers.next()) {
            int id = rsUsers.getInt("id");
            double fine = rsUsers.getDouble("fine");
            String login = rsUsers.getString("login");
            String password = rsUsers.getString("password");
            boolean isBlocked = rsUsers.getBoolean("isBlocked");

            switch (this.getRoleEnum(roleInt)) {
                case ADMINISTRATOR: {
                    users[i] = new Administrator(id, login, password);
                    break;
                }

                case LIBRARIAN: {
                    users[i] = new Librarian(id, login, password);
                    break;
                }

                case STUDENT: {
                    users[i] = new Student(id, login, password, fine, isBlocked);
                    break;
                }
            }

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

    public User fetchUser(String login) throws SQLException {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM Users WHERE LOGIN = ?";
        PreparedStatement pst = db.connection.prepareStatement(sql);

        pst.setString(1, login);
        ResultSet rsUsers = pst.executeQuery();

        rsUsers.next();

        int id = rsUsers.getInt("id");
        int role = rsUsers.getInt("role");
        double fine = rsUsers.getDouble("fine");
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

        throw new Error("Incorrect login.");
    }
}
