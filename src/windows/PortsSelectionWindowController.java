package windows;

import Specs.*;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Created by wintson on 3/14/17.
 */
public class PortsSelectionWindowController {
    Stage loader;
    FileChooser fileLoader = new FileChooser ( );

    @FXML
    CheckBox portHelperButton;
    @FXML
    TextField portTextField;
    @FXML
    ListView portsListView;


    private PortSpec portObject = new PortSpec ( );

    @FXML
    private void addPortButton ( ) throws InterruptedException {
        String toAdd = portTextField.getText ( );

        if ( toAdd.length ( ) <= 0 ) {
            portTextField.setStyle ( "-fx-background-color: #ff9494;" );

            return;
        }
        if ( portsListView.getItems ( ).contains ( toAdd ) ) {
            portTextField.setStyle ( "-fx-background-color: #ff9494;" );
            return;
        }
        if ( ! portObject.validatePortString ( toAdd ) ) {
            portTextField.setStyle ( "-fx-background-color: #ff9494;" );
            return;
        }

        portsListView.getItems ( ).add ( toAdd );
        portTextField.setText ( "" );
        portTextField.setStyle ( "-fx-background-color: white;" );

    }

    @FXML
    private void removePortButton ( ) {
        if ( portsListView.getSelectionModel ( ).getSelectedIndex ( ) != - 1 ) {
            portsListView.getItems ( ).remove ( portsListView.getSelectionModel ( ).getSelectedIndex ( ) );
        }
    }

    @FXML
    private void save ( ) {
        portObject.setIncludedPortsList ( ( ArrayList < String > ) Arrays
                .asList ( ( String[] ) portsListView.getItems ( ).toArray ( ) ) );
        portObject.setIsExcludedPortsConfigured ( false );
        portObject.setIsExcludedPortsLoadedFromFile ( false );
        portObject.setIsIncludedPortsConfigured ( true );
        portObject.setIsIncludedPortsLoadedFromFile ( false );
        Stage stage = ( Stage ) portHelperButton.getScene ( ).getWindow ( );
        stage.close ( );
    }

    @FXML
    private void load ( ) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ( "Ports files (*.port)", "*.port" );
        fileLoader.getExtensionFilters ( ).add ( extFilter );
        File temp = fileLoader.showOpenDialog ( loader );
        if ( temp != null && temp.getAbsoluteFile ( ) != null && temp.getAbsolutePath ( ).length ( ) != 0 ) {
            Scanner input = null;
            String ports = "";
            try {
                input = new Scanner ( temp );
            } catch ( FileNotFoundException e ) {
                e.printStackTrace ( );
            }
            while ( input.hasNext ( ) ) {
                ports += input.next ( );
            }
            String[] arr = ports.split ( "," );
            for ( String x : arr ) {
                if (portObject.validatePortString ( x )){
                    portsListView.getItems ().add ( x );
                }
            }
        }
    }

    public PortSpec getPortObject ( ) {
        return portObject;
    }

    public void setPortObject ( PortSpec portObject ) {
        this.portObject = portObject;
    }

    public String getPorts ( ) {
        String res = "";
        try {
            if ( portsListView.getItems ( ).size ( ) != 0 ) {
                for ( Object x : portsListView.getItems ( ) ) {
                    res += x + ",";
                }
                if ( res.length ( ) != 0 ) {
                    return res.substring ( 0, res.length ( ) - 1 );
                }
            }
        } catch ( Exception e ) {
            return "";
        }
        return "";
    }


}
