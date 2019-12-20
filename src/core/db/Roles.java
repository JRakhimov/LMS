package core.db;

import java.sql.SQLException;
import java.sql.Statement;

public class Roles {
    public static void initRolesTable() {
        Database db = Database.getInstance();

        try {
            Statement initStatement = db.connection.createStatement();
            String sql =
                    "CREATE TABLE Roles(" +
                    "id INT NOT NULL, " +
                    "title VARCHAR(15) NOT NULL, " +
                    "PRIMARY KEY (id))";

            initStatement.execute(sql);
            initStatement.close();

            Statement createRolesStatement = db.connection.createStatement();

            createRolesStatement.execute("INSERT INTO ROLES(id, title) VALUES (0, 'Administrator')");
            createRolesStatement.execute("INSERT INTO ROLES(id, title) VALUES (1, 'Librarian')");
            createRolesStatement.execute("INSERT INTO ROLES(id, title) VALUES (2, 'Student')");
            createRolesStatement.close();
        } catch (SQLException e) {
            if (Database.tableAlreadyExists(e)) {
                return;
            }

            Database.printSQLException(e);
        }
    }
}
