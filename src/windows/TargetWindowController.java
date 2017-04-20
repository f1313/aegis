package windows;

import MiddleMan.TargetSpec;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;


public class TargetWindowController {

    public TargetWindowController() {
    }

    TargetSpec TargetObject = new TargetSpec();
    DirectoryChooser dirChooser = new DirectoryChooser();
    Stage chooser = new Stage();
    @FXML
    ListView ipList;
    @FXML
    TextField ipText;
    @FXML
    Button editButton;
    @FXML
    Label errorLabel;
    @FXML
    Button startButton;
    @FXML
    TextField fileText;
    @FXML
    TextField groupName;

    @FXML
    private void initialize() {
        editButton.setText("\u270E");

        ipText.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                ipText.setStyle("-fx-background-color: white;");
                errorLabel.setText("");
                if (event.getCode() == KeyCode.ENTER) {
                    addIP();
                }
            }
        });

        fileText.setText(System.getProperty("user.home"));
    }

    //Adding an ip to the list.
    @FXML
    private void addIP() {
        String toAdd = ipText.getText();

        if (toAdd.length() <= 0) {
            ipText.setStyle("-fx-background-color: #ff9494;");
            errorLabel.setText("Empty Target");
            return;
        } else if (ipList.getItems().contains(toAdd)) {
            ipText.setStyle("-fx-background-color: #ff9494;");
            errorLabel.setText("Duplicate Target");
            return;
        } else if (!TargetObject.validateHostString(toAdd)) {
            errorLabel.setText("Invalid Target Format");
            ipText.setStyle("-fx-background-color: #ff9494;");
            return;
        } else {
            ipList.getItems().add(toAdd);
            ipText.setText("");
            errorLabel.setText("");
            ipText.setStyle("-fx-background-color: white;");
        }
    }

    //Removing an ip from the list
    /*
     WARNING : Error when removing the first element from the list.
    */
    @FXML
    private void removeButton() {
        if (ipList.getSelectionModel().getSelectedIndex() != -1) {
            ipList.getItems().remove(ipList.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void edit() {
        if (ipList.getSelectionModel().getSelectedIndex() != -1) {
            String temp = (String) ipList.getSelectionModel().getSelectedItem();
            ipText.setText(temp);
            ipList.getItems().remove(ipList.getSelectionModel().getSelectedIndex());
            ipText.requestFocus();
        }
    }


    @FXML
    private void save() {
        dirChooser.setTitle("Choose Location");
        File temp = dirChooser.showDialog(chooser);
        if (temp != null && temp.getAbsoluteFile() != null && temp.getAbsolutePath().length() != 0) {
            fileText.setText(temp.getAbsolutePath());
        }
    }

    @FXML
    private void start() {
        if (ipList.getItems().size() == 0) {
            errorLabel.setText("Group can't be empty");
            ipText.setStyle("-fx-background-color: #ff9494;");
        } else {
            AegisMainWindowController.groupClosed = true;
            Stage stage = (Stage) ipText.getScene().getWindow();
            stage.close();
        }
    }


    public TextField getFileText() {
        return fileText;
    }

    public ListView getIpList() {
        return ipList;
    }

    public Button getStartButton() {
        return startButton;
    }

    public String getGroupName() {
        return groupName.getText();
    }

}
