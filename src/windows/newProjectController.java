package windows;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by wintson on 4/16/17.
 */
public class newProjectController {
    Stage chooserStage;
    @FXML
    TextField locationText;
    @FXML
    TextField projectText;
    @FXML
    Button locationButton;
    @FXML
    Button newButton;
    @FXML
    Label errorLabel;

    String groupName = "";

    @FXML
    private void initialize(){
        projectText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                projectText.setStyle("-fx-background-color: white;");
                errorLabel.setText("");
                if (event.getCode() == KeyCode.ENTER) create();
            }
        });
        locationText.setText(System.getProperty("user.home"));
    }
    @FXML
    private void selectLocation(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Location");
        File temp = chooser.showDialog(chooserStage);
        if (temp != null && temp.getAbsoluteFile() != null && temp.getAbsolutePath().length()!=0){
            locationText.setText(temp.getAbsolutePath());
        }
    }


    @FXML
    private void create(){
        File checker = new File(locationText.getText ()+"/"+projectText.getText ());
        if (projectText.getText()== null || projectText.getText().length()  == 0 ){
            errorLabel.setText("Project Name Can't Be Empty");
            projectText.setStyle("-fx-background-color: #ff9494;");
        }
        else if (AegisMainWindowController.proExists(projectText.getText())){
            errorLabel.setText("Duplicate Project Name");
            projectText.setStyle("-fx-background-color: #ff9494;");
        }
        else if (checker.exists ()){
            errorLabel.setText("A project with that name already exists");
            projectText.setStyle("-fx-background-color: #ff9494;");
        }
        else{
            AegisMainWindowController.projectClosed = true;
            errorLabel.getScene().getWindow().hide();
        }
    }

    public TextField getLocationText() {
        return locationText;
    }

    public TextField getProjectText() {
        return projectText;
    }

}
