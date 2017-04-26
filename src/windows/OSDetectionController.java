package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 3/31/17.
 */
public class OSDetectionController {
    @FXML
    CheckBox enableCheck;
    @FXML
    CheckBox maxTriesCheck;
    @FXML
    TextField maxNum;
    @FXML
    VBox OSVBox;

    @FXML
    private void enableCheckController(){
        if (enableCheck.isSelected()){
            OSVBox.setDisable(false);
        }else {
            OSVBox.setDisable(true);
        }
    }
    @FXML
    private void enableMaxTries(){
        if (maxTriesCheck.isSelected()){
            maxNum.setDisable(false);
        }else {
            maxNum.setDisable(true);
        }
    }
}
