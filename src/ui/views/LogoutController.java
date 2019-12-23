package ui.views;

import core.models.SignedInUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoutController implements Initializable {

    @FXML
    private Button yeslogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onLogoutClicked(ActionEvent actionEvent) {
        if (openPage("loginPage.fxml"))
            SignedInUser.setUser(null);
    }

    public void onCancelClicked(ActionEvent actionEvent) {
        openPage("AdminPage.fxml");
    }

    private boolean openPage(String resName) {
        try {
            Stage stage = (Stage) yeslogout.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(resName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
