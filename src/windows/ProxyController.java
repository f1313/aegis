package windows;

import Specs.PortSpec;
import Specs.TargetSpec;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Util.*;
import javafx.stage.Stage;

/**
 * Created by wintson on 5/20/17.
 */
public class ProxyController {


    @FXML
    ListView proxyListView;
    @FXML
    TextField proxyText;
    @FXML
    Label errorLabel;
    TargetSpec spec = new TargetSpec ( );
    Stage proxy = new Stage ( );

    @FXML
    private void add ( ) {
        String toAdd = proxyText.getText ( );

        if ( toAdd.length ( ) <= 0 ) {
            proxyText.setStyle ( "-fx-background-color: #ff9494;" );
            errorLabel.setText ( "Empty Target" );
            return;
        } else if ( proxyListView.getItems ( ).contains ( toAdd ) ) {
            proxyText.setStyle ( "-fx-background-color: #ff9494;" );
            errorLabel.setText ( "Duplicate Target" );
            return;
        } else if ( ! spec.validateHostString ( toAdd ) ) {
            errorLabel.setText ( "Invalid Target Format" );
            proxyText.setStyle ( "-fx-background-color: #ff9494;" );
            return;
        } else if ( toAdd.equals ( "+ Add Group" ) ) {
            errorLabel.setText ( "Invalid Name" );
            proxyText.setStyle ( "-fx-background-color: #ff9494;" );
        } else {
            proxyListView.getItems ( ).add ( toAdd );
            proxyText.setText ( "" );
            errorLabel.setText ( "" );
            proxyText.setStyle ( "-fx-background-color: white;" );
        }
    }

    @FXML
    private void remove ( ) {
        if ( proxyListView.getSelectionModel ( ).getSelectedIndex ( ) != - 1 ) {
            proxyListView.getItems ( ).remove ( proxyListView.getSelectionModel ( ).getSelectedIndex ( ) );
        }
    }


    public String getProxies ( ) {
        String proxies = "";
        if ( proxyListView.getItems ( ).size ( ) != 0 ) {
            for ( Object x : proxyListView.getItems ( ) ) {
                proxies += x + ",";
            }
        }
        if ( ! proxies.isEmpty ( ) ) {
            return proxies.substring ( 0, proxies.length ( ) - 1 );
        }
        return "";
    }
}
