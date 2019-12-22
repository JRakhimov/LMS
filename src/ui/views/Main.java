package ui.views;

import core.db.BorrowedBooks;
import core.db.Database;
import core.models.Book;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
     //  Parent root = FXMLLoader.load(getClass().getResource("AdminPage_students.fxml"));
        primaryStage.setTitle("Library System Management");
        primaryStage.setScene(new Scene(root,1200,800));
//        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }

    public static void main(String[] args) {
//        Database.init("jdbc:derby://localhost:3303/LMS", "root", "root");
//
//        try {
//            BorrowedBooks.createBorrowedBooks(3, 3);
//
//            Book[] books = BorrowedBooks.fetchBorrowedBooksByStudent(3);
//
//            System.out.println(books.length);
//            System.out.println(books[0]);
//        } catch (SQLException e) {
//            Database.printSQLException(e);
//        }

       launch(args);
    }
}
