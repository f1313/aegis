package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 4/8/17.
 */
public class ScanOptionsController {
    @FXML
    RadioButton basicScan;
    @FXML
    RadioButton fastScan;
    @FXML
    RadioButton randomScan;
    @FXML
    RadioButton ratioScan;
    @FXML
    RadioButton customScan;
    @FXML
    VBox ratioVBox;
    @FXML
    VBox customVBox;
    @FXML
    CheckBox topPortsCheck;
    @FXML
    TextField topPortsText;
    @FXML
    CheckBox includeCheck;
    @FXML
    TextField includeText;
    @FXML
    CheckBox excludeCheck;
    @FXML
    TextField excludeText;
    @FXML
    HBox customHBox;

    @FXML
    private void basicController ( ) {
        checker ( );
    }

    @FXML
    private void fastController ( ) {
        checker ( );
    }

    @FXML
    private void randomController ( ) {
        checker ( );
    }

    @FXML
    private void ratioController ( ) {
        checker ( );
    }

    @FXML
    private void customController ( ) {
        checker ( );
    }

    @FXML
    private void topRatio ( ) {
        if ( topPortsCheck.isSelected ( ) ) {
            topPortsText.setDisable ( false );
        } else {
            topPortsText.setDisable ( true );
        }
    }

    @FXML
    private void include ( ) {
        if ( includeCheck.isSelected ( ) ) {
            includeText.setDisable ( false );
        } else {
            includeText.setDisable ( true );
        }
    }

    @FXML
    private void exclude ( ) {
        if ( excludeCheck.isSelected ( ) ) {
            excludeText.setDisable ( false );
        } else {
            excludeText.setDisable ( true );
        }
    }

    private void checker ( ) {
        if ( customScan.isSelected ( ) ) {
            customHBox.setDisable ( false );
        } else {
            customHBox.setDisable ( true );
        }
    }

    public String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( fastScan.isSelected ( ) ) {
            sb.append ( " -F " );
        } else if ( randomScan.isSelected ( ) ) {
            sb.append ( " -r " );
        } else if ( customScan.isSelected ( ) ) {
            if ( excludeCheck.isSelected ( ) ) {
                if ( excludeText.getText ( ).matches ( "^([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])" +
                        "(-([01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$)?" ) ) {
                    sb.append ( " --exclude-ports " + excludeText.getText ( ) + " " );
                }
            }
        }

        return sb.toString ();
    }
}
