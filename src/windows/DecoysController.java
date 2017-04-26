package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 4/6/17.
 */
public class DecoysController {
    @FXML
    CheckBox decoysCheck;
    @FXML
    CheckBox randCheck;
    @FXML
    TextField randText;
    @FXML
    VBox enableVBox;

    @FXML
    private void enableController(){
        if (decoysCheck.isSelected()){
            enableVBox.setDisable(false);
        }else {
            enableVBox.setDisable(true);
        }
    }

    @FXML
    private void randController(){
        if (randCheck.isSelected()){
            randText.setDisable(false);
        }else {
            randText.setDisable(true);
        }
    }
}
