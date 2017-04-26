package windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


/**
 * Created by wintson on 3/26/17.
 */
public class ProtocolRangesController {
    @FXML
    ComboBox IPProtocolBox;
    @FXML
    ListView IPProtocolList;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;

    @FXML
    private void addItem(){
        String toAdd = (String)IPProtocolBox.getSelectionModel().getSelectedItem();
        if (toAdd != null && toAdd.length() > 0){
            IPProtocolList.getItems().add(toAdd);
            IPProtocolBox.getSelectionModel().clearSelection();
        }
    }
    @FXML
    private void removeItem(){
        if (IPProtocolList.getItems().size() > 0 && IPProtocolList.getSelectionModel().getSelectedIndex() != -1)
        IPProtocolList.getItems().remove(IPProtocolList.getSelectionModel().getSelectedIndex());
    }
}
