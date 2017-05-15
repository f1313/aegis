package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 3/31/17.
 */
public class OSDetectionController {

    String ena = " -O ",
            limitToPromising = " --osscan-limit ",
            aggressiveGuess = " --osscan-guess ";


    @FXML
    CheckBox osLimit;
    @FXML
    CheckBox enableCheck;
    @FXML
    CheckBox aggressiveScanCheck;
    @FXML
    VBox OSVBox;

    @FXML
    private void enableCheckController ( ) {
        if ( enableCheck.isSelected ( ) ) {
            OSVBox.setDisable ( false );
        } else {
            OSVBox.setDisable ( true );
        }
    }

    String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );
        if ( enableCheck.isSelected ( ) ) {
            sb.append ( ena );
            if ( osLimit.isSelected ( ) ) {
                sb.append ( limitToPromising );
            }
            if ( aggressiveScanCheck.isSelected ( ) ) {
                sb.append ( aggressiveGuess );
            }
            return sb.toString ( );
        } else {
            return " ";
        }
    }
}
