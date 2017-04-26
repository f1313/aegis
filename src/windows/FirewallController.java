package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 * Created by wintson on 3/31/17.
 */
public class FirewallController {

    @FXML
    VBox mainVBox;
    @FXML
    CheckBox enableCheck;
    @FXML
    CheckBox sourceIPCheck;
    @FXML
    CheckBox interfaceCheck;
    @FXML
    ListView interfaceListView;
    @FXML
    VBox enableVBox;
    @FXML
    TextField sourceIPText;
    @FXML
    CheckBox sourcePortCheck;
    @FXML
    TextField sourcePortText;
    @FXML
    CheckBox ttlCheck;
    @FXML
    TextField ttlText;

    @FXML
    private void initialize(){

    }

    @FXML
    private void enableController(){
        if (enableCheck.isSelected()){
            enableVBox.setDisable(false);
        }else {
            enableVBox.setDisable(true);
        }
    }

    @FXML
    private void sourceIP(){
        if (sourceIPCheck.isSelected()){
            sourceIPText.setDisable(false);
        }else {
            sourceIPText.setDisable(true);
        }
    }

    @FXML
    private void interfaceController(){
        if(interfaceCheck.isSelected()){
        }
    }

    @FXML
    private void sourcePortController(){
        if (sourcePortCheck.isSelected()){
            sourcePortText.setDisable(false);
        }else {
            sourcePortText.setDisable(true);
        }
    }

    @FXML
    private void ttlController(){
        if (ttlCheck.isSelected()){
            ttlText.setDisable(false);
        }else{
            ttlText.setDisable(true);
        }
    }
}
