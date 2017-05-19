package windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import Specs.*;

import java.util.Random;

/**
 * Created by wintson on 4/8/17.
 */
public class SpoofingAndChecksumController {
    String macRegex = "[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:" +
            "[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}";
    PortSpec portSpec = new PortSpec ( );
    TargetSpec spec = new TargetSpec ( );
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
    CheckBox portCheck;
    @FXML
    TextField portsText;
    @FXML
    CheckBox ipCheck;
    @FXML
    TextField ipText;
    @FXML
    CheckBox badCheckSum;
    @FXML
    CheckBox oldCheckSum;

    @FXML
    private void macController ( ) {
        if ( macCheck.isSelected ( ) ) {
            macText.setDisable ( false );
            macButton.setDisable ( false );
        } else {
            macText.setDisable ( true );
            macButton.setDisable ( true );
        }
    }

    @FXML
    private void proxyController ( ) {
        if ( proxyCheck.isSelected ( ) ) {
            proxyButton.setDisable ( false );
        } else {
            proxyButton.setDisable ( true );
        }
    }

    @FXML
    private void portController ( ) {
        if ( portCheck.isSelected ( ) ) {
            portsText.setDisable ( false );
        } else {
            portsText.setDisable ( true );
        }
    }

    @FXML
    private void ipController ( ) {
        if ( ipCheck.isSelected ( ) ) {
            ipText.setDisable ( false );
        } else {
            ipText.setDisable ( true );
        }
    }

    @FXML
    private void randomizeMac ( ) {
        macText.setText ( randomMACAddress ( ) );
    }


    public String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( portCheck.isSelected ( ) ) {
            if ( portSpec.validatePortString ( portsText.getText ( ) ) ) {
                sb.append ( " --source-port " + portsText.getText ( ) + " " );
            }
        }
        if ( ipCheck.isSelected ( ) ) {
            if ( spec.validateHostString ( ipText.getText ( ) ) ) {
                sb.append ( " -S " + ipText.getText ( ) );
            }
        }

        if ( macCheck.isSelected ( ) ) {
            if ( macText.getText ( ).matches ( macRegex ) ) {
                sb.append ( " --spoof-mac " + macText.getText ( ) + " " );
            }
        }

        if ( badCheckSum.isSelected ( ) ) {
            sb.append ( " --badsum " );
        }

        if ( oldCheckSum.isSelected ( ) ) {
            sb.append ( " --adler32 " );
        }

        return sb.toString ( );
    }


    private String randomMACAddress ( ) {
        Random rand = new Random ( );
        byte[] macAddr = new byte[ 6 ];
        rand.nextBytes ( macAddr );
        macAddr[ 0 ] = ( byte ) ( macAddr[ 0 ] & ( byte ) 254 );  //zeroing last 2 bytes to make it unicast and locally adminstrated
        StringBuilder sb = new StringBuilder ( 18 );
        for ( byte b : macAddr ) {
            if ( sb.length ( ) > 0 )
                sb.append ( ":" );

            sb.append ( String.format ( "%02x", b ) );
        }
        return sb.toString ( );
    }


}
