package ui.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Controller implements Initializable {

        @FXML
        private Circle iCircle;

        @FXML
        private Button Admin_books_btn;

        @FXML
        private Button Admin_librarians_btn;

        @FXML
        private Button Admin_students_btn;

        @FXML
        private Button Admin_borrowed_books_btn;

        @FXML
        private Button Admin_overdue_books_btn;

        @FXML
        private Button Admin_report_btn;

        @FXML
        private Button Admin_logout_btn;

        @FXML
        private Button Admin_search_btn;

        @FXML
        private Button Admin_filter_btn;

        @FXML
        private Button Admin_add_books_btn;


    @FXML
    private Label label;

    @FXML
    private VBox pnItems = null;

    @FXML
    private Label num1;

    @FXML
    private Label num2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
            iCircle.setStroke(Color.BLACK);
            Image im = new Image("ui/views/images/photo_2019-11-23_18-47-23.jpg", false);
            iCircle.setFill(new ImagePattern(im));

            Node[] nodes = new Node[15];
            for (int i = 0; i < nodes.length; i++) {
                try {
                    nodes[i] = FXMLLoader.load(getClass().getResource("../views/Item_students.fxml"));
                    pnItems.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
}
