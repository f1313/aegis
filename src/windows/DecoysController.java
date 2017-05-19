package windows;

import Specs.TargetSpec;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import Util.*;

import javax.xml.soap.Text;

/**
 * Created by wintson on 4/6/17.
 */
public class DecoysController {
    TargetSpec spec = new TargetSpec ( );
    @FXML
    CheckBox decoysCheck;
    @FXML
    CheckBox randCheck;
    @FXML
    TextField randText;
    @FXML
    VBox enableVBox;
    @FXML
    VBox decoysVBox;
    @FXML
    TextField ipText;
    @FXML
    ListView decoysList;
    @FXML
    Label errorLabel;

    @FXML
    private void init ( ) {
        ipText.setOnKeyPressed ( new EventHandler < KeyEvent > ( ) {
            @Override
            public void handle ( javafx.scene.input.KeyEvent event ) {
                ipText.setStyle ( "-fx-background-color: white;" );
                errorLabel.setText ( "" );
                if ( event.getCode ( ) == KeyCode.ENTER ) {
                    add ( );
                }
            }
        } );
    }

    @FXML
    private void enableController ( ) {
        if ( decoysCheck.isSelected ( ) ) {
            decoysVBox.setDisable ( false );
        } else {
            decoysVBox.setDisable ( true );
        }
    }

    @FXML
    private void add ( ) {
        String toAdd = ipText.getText ( );

        if ( toAdd.length ( ) <= 0 ) {
            ipText.setStyle ( "-fx-background-color: #ff9494;" );
            errorLabel.setText ( "Empty Target" );
            return;
        } else if ( decoysList.getItems ( ).contains ( toAdd ) ) {
            ipText.setStyle ( "-fx-background-color: #ff9494;" );
            errorLabel.setText ( "Duplicate Target" );
            return;
        } else if ( ! spec.validateHostString ( toAdd ) ) {
            errorLabel.setText ( "Invalid Target Format" );
            ipText.setStyle ( "-fx-background-color: #ff9494;" );
            return;
        } else {
            decoysList.getItems ( ).add ( toAdd );
            ipText.setText ( "" );
            errorLabel.setText ( "" );
            ipText.setStyle ( "-fx-background-color: white;" );
        }
    }

    @FXML
    private void remove ( ) {
        if ( decoysList.getSelectionModel ( ).getSelectedIndex ( ) != - 1 ) {
            decoysList.getItems ( ).remove ( decoysList.getSelectionModel ( ).getSelectedIndex ( ) );
        }
    }

    @FXML
    private void randController ( ) {
        if ( randCheck.isSelected ( ) ) {
            randText.setDisable ( false );
        } else {
            randText.setDisable ( true );
        }
    }


    public String getCommand ( ) {
        StringBuilder sb = new StringBuilder ( 200 );

        if ( decoysCheck.isSelected ( ) ) {
            if ( decoysList.getItems ( ).size ( ) != 0 ) {
                sb.append ( " -D " );
                for ( Object x : decoysList.getItems ( ) ) {
                    sb.append ( x.toString ( ) + "," );
                }
                sb.replace ( sb.length ( ) - 1, sb.length ( ), " " );
            }

            if ( randCheck.isSelected ( ) ) {
                if ( randText.getText ( ).matches ( "\\d+" ) ) {
                    sb.append ( "RND:" + randText.getText ( ) + " " );
                }
            }
        }

        return sb.toString ( );
    }
}
