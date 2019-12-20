package core.db;

import java.sql.*;
import java.util.Properties;

public class Database {
    private static Database instance = null;

    private String dbEndpoint;
    private String password;
    private String user;
    public Connection connection = null;

    private void connectToDB() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            Properties auth = new Properties();
            auth.put("password", this.password);
            auth.put("user", this.user);

            this.connection = DriverManager.getConnection(this.dbEndpoint, auth);

            System.out.println("Connected to: " + this.dbEndpoint);
        } catch (SQLException e) {
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    protected Database(String dbEndpoint, String user, String password) {
        this.dbEndpoint = dbEndpoint;
        this.password = password;
        this.user = user;

        this.connectToDB();
    }

    public synchronized static void init(String dbEndpoint, String user, String password) {
        if (instance == null) {
            instance = new Database(dbEndpoint, user, password);
        } else {
            System.out.println("Already initialized!");
        }
    }

    public static Database getInstance() {
        if(instance == null) {
            throw new AssertionError("You have to call init first!");
        }

        return instance;
    }

    public static void printSQLException(SQLException e) {
        while (e != null) {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());

            e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    public static boolean tableAlreadyExists(SQLException e) {
        return e.getSQLState().equals("X0Y32");
    }
}
