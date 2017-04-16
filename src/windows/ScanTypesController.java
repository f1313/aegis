package windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.font.NumericShaper;
import java.io.IOException;

/**
 * Created by wintson on 3/24/17.
 */
public class ScanTypesController {

    @FXML
    RadioButton ListScanButton;
    @FXML
    RadioButton AdvancedScanButton;
    @FXML
    CheckBox NoPortCheck;
    @FXML
    CheckBox NoPingCheck;
    @FXML
    CheckBox SYNCheck;
    @FXML
    CheckBox ACKCheck;
    @FXML
    CheckBox UDPCheck;
    @FXML
    TextField SYNText;
    @FXML
    TextField ACKText;
    @FXML
    TextField UDPText;
    @FXML
    TextField SCTPText;
    @FXML
    VBox AdvancedVbox;
    @FXML
    VBox pingTypesBox;
    @FXML
    RadioButton EchoButton;
    @FXML
    RadioButton TimeStampButton;
    @FXML
    RadioButton MaskButton;
    @FXML
    Button protocolButton;
    @FXML
    Button rangesButton;
    @FXML
    CheckBox IPpingButton;
    @FXML
    VBox noPortOff;
    @FXML
    CheckBox tcpCheckBox;
    @FXML
    VBox tcpVBox;
    @FXML
    VBox SCTPVBox;
    @FXML
    CheckBox sctpCheck;
    @FXML
    Button TCPPorts;
    @FXML
    Button UDPPorts;
    @FXML
    Button SCTPPorts;
    @FXML
    Button udpPayloadButton;
    @FXML
    Button sctpPayloadButton;


    Stage udpPayload;
    Stage sctpPayload;
    Stage udpPorts;
    Stage sctpPorts;


    @FXML
    private void tcpCheckBoxController(){
        if (tcpCheckBox.isSelected()){
            tcpVBox.setDisable(false);
            TCPPorts.setDisable(false);

        }else {
            tcpVBox.setDisable(true);
            TCPPorts.setDisable(true);
        }
    }

    @FXML
    private void udpCheckController(){
        if (UDPCheck.isSelected()){
            UDPPorts.setDisable(false);
            udpPayloadButton.setDisable(false);

        }else {
            UDPPorts.setDisable(true);
            udpPayloadButton.setDisable(true);
        }
    }

    @FXML
    private void sctpCheckController(){
        if (sctpCheck.isSelected()){
            SCTPPorts.setDisable(false);
            SCTPVBox.setDisable(false);
            sctpPayloadButton.setDisable(false);
        }else {
            SCTPPorts.setDisable(true);
            SCTPVBox.setDisable(true);
            sctpPayloadButton.setDisable(true);
        }
    }


    @FXML
    private void listScanButton(){
        AdvancedVbox.setDisable(true);
    }

    @FXML
    private void advancedScanButton(){
        AdvancedVbox.setDisable(false);
    }

    @FXML
    private void noPortScanButtonOn(){
        if (NoPortCheck.isSelected()){
            noPortOff.setDisable(true);
        }
        else{
            noPortOff.setDisable(false);
        }
    }

    @FXML
    private void noPingScanButton(){
        if (NoPingCheck.isSelected()){
            pingTypesBox.setDisable(true);
        }else {
            pingTypesBox.setDisable(false);
        }
    }

    @FXML
    private void IPpingButtonController(){
        if(IPpingButton.isSelected()){
            rangesButton.setDisable(false);
            protocolButton.setDisable(false);
        }else {
            rangesButton.setDisable(true);
            protocolButton.setDisable(true);
        }
    }

    @FXML
    private void udpPayload() throws IOException {
        if (udpPayload == null){
            udpPayload = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Payload.fxml"));
            udpPayload.setScene(new Scene(root,479,555));
        }
        udpPayload.show();
    }
    @FXML
    private void sctpPayload() throws IOException{
        if (sctpPayload == null){
            sctpPayload = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Payload.fxml"));
            sctpPayload.setScene(new Scene(root,479,555));
        }
        sctpPayload.show();
    }

    @FXML
    private void sctpPorts() throws IOException{
        if (sctpPorts == null){
            sctpPorts = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("PortsSelectionWindow.fxml"));
            sctpPorts.setScene(new Scene(root,328,328));
        }
        sctpPorts.show();
    }

    @FXML
    private void udpPorts() throws IOException{
        if (udpPorts == null){
            udpPorts = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("PortsSelectionWindow.fxml"));
            udpPorts.setScene(new Scene(root,328,328));
        }
        udpPorts.show();
    }

}
