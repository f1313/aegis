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
    CheckBox interfaceCheck;
    @FXML
    ListView interfaceListView;
    @FXML
    VBox enableVBox;
    @FXML
    CheckBox ttlCheck;
    @FXML
    TextField ttlText;
    @FXML
    CheckBox fragmentCheck;
    @FXML
    CheckBox firewalk;

    @FXML
    private void initialize ( ) {

    }

    @FXML
    private void enableController ( ) {
        if ( enableCheck.isSelected ( ) ) {
            enableVBox.setDisable ( false );
        } else {
            enableVBox.setDisable ( true );
        }
    }



    @FXML
    private void interfaceController ( ) {
        if ( interfaceCheck.isSelected ( ) ) {
        }
    }


    @FXML
    private void ttlController ( ) {
        if ( ttlCheck.isSelected ( ) ) {
            ttlText.setDisable ( false );
        } else {
            ttlText.setDisable ( true );
        }
    }


    public String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( enableCheck.isSelected ( ) ) {
            if (fragmentCheck.isSelected ()){
                sb.append ( " -f " );
            }
            if(firewalk.isSelected ()){
                sb.append ( " --script=firewalk " );
            }
            if(ttlCheck.isSelected ()){
                if (ttlText.getText ().matches ( "\\d+" )){
                    sb.append ( " -ttl "+ttlText.getText () );
                }
            }
        }


        return sb.toString ( );
    }
}
