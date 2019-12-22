package ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;





public class Controller implements Initializable{
    @FXML
    private Button login, addBookBtn;



    public void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        if(event.getSource()==login){
            stage = (Stage) login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../addBook.fxml"));
        }
        else{
            stage = (Stage) addBookBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle rb){


    }




}