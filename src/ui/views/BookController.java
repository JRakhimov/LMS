package ui.views;

import core.db.Database;
import core.models.Administrator;
import core.models.Book;
import core.models.Librarian;
import core.models.SignedInUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private TextField titleInput, authorInput, subjectInput, publishDateInput, isbnInput;

    private Administrator admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin = (Administrator) SignedInUser.getUser();
    }

    public void onBackClicked(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) container.getScene().getWindow();
            String resName = "AdminPage.fxml";
            if (SignedInUser.getUser() instanceof Librarian)
                resName = ""; // TODO change to librarin Main Menu
            Parent root = FXMLLoader.load(getClass().getResource(resName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBookAddClicked(ActionEvent actionEvent) {
        String title = titleInput.getText();
        String author = authorInput.getText();
        String subject = subjectInput.getText();
        String publishDate = publishDateInput.getText();
        Timestamp timestamp = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
        try {
            Date parsedDate = dateFormat.parse(publishDate);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String isbn = isbnInput.getText();

        try {
            Book book = admin.createBook(Integer.parseInt(isbn), title, author, timestamp, subject);
            if (book != null)
                onBackClicked(actionEvent);
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

    }
}
