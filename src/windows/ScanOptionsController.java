package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 4/8/17.
 */
public class ScanOptionsController {
    @FXML
    RadioButton basicScan;
    @FXML
    RadioButton fastScan;
    @FXML
    RadioButton randomScan;
    @FXML
    RadioButton ratioScan;
    @FXML
    RadioButton customScan;
    @FXML
    VBox ratioVBox;
    @FXML
    VBox customVBox;
    @FXML
    CheckBox topPortsCheck;
    @FXML
    TextField topPortsText;
    @FXML
    CheckBox includeCheck;
    @FXML
    TextField includeText;
    @FXML
    CheckBox excludeCheck;
    @FXML
    TextField excludeText;

    @FXML
    private void basicController(){
        checker();
    }
    @FXML
    private void fastController(){
        checker();
    }
    @FXML
    private void randomController(){
        checker();
    }
    @FXML
    private void ratioController(){
        checker();
    }
    @FXML
    private void customController(){
        checker();
    }
    @FXML
    private void topRatio(){
        if(topPortsCheck.isSelected()){
            topPortsText.setDisable(false);
        }else {
            topPortsText.setDisable(true);
        }
    }
    @FXML
    private void include(){
        if (includeCheck.isSelected()){
            includeText.setDisable(false);
        }else {
            includeText.setDisable(true);
        }
    }
    @FXML
    private void exclude(){
        if (excludeCheck.isSelected()){
            excludeText.setDisable(false);
        }else {
            excludeText.setDisable(true);
        }
    }

    private void checker(){
        if (ratioScan.isSelected()){
            ratioVBox.setDisable(false);
        }else {
            ratioVBox.setDisable(true);
        }
        if (customScan.isSelected()){
            customVBox.setDisable(false);
        }else {
            customVBox.setDisable(true);
        }
    }
}
