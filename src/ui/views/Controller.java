package ui.views;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;





public class Controller implements Initializable{
    @FXML
    private Button login, addBookBtn,plusBookBtn,backBtn,Admin_logout_btn, nologout, yeslogout;
    @FXML
    private VBox pnItems;

    public void handleLogin(ActionEvent event)throws Exception{
        Stage stage;
        Parent root;
        if(event.getSource()==login){
            stage = (Stage) login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AdminPage_students.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void addBooks(ActionEvent event)throws Exception{
        Stage stage;
        Parent root;
            stage = (Stage) plusBookBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("addBook.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    public void goBack(ActionEvent event)throws Exception{
        Stage stage;
        Parent root;
        stage = (Stage) backBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AdminPage_students.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void logout (ActionEvent event)throws Exception{
        Stage stage;
        Parent root;
        stage = (Stage) Admin_logout_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("logout.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void No (ActionEvent event)throws Exception{
        Stage stage;
        Parent root;
        stage = (Stage) nologout.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AdminPage_students.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Yes (ActionEvent event)throws Exception{
        Parent root;
        Stage stage = (Stage) yeslogout.getScene().getWindow();
        stage.close();
    }




    public void handleStudentsList (ActionEvent event) throws Exception {
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


    public void handleLibrarianList (ActionEvent event) throws Exception {
        Node[] nodes = new Node[15];
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("../views/Item_librarians.fxml"));
                pnItems.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb){
       /* Node[] nodes = new Node[15];
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("Item_students.fxml"));
                pnItems.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }




}