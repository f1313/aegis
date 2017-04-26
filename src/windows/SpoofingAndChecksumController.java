package windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Created by wintson on 4/8/17.
 */
public class SpoofingAndChecksumController {

    @FXML
    CheckBox macCheck;
    @FXML
    TextField macText;
    @FXML
    Button macButton;
    @FXML
    CheckBox proxyCheck;
    @FXML
    Button proxyButton;

    @FXML
    private void macController(){
        if(macCheck.isSelected()){
            macText.setDisable(false);
            macButton.setDisable(false);
        }else {
            macText.setDisable(true);
            macButton.setDisable(true);
        }
    }
    @FXML
    private void proxyController(){
        if (proxyCheck.isSelected()){
            proxyButton.setDisable(false);
        }else {
            proxyButton.setDisable(true);
        }
    }
}
