package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class MainMenuController {
    @FXML
    private Button createGameButton;

    @FXML
    public void handleCreateGameButtonAction(ActionEvent actionEvent) {
        createGameButton.setText("Test");
    }
}
