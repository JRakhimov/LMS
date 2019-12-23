package ui.views;
import core.db.Users.Users;
import core.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller implements Initializable{
    @FXML
    private Button login, addBookBtn;
    @FXML
    private TextField loginInput, passInput;


    public void handleLogin(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        String passText = passInput.getText();
        String loginText = loginInput.getText();
        Users users = new Users();
        Object user = users.fetchUser(loginText, passText);
        String resName = "AdminPage.fxml";//this
        SignedInUser.setUser(user);
        if (user instanceof Librarian)
            resName = "AdminPage.fxml";
        else if (user instanceof Student)
            resName = "AdminPage.fxml";

        if(event.getSource() == login) {
            stage = (Stage) login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminPage.fxml")); //TODO change to normal data
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /*public void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        if(event.getSource()==addBookBtn){
            stage = (Stage) addBookBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }// a eto chto za method?  a manimca test qgan wunda kk
    }*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //System.out.println("initialize: " + url.toString());
    }
}