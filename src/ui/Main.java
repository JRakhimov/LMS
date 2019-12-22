package ui;

import core.db.Database;
import core.db.Users.Users;
import core.enums.Roles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Database.init("jdbc:derby://localhost:3303/LMS", "root", "root");
        Database db = Database.getInstance();

        Users udb = new Users();

//        Roles.initRolesTable();
//        Users.initUsersTable();

//        udb.createUser("U1810264", "123abc123", core.enums.Roles.STUDENT);
//        udb.deleteUser("u1810265");
//        udb.fetchUsers(Roles.STUDENT);

       launch(args);
    }
}
