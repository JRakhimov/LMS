package core.db;

import java.sql.*;

public class Database {
    private Connection db = null;
    private Statement statement = null;

    public Database(String dbURL) {
        this.connectToDB(dbURL);
    }

    // "jdbc:derby:C:/Apache/bin/LMS;user=root;password=root"
    private void connectToDB(String dbURL) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            this.db = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public void execute(String query) {
        try {
            this.statement = this.db.createStatement();
            this.statement.execute(query);
            this.statement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
}
