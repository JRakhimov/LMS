package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("addBook.fxml"));
        primaryStage.setTitle("Library System Management");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

    }



    public static void main(String[] args) {
//        Database.init("jdbc:derby://localhost:3303/LMS", "root", "root");
//        Database db = Database.getInstance();

//        UserDB udb = new UserDB();

//        udb.initUsersTable();
//        udb.createUser("U1810265", "19980622");
//        udb.printUsers();

       launch(args);
    }
}
