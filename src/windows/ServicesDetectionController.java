package windows;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * Created by wintson on 3/31/17.
 */
public class ServicesDetectionController {

    String enable = " -sV -T4 -F ";

    @FXML
    CheckBox enableButton;
    @FXML
    CheckBox enableAllPortsButton;
    @FXML
    CheckBox debugOutput;
    @FXML
    VBox serviceDetectionVBox;
    @FXML
    VBox debugVBox;
    @FXML
    Slider intensitySlider;
    @FXML
    CheckBox CVECheck;

    @FXML
    private void initialize(){
        System.out.println("IN");
        intensitySlider.setMajorTickUnit(0.5);
        intensitySlider.setMinorTickCount(0);
        intensitySlider.setSnapToTicks(true);
        System.out.println("OUT");
    }

    @FXML
    private void enableServiceDetection(){
        if(enableButton.isSelected()){
            serviceDetectionVBox.setDisable(false);
        }else {
            serviceDetectionVBox.setDisable(true);
        }
    }
    @FXML
    private void enableDebugging(){
        if(debugOutput.isSelected()){
            debugVBox.setDisable(false);
        }else {
            debugVBox.setDisable(true);
        }
    }

    String getCommand(){
        StringBuilder sb = new StringBuilder ( 200 );
        if (enableButton.isSelected ()){
            sb.append ( enable );
        }


        return sb.toString ();
    }


    public boolean isCVESelected(){
        return CVECheck.isSelected ();
    }
}
