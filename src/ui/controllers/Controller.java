package ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


public class Controller implements Initializable{


    @FXML
    private Circle myCircle;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentId;





    @Override
    public void initialize(URL url, ResourceBundle rb){
       // myCircle.setStroke(Color.BLACK);
        Image im = new Image("ui/views/icons/bober.jpg" ,false);
        myCircle.setFill(new ImagePattern(im));
        studentName.setText("Here will be name");
        studentId.setText("Here will be ID");

    }




}