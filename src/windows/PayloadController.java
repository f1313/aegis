package windows;

import Util.input;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by wintson on 4/8/17.
 */
public class PayloadController {

    FileChooser fileLoader = new FileChooser ( );
    Stage loader = new Stage ( );
    String payLoadCopied = "";
    @FXML
    CheckBox dataCheck;
    @FXML
    RadioButton hexRadio;
    @FXML
    TextArea hexText;
    @FXML
    RadioButton asciiRadio;
    @FXML
    RadioButton loadRadio;
    @FXML
    Button loadButton;
    @FXML
    TextArea asciiText;
    @FXML
    RadioButton randomRadio;
    @FXML
    TextField randomText;
    @FXML
    VBox dataVBox;
    @FXML
    Button saveButton;
    @FXML
    Label hexError;
    @FXML
    Label asciiError;


    @FXML
    private void dataController ( ) {
        if ( dataCheck.isSelected ( ) ) {
            dataVBox.setDisable ( false );
        } else {
            dataVBox.setDisable ( true );
        }
    }

    @FXML
    private void hexCtontroller ( ) {
        if ( hexRadio.isSelected ( ) ) {
            hexText.setDisable ( false );
            asciiText.setDisable ( true );
            randomText.setDisable ( true );
            loadButton.setDisable ( true );
        } else {
            hexText.setDisable ( true );
        }
    }


    @FXML
    private void asciiController ( ) {
        if ( asciiRadio.isSelected ( ) ) {
            asciiText.setDisable ( false );
            hexText.setDisable ( true );
            randomText.setDisable ( true );
            loadButton.setDisable ( true );
        } else {
            asciiText.setDisable ( true );
        }
    }

    @FXML
    private void randomController ( ) {
        if ( randomRadio.isSelected ( ) ) {
            randomText.setDisable ( false );
            hexText.setDisable ( true );
            asciiText.setDisable ( true );
            loadButton.setDisable ( true );
        } else {
            randomText.setDisable ( true );
        }
    }

    @FXML
    private void loadController ( ) {
        if ( loadRadio.isSelected ( ) ) {
            loadButton.setDisable ( false );
            randomText.setDisable ( false );
            hexText.setDisable ( true );
            asciiText.setDisable ( true );
            randomText.setDisable ( true );
        } else {
            loadButton.setDisable ( true );
        }
    }

    @FXML
    private void loadButtonController ( ) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ( "Payload files (*.payload)", "*.payload" );
        fileLoader.getExtensionFilters ( ).add ( extFilter );
        File temp = fileLoader.showOpenDialog ( loader );
        if ( temp != null && temp.getAbsoluteFile ( ) != null && temp.getAbsolutePath ( ).length ( ) != 0 ) {
            Scanner input = null;
            try {
                input  = new Scanner (temp);
            } catch ( FileNotFoundException e ) {
                e.printStackTrace ( );
            }

            while ( input.hasNext ()){
                payLoadCopied += input.next ();
            }

        }
    }

    @FXML
    private void save ( ) {
        Stage stage = ( Stage ) saveButton.getScene ( ).getWindow ( );
        if ( hexRadio.isSelected ( ) ) {

            if ( ! hexText.getText ( ).matches ( "(0x([A-Fa-f0-9]{2})+)" ) &&
                    ! hexText.getText ( ).matches ( "((\\\\x)(([0-9A-Fa-f]{2})))+" ) ) {
                hexError.setText ( "Invalid Hex Format. (Format: \\xFF\\xAA or 0xFFAA)" );
                return;
            }
        }


        hexError.setText ( "" );
        stage.close ( );
    }


    public String getPayload ( ) {
        String payload = "";
        if ( dataCheck.isSelected ( ) ) {
            if ( hexRadio.isSelected ( ) ) {
                if ( hexError.getText ( ).length ( ) == 0 ) {
                    payload += " --data ";
                    payload += hexText.getText ( ) + " ";
                }
            } else if ( asciiRadio.isSelected ( ) ) {
                payload += " --data-string ";
                payload += asciiText.getText ( ) + " ";
            } else if ( randomRadio.isSelected ( ) ) {
                if ( randomText.getText ( ) != null && randomText.getText ( ).length ( ) != 0 ) {
                    payload += " --data-length " + randomText.getText ( ) + " ";
                }
            } else if ( loadRadio.isSelected ( ) ) {
                if (payLoadCopied.length () != 0){
                    payload += " --data-string " + payLoadCopied + " ";
                }
            }
        }
        return payload;
    }
}
