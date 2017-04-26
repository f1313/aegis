package windows;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by wintson on 4/8/17.
 */
public class PayloadController {

    FileChooser fileLoader = new FileChooser();
    Stage loader = new Stage();
    @FXML
    CheckBox dataCheck;
    @FXML
    RadioButton hexRadio;
    @FXML
    TextArea hexText;
    @FXML
    RadioButton binaryRadio;
    @FXML
    TextArea binaryText;
    @FXML
    RadioButton asciiRadio;
    @FXML
    RadioButton loadRadio;
    @FXML
    Button loadButton;
    @FXML
    TextArea asciiText;
    @FXML
    RadioButton randomRadio;
    @FXML
    TextField randomText;
    @FXML
    VBox dataVBox;
    @FXML
    Button saveButton;
    @FXML
    Label hexError;
    @FXML
    Label binaryError;
    @FXML
    Label asciiError;

    @FXML private void dataController(){
        if (dataCheck.isSelected()){
            dataVBox.setDisable(false);
        }else {
            dataVBox.setDisable(true);
        }
    }

    @FXML private void hexCtontroller(){
        if (hexRadio.isSelected()){
            hexText.setDisable(false);
            binaryText.setDisable(true);
            asciiText.setDisable(true);
            randomText.setDisable(true);
            loadButton.setDisable(true);
        }else {
            hexText.setDisable(true);
        }
    }

    @FXML private void binaryController(){
        if (binaryRadio.isSelected()){
            binaryText.setDisable(false);
            hexText.setDisable(true);
            asciiText.setDisable(true);
            randomText.setDisable(true);
            loadButton.setDisable(true);
        }else {
            binaryText.setDisable(true);
        }
    }

    @FXML private void asciiController(){
        if (asciiRadio.isSelected()){
            asciiText.setDisable(false);
            hexText.setDisable(true);
            binaryText.setDisable(true);
            randomText.setDisable(true);
            loadButton.setDisable(true);
        }else {
            asciiText.setDisable(true);
        }
    }

    @FXML private void randomController(){
        if(randomRadio.isSelected()){
            randomText.setDisable(false);
            binaryText.setDisable(true);
            hexText.setDisable(true);
            asciiText.setDisable(true);
            loadButton.setDisable(true);
        }else {
            randomText.setDisable(true);
        }
    }

    @FXML private void loadController(){
        if(loadRadio.isSelected()){
            loadButton.setDisable(false);
            randomText.setDisable(false);
            binaryText.setDisable(true);
            hexText.setDisable(true);
            asciiText.setDisable(true);
            randomText.setDisable(true);
        }else {
            loadButton.setDisable(true);
        }
    }

    @FXML private void loadButtonController(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Payload files (*.payload)", "*.payload");
        fileLoader.getExtensionFilters().add(extFilter);
        File temp = fileLoader.showOpenDialog(loader);
        if (temp != null && temp.getAbsoluteFile() != null && temp.getAbsolutePath().length() != 0) {

        }
    }

    @FXML private void save(){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        if (hexRadio.isSelected()){
            if (!hexText.getText().matches("((\\\\x)?(([0-9A-Fa-f]{2})+))")){
                hexError.setText("Invalid Hex Format. (Format: \\xFF or FF)");
                binaryError.setText("");
                return;
            }

        }
        else if (binaryRadio.isSelected()){
            if (!binaryText.getText().matches("(0|1)+")){
                binaryError.setText("Invalid Binary Format.");
                hexError.setText("");
                return;
            }

        }

        binaryError.setText("");
        hexError.setText("");
        stage.close();
    }
}
