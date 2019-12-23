package ui.views;

import core.db.Books.BooksPrimaryKeys;
import core.models.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private HBox listHeader;
    @FXML
    private VBox listContainer;

    private NavState navState = NavState.BOOKS;
    private Administrator admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin = (Administrator) SignedInUser.getUser();
        loadBooks();
    }

    private void loadBooks() {
        try {
            initHeader("header_books.fxml");
            Book[] books = admin.fetchBooks();
            for (Book book : books) {
                Node rowItem = FXMLLoader.load(getClass().getResource("item_books.fxml"));
                initBookItem(rowItem, book);
                listContainer.getChildren().add(rowItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBookItem(Node rowItem, Book book) {
        Label order = (Label) rowItem.lookup("#order");
        Label subject = (Label) rowItem.lookup("#subject");
        Label name = (Label) rowItem.lookup("#name");
        Label author = (Label) rowItem.lookup("#author");
        Label isbn = (Label) rowItem.lookup("#isbn");
        Label publishDate = (Label) rowItem.lookup("#publishDate");
        Label expiryDate = (Label) rowItem.lookup("#expiryDate");
        Button deleteBtn = (Button) rowItem.lookup("#deleteBtn");
        deleteBtn.setOnAction(actionEvent -> {
            //System.out.println("Trying to delete: " + book.getTitle()+", ISBN: " + book.getISBN());
            try {
                admin.deleteBook(BooksPrimaryKeys.ISBN, book.getISBN() + "");
                clearList();
                loadBooks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        order.setText(book.getId() + "");
        subject.setText(book.getSubject());
        name.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN() + "");
        publishDate.setText(book.getPublishDate().toString());
        if (book.getExpiresAt() != null)
            expiryDate.setText(book.getExpiresAt().toString());
    }

    private void loadLibrarians() {
        try {
            initHeader("header_librarians.fxml");
            Librarian[] librarians = admin.fetchLibrarians();
            for (Librarian librarian : librarians) {
                Node rowItem = FXMLLoader.load(getClass().getResource("item_librarians.fxml"));
                initLibrarianItem(rowItem, librarian);
                listContainer.getChildren().add(rowItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLibrarianItem(Node rowItem, Librarian librarian) {
        Label order = (Label) rowItem.lookup("#order");
        Label name = (Label) rowItem.lookup("#name");
        Label login = (Label) rowItem.lookup("#login");
        Label mail = (Label) rowItem.lookup("#mail");
        order.setText(librarian.getId() + "");
        name.setText("Some name");
        login.setText(librarian.getLogin());
        mail.setText("some mail");
    }

    private void loadStudents() {
        try {
            initHeader("header_students.fxml");
            Student[] students = admin.fetchStudents();
            for (Student student : students) {
                Node rowItem = FXMLLoader.load(getClass().getResource("item_students.fxml"));
                initStudentItem(rowItem, student);
                listContainer.getChildren().add(rowItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStudentItem(Node rowItem, Student student) {
        Label order = (Label) rowItem.lookup("#order");
        Label studentName = (Label) rowItem.lookup("#studentName");
        Label studentId = (Label) rowItem.lookup("#studentId");
        Label studentMail = (Label) rowItem.lookup("#studentMail");
        Label studentStage = (Label) rowItem.lookup("#studentStage");
        order.setText(student.getId() + "");
        studentName.setText("Some name");
        studentId.setText(student.getLogin());
        studentMail.setText("some mail");
        studentStage.setText(student.getCourse() + "");
    }

    public void onNavBooksClicked(ActionEvent actionEvent) {
        clearList();
        navState = NavState.BOOKS;
        loadBooks();
    }

    public void onNavLibrariansClicked(ActionEvent actionEvent) {
        clearList();
        navState = NavState.LIBRARIANS;
        loadLibrarians();
    }

    public void onNavStudentsClicked(ActionEvent actionEvent) {
        clearList();
        navState = NavState.STUDENTS;
        loadStudents();
    }

    private void initHeader(String resName) {
        try {
            if (!listHeader.getChildren().isEmpty())
                listHeader.getChildren().remove(0);
            listHeader.getChildren().add(FXMLLoader.load(getClass().getResource(resName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearList() {
        List<Node> childs = new ArrayList<>(listContainer.getChildren());
        listContainer.getChildren().removeAll(childs);
    }

    public void onAddClicked(ActionEvent actionEvent) {
        switch (navState) {
            case BOOKS:
                openPage("addBook.fxml");
                break;
        }
    }

    public void onLogoutClicked(ActionEvent actionEvent) {
        openPage("logout.fxml");
    }

    private void openPage(String resName) {
        try {
            Stage stage = (Stage) listContainer.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(resName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
