package windows;

import Specs.ScanOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wintson on 3/26/17.
 */
public class AdvancedSettingsController {

    @FXML
    BorderPane mainBorderPane;

    ArrayList < FXMLLoader > loaderList = new ArrayList <> ( 200 );

    private OSDetectionController os = new OSDetectionController ( );
    private ServicesDetectionController sd = new ServicesDetectionController ( );
    private DNSOptionsController dns = new DNSOptionsController ( );
    private FirewallController firewall = new FirewallController ( );
    private DecoysController decoys = new DecoysController ( );
    private SpoofingAndChecksumController spoofing = new SpoofingAndChecksumController ( );
    private ScanOptions scanOptions = new ScanOptions ( );
    private ProtocolRangesController ranges = new ProtocolRangesController ( );
    private ScanTypesController scanTypes = new ScanTypesController ( );

    public void initialize ( ) throws IOException {
        //Protocol Ranges window
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "ProtocolRanges.fxml" ) ) );
        AnchorPane ProtocolRanges = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        ranges = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //Scan Types
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "ScanTypes.fxml" ) ) );
        BorderPane AdvancedTarget = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        scanTypes = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //Service Detection
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "ServicesDetection.fxml" ) ) );
        AnchorPane ServiceDetection = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        sd = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //OS Detection
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "OSDetection.fxml" ) ) );
        AnchorPane OSDetection = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        os = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //DNS OPTIONS
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "DNSOptionsController.fxml" ) ) );
        AnchorPane DNSOptions = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        dns = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //FIREWALL Options
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "Firewall.fxml" ) ) );
        AnchorPane FireWall = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        firewall = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //Decoys Options
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "Decoys.fxml" ) ) );
        AnchorPane Decoys = loaderList.get ( loaderList.size ( ) - 1 ).load ( );

        //SpoofAndChecksum
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "SpoofingAndChecksum.fxml" ) ) );
        AnchorPane SpoofAndChecksum = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        spoofing = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //scanOptions
        loaderList.add ( new FXMLLoader ( getClass ( ).getResource ( "ScanOptions.fxml" ) ) );
        AnchorPane ScanOptions = loaderList.get ( loaderList.size ( ) - 1 ).load ( );
        scanOptions = loaderList.get ( loaderList.size ( ) - 1 ).getController ( );

        //Creating the tree
        TreeItem < Hyperlink > root = new TreeItem ( "Settings" );
        root.setExpanded ( true );
        TreeView treeView = new TreeView ( root );
        //////IP Protocol Range
        Hyperlink ProtocolRangesLink = new Hyperlink ( "IP Protocols Range" );
        ProtocolRangesLink.setVisited ( true );
        TreeItem item = new TreeItem ( ProtocolRangesLink );
        root.getChildren ( ).add ( item );
        //////DNS Options
        Hyperlink DNSOptionsLink = new Hyperlink ( "DNS Options" );
        item = new TreeItem ( DNSOptionsLink );
        root.getChildren ( ).add ( item );
        //////Scan
        TreeItem < Hyperlink > ScanItem = new TreeItem ( "Scan" );
        ScanItem.setExpanded ( true );
        root.getChildren ( ).add ( ScanItem );
        //////Scan Type
        Hyperlink ScanTypeLink = new Hyperlink ( "Scan Type" );
        item = new TreeItem ( ScanTypeLink );
        ScanItem.getChildren ( ).add ( item );
        //////Scan Options
        Hyperlink ScanOptionsLink = new Hyperlink ( "Scan Options" );
        item = new TreeItem ( ScanOptionsLink );
        ScanItem.getChildren ( ).add ( item );

        //////OS & Version Detecion
        TreeItem < Hyperlink > OSItem = new TreeItem ( "Services & OS Detection" );
        OSItem.setExpanded ( true );
        root.getChildren ( ).add ( OSItem );
        //////Services & OS Options
        Hyperlink ServicesLink = new Hyperlink ( "Services Detection" );
        item = new TreeItem ( ServicesLink );
        OSItem.getChildren ( ).add ( item );
        //////Database Options
        Hyperlink OSDetect = new Hyperlink ( "OS Detection" );
        item = new TreeItem ( OSDetect );
        OSItem.getChildren ( ).add ( item );
        /////Firewall/IDS Evasion
        TreeItem < Hyperlink > FirewallIDSEvasion = new TreeItem ( "Firewall/IDS Evasion" );
        FirewallIDSEvasion.setExpanded ( true );
        root.getChildren ( ).add ( FirewallIDSEvasion );
        /////Firewall
        Hyperlink FirewallLink = new Hyperlink ( "Firewall" );
        item = new TreeItem ( FirewallLink );
        FirewallIDSEvasion.getChildren ( ).add ( item );
        //Decoys
        Hyperlink DecoysLink = new Hyperlink ( "Decoys" );
        item = new TreeItem ( DecoysLink );
        FirewallIDSEvasion.getChildren ( ).add ( item );
        //Spoofing and Checksum
        Hyperlink checksumLink = new Hyperlink ( "Spoofing and Checksum" );
        item = new TreeItem ( checksumLink );
        FirewallIDSEvasion.getChildren ( ).add ( item );


        mainBorderPane.setLeft ( treeView );
        mainBorderPane.setCenter ( ProtocolRanges );


        //Controllers
        ProtocolRangesLink.setOnAction ( event -> mainBorderPane.setCenter ( ProtocolRanges ) );
        DNSOptionsLink.setOnAction ( event -> mainBorderPane.setCenter ( DNSOptions ) );
        ScanTypeLink.setOnAction ( event -> mainBorderPane.setCenter ( AdvancedTarget ) );
        ServicesLink.setOnAction ( event -> mainBorderPane.setCenter ( ServiceDetection ) );
        OSDetect.setOnAction ( event -> mainBorderPane.setCenter ( OSDetection ) );
        FirewallLink.setOnAction ( event -> mainBorderPane.setCenter ( FireWall ) );
        DecoysLink.setOnAction ( event -> mainBorderPane.setCenter ( Decoys ) );
        checksumLink.setOnAction ( event -> mainBorderPane.setCenter ( SpoofAndChecksum ) );
        ScanOptionsLink.setOnAction ( event -> mainBorderPane.setCenter ( ScanOptions ) );
    }

    public OSDetectionController getOs ( ) {
        return os;
    }

    public ServicesDetectionController getSd ( ) {
        return sd;
    }

    public DNSOptionsController getDns ( ) {
        return dns;
    }

    public FirewallController getFirewall ( ) {
        return firewall;
    }

    public DecoysController getDecoys ( ) {
        return decoys;
    }

    public SpoofingAndChecksumController getSpoofing ( ) {
        return spoofing;
    }

    public ScanOptions getScanOptions ( ) {
        return scanOptions;
    }

    public ProtocolRangesController getRanges ( ) {
        return ranges;
    }

    public ScanTypesController getScanTypes ( ) {
        return scanTypes;
    }
}
