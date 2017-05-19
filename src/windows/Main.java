package windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    @Override
    public void start ( Stage primaryStage ) throws Exception {
        FXMLLoader loader = new FXMLLoader ( getClass ( ).getResource ( "AegisMainWindow.fxml" ) );
        Parent root = loader.load ( );
        AegisMainWindowController mainController = loader.getController ( );
        closed ( primaryStage, mainController );
        primaryStage.setScene ( new Scene ( root ) );
        primaryStage.setMaximized ( true );
        primaryStage.show ( );
    }

    public void closed ( Stage primaryStage, AegisMainWindowController mainController ) {
        primaryStage.setOnCloseRequest ( event -> {
            if ( mainController.getProcesses ( ).size ( ) != 0 ) {
                Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
                alert.setTitle ( "Scans in progress" );
                alert.setHeaderText ( "Some scans are still running.\n Are you sure you want to exit?" );
                String procs = "";
                for ( String x : mainController.getProcesses ( ).keySet ( ) ) {
                    procs += "-" + x + "\n";
                }
                alert.setContentText ( "Scans running:\n" + procs );
                alert.showAndWait ( );
                if ( alert.getResult ( ) == ButtonType.OK ) {
                    for ( String x : mainController.getProcesses ( ).keySet ( ) ) {
                        mainController.getProcesses ( ).get ( x ).destroyForcibly ( );
                    }
                }else {
                    event.consume ();
                }
            }
        } );

    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}
