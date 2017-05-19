package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 3/31/17.
 */
public class ServicesDetectionController {

    String enable = " -sV ";

    @FXML
    CheckBox enableButton;
    @FXML
    CheckBox debugOutput;
    @FXML
    VBox serviceDetectionVBox;
    @FXML
    VBox debugVBox;
    @FXML
    RadioButton intensityButton;
    @FXML
    TextField intensityText;
    @FXML
    RadioButton enableAllButton;
    @FXML
    RadioButton lightButton;

    @FXML
    private void enableServiceDetection ( ) {
        if ( enableButton.isSelected ( ) ) {
            serviceDetectionVBox.setDisable ( false );
        } else {
            serviceDetectionVBox.setDisable ( true );
        }
    }

    @FXML
    private void intensityClick ( ) {
        if ( intensityButton.isSelected ( ) ) {
            intensityText.setDisable ( false );
        } else {
            intensityText.setDisable ( false );

        }
    }

    @FXML
    private void enableDebugging ( ) {
        if ( debugOutput.isSelected ( ) ) {
            debugVBox.setDisable ( false );
        } else {
            debugVBox.setDisable ( true );
        }
    }

    String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( enableButton.isSelected ( ) ) {
            sb.append ( enable );
            if ( intensityButton.isSelected ( ) ) {
                if ( intensityText.getText ( ).matches ( "[0-9]" ) ) {
                    sb.append ( " --version-intensity " + intensityText.getText ( ) + " " );
                }
            } else if ( enableAllButton.isSelected ( ) ) {
                sb.append ( " --version-all " );
            } else if ( lightButton.isSelected ( ) ) {
                sb.append ( " --version-light " );
            }
            if (debugOutput.isSelected ()){
                sb.append ( " --version-trace " );
            }
        }
        return sb.toString ( );
    }

    public boolean isEnabled ( ) {
        return enableButton.isSelected ( );
    }

}
