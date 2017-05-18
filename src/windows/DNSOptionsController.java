package windows;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Specs.TargetSpec;

/**
 * Created by wintson on 3/27/17.
 */
public class DNSOptionsController {

    TargetSpec TargetObject = new TargetSpec ( );
    @FXML
    RadioButton enableDNSButton;
    @FXML
    RadioButton disableDNSButton;
    @FXML
    CheckBox DNSServerCheck;
    @FXML
    CheckBox traceRouteCheck;
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
    @FXML
    Label errorLabel;

    public void init(){
        DNSText.setOnMouseClicked ( e -> DNSText.setStyle ( "-fx-background-color: white;" ) );
    }

    @FXML
    private void addItem ( ) {
        String toAdd = DNSText.getText ( );
        if ( ! TargetObject.validateHostString ( toAdd ) ) {
            errorLabel.setText ( "Invalid Service Format" );
            DNSText.setStyle ( "-fx-background-color: #ff9494;" );
            return;
        } else {
            if ( toAdd.length ( ) > 0 && ! DNSListView.getItems ( ).contains ( toAdd ) ) {
                DNSListView.getItems ( ).add ( toAdd );
                DNSText.setText ( "" );
            }
        }
    }

    @FXML
    private void removeItem ( ) {
        if ( DNSListView.getSelectionModel ( ).getSelectedIndex ( ) != - 1 ) {
            DNSListView.getItems ( ).remove ( DNSListView.getSelectionModel ( ).getSelectedIndex ( ) );
        }
    }

    @FXML
    private void ControlDNS ( ) {
        if ( enableDNSButton.isSelected ( ) ) {
            DNSOptionsVBox.setDisable ( false );
        } else {
            DNSOptionsVBox.setDisable ( true );
        }
    }

    @FXML
    private void ControlDNSServer ( ) {
        if ( DNSServerCheck.isSelected ( ) ) {
            DoDNSVBox.setDisable ( false );
            if ( ownDNSRadioButton.isSelected ( ) ) {
                DNSServerList.setDisable ( false );
            }
        } else {
            DoDNSVBox.setDisable ( true );
            DNSServerList.setDisable ( true );
        }
    }

    @FXML
    private void OwnDNS ( ) {
        if ( ownDNSRadioButton.isSelected ( ) ) {
            DNSServerList.setDisable ( false );
        } else {
            DNSServerList.setDisable ( true );
        }
    }

    public String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( enableDNSButton.isSelected ( ) ) {
            if ( traceRouteCheck.isSelected ( ) ) {
                sb.append ( " --traceroute " );
            }
            if ( DNSServerCheck.isSelected ( ) ) {
                sb.append ( " -R " );
                if ( ownDNSRadioButton.isSelected ( ) ) {
                    if ( ! DNSListView.getItems ( ).isEmpty ( ) ) {
                        sb.append ( " --dns-servers " );
                        for ( Object x : DNSListView.getItems ( ) ) {
                            sb.append ( x + "," );
                        }
                        sb.append ( " " );
                    }
                } else {
                    sb.append ( " --system-dns " );
                }
            } else {
                sb.append ( " -n " );
            }
            return sb.toString ( );
        } else {
            return " ";
        }
    }

}
