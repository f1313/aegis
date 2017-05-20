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
    CheckBox UDPCheck;
    @FXML
    VBox AdvancedVbox;
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
    RadioButton syn;
    @FXML
    RadioButton ack;
    @FXML
    RadioButton xmas;
    @FXML
    RadioButton NL;
    @FXML
    RadioButton connect;
    @FXML
    RadioButton fin;
    @FXML
    RadioButton window;
    @FXML
    RadioButton maimon;
    @FXML
    RadioButton init;
    @FXML
    RadioButton echo;
    @FXML
    CheckBox payloadCheck;
    @FXML
    Button payloadButton;

    Stage payloadStage;
    Stage udpPorts;
    Stage sctpPorts;
    Stage tcpPorts;

    PortsSelectionWindowController tcpPortsController = new PortsSelectionWindowController ( );
    PortsSelectionWindowController udpPortsController = new PortsSelectionWindowController ( );
    PortsSelectionWindowController sctpPortsController = new PortsSelectionWindowController ( );

    PayloadController payloadController;

    @FXML
    private void tcpCheckBoxController ( ) {
        if ( tcpCheckBox.isSelected ( ) ) {
            tcpVBox.setDisable ( false );
            TCPPorts.setDisable ( false );

        } else {
            tcpVBox.setDisable ( true );
            TCPPorts.setDisable ( true );
        }
    }


    @FXML
    private void payloadClick ( ) {
        if ( payloadCheck.isSelected ( ) ) {
            payloadButton.setDisable ( false );
        } else {
            payloadButton.setDisable ( true );
        }
    }

    @FXML
    private void udpCheckController ( ) {
        if ( UDPCheck.isSelected ( ) ) {
            UDPPorts.setDisable ( false );

        } else {
            UDPPorts.setDisable ( true );
        }
    }

    @FXML
    private void sctpCheckController ( ) {
        if ( sctpCheck.isSelected ( ) ) {
            SCTPPorts.setDisable ( false );
            SCTPVBox.setDisable ( false );
        } else {
            SCTPPorts.setDisable ( true );
            SCTPVBox.setDisable ( true );
        }
    }

    @FXML
    private void listScanButton ( ) {
        AdvancedVbox.setDisable ( true );
    }

    @FXML
    private void advancedScanButton ( ) {
        AdvancedVbox.setDisable ( false );
    }

    @FXML
    private void noPortScanButtonOn ( ) {
        if ( NoPortCheck.isSelected ( ) ) {
            noPortOff.setDisable ( true );
        } else {
            noPortOff.setDisable ( false );
        }
    }

    @FXML
    private void noPingScanButton ( ) {
        if ( NoPingCheck.isSelected ( ) ) {
        } else {
        }
    }


    @FXML
    private void tcpPorts ( ) throws IOException {
        if ( tcpPorts == null ) {
            tcpPorts = new Stage ( );
            tcpPorts.setMaxWidth ( 325 );
            tcpPorts.setMaxHeight ( 380 );
            FXMLLoader loader = new FXMLLoader ( getClass ( ).getResource ( "PortsSelectionWindow.fxml" ) );
            Parent root = loader.load ( );
            tcpPortsController = loader.getController ( );
            tcpPorts.setScene ( new Scene ( root, 479, 585 ) );
        }
        tcpPorts.show ( );
    }

    @FXML
    private void Payload ( ) throws IOException {
        if ( payloadStage == null ) {
            payloadStage = new Stage ( );
            FXMLLoader loader = new FXMLLoader ( getClass ( ).getResource ( "Payload.fxml" ) );
            Parent root = loader.load ( );
            payloadController = loader.getController ( );
            payloadStage.setScene ( new Scene ( root, 479, 485 ) );
        }
        try {
            payloadStage.showAndWait ( );
        } catch ( Exception e ) {

        }
    }


    @FXML
    private void sctpPorts ( ) throws IOException {
        if ( sctpPorts == null ) {
            sctpPorts = new Stage ( );
            FXMLLoader loader = new FXMLLoader ( getClass ( ).getResource ( "PortsSelectionWindow.fxml" ) );
            Parent root = loader.load ( );
            sctpPortsController = loader.getController ( );
            sctpPorts.setScene ( new Scene ( root, 328, 354 ) );
        }
        try {
            sctpPorts.showAndWait ( );
        } catch ( Exception e ) {

        }
    }

    @FXML
    private void udpPorts ( ) throws IOException {
        if ( udpPorts == null ) {
            FXMLLoader loader = new FXMLLoader (
                    getClass ( ).getResource (
                            "PortsSelectionWindow.fxml"
                    )
            );
            udpPorts = new Stage ( );
            Parent root = loader.load ( );
            udpPortsController = loader.getController ( );
            udpPorts.setScene ( new Scene ( root, 328, 354 ) );
        }
        try {
            udpPorts.showAndWait ( );
        } catch ( Exception e ) {

        }
    }


    public String getCommand ( ) {
        String ports = "";
        boolean tcp = false, udp = false, sctp = false;

        StringBuilder sb = new StringBuilder ( 200 );
        //Scan type spec
        if ( ListScanButton.isSelected ( ) ) {
        } else {
            if ( NoPortCheck.isSelected ( ) ) {
                sb.append ( " -sn " );
            }
            if ( NoPingCheck.isSelected ( ) ) {
                sb.append ( " -Pn " );
            }

            //Adding TCP Flags
            if ( tcpCheckBox.isSelected ( ) ) {
                if ( syn.isSelected ( ) ) {
                    sb.append ( " -sS " );
                } else if ( connect.isSelected ( ) ) {
                    sb.append ( "-sT " );
                } else if ( ack.isSelected ( ) ) {
                    sb.append ( " -sA " );
                } else if ( window.isSelected ( ) ) {
                    sb.append ( " -sW " );
                } else if ( maimon.isSelected ( ) ) {
                    sb.append ( " sM " );
                } else if ( NL.isSelected ( ) ) {
                    sb.append ( " -sN " );
                } else if ( fin.isSelected ( ) ) {
                    sb.append ( " -sF " );
                } else if ( xmas.isSelected ( ) ) {
                    sb.append ( " -sX " );
                }
            }

            //Adding SCTP Flags
            if ( sctpCheck.isSelected ( ) ) {
                if ( init.isSelected ( ) ) {
                    sb.append ( " -sY " );
                } else if ( echo.isSelected ( ) ) {
                    sb.append ( " -sZ " );
                }
            }

            //Ports Specification
            //TCP Ports


            if ( tcpPortsController != null ) {
                if ( tcpPortsController.getPorts ( ).length ( ) != 0 ) {
                    tcp = true;
                    ports += ( "T:" );
                    ports += ( tcpPortsController.getPorts ( ) );
                }
            }

            //UDP Ports
            if ( udpPortsController != null ) {
                if ( udpPortsController.getPorts ( ).length ( ) != 0 ) {
                    if ( tcp ) {
                        ports += ( "," );
                    }
                    ports += ( "U:" );
                    ports += ( udpPortsController.getPorts ( ) );
                    udp = true;
                }
            }

            //SCTP Ports
            if ( sctpPortsController != null ) {
                if ( sctpPortsController.getPorts ( ).length ( ) != 0 ) {
                    if ( tcp || udp ) {
                        ports += ( "," );
                    }
                    ports += ( "S:" );
                    ports += ( sctpPortsController.getPorts ( ) );
                    sctp = true;
                }
            }
        }

        if ( tcp || udp || sctp ) {
            sb.append ( " -p " + ports + " " );
        }

        if ( payloadController != null ) {
            if ( payloadController.getPayload ( ).length ( ) != 0 ) {
                sb.append ( payloadController.getPayload ( ) );
            }
        }
        return sb.toString ( );
    }

}
