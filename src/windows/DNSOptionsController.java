package windows;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 3/27/17.
 */
public class DNSOptionsController {

    @FXML
    RadioButton enableDNSButton;
    @FXML
    RadioButton disableDNSButton;
    @FXML
    CheckBox DNSServerCheck;
    @FXML
    RadioButton ownDNSRadioButton;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;
    @FXML
    ListView DNSListView;
    @FXML
    HBox DNSServerHBox;
    @FXML
    VBox DoDNSVBox;
    @FXML
    VBox DNSOptionsVBox;
    @FXML
    TextField DNSText;
    @FXML
    VBox DNSServerList;

    @FXML private void addItem(){
        String toAdd = DNSText.getText();
        if (toAdd.length() > 0 && !DNSListView.getItems().contains(toAdd)){
            DNSListView.getItems().add(toAdd);
            DNSText.setText("");
        }
    }
    @FXML private void removeItem(){
        if (DNSListView.getSelectionModel().getSelectedIndex() != -1){
            DNSListView.getItems().remove(DNSListView.getSelectionModel().getSelectedIndex());
        }
    }
    @FXML private void ControlDNS(){
        if (enableDNSButton.isSelected()){
            DNSOptionsVBox.setDisable(false);
        }else {
            DNSOptionsVBox.setDisable(true);
        }
    }
    @FXML private void ControlDNSServer(){
        if(DNSServerCheck.isSelected()){
            DoDNSVBox.setDisable(false);
            if (ownDNSRadioButton.isSelected()){
                DNSServerList.setDisable(false);
            }
        }else {
            DoDNSVBox.setDisable(true);
            DNSServerList.setDisable(true);
        }
    }
    @FXML private void OwnDNS(){
        if(ownDNSRadioButton.isSelected()){
            DNSServerList.setDisable(false);
        }else {
            DNSServerList.setDisable(true);
        }
    }

}
