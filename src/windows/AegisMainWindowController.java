package windows;

import Util.*;

////


////

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by wintson on 3/25/17.
 */
public class AegisMainWindowController {
    HBox projectsH = new HBox ( );
    HBox bottomHbox = new HBox ( );
    static boolean projectClosed = false;
    static boolean groupClosed = false;
    ArrayList < Hyperlink > hyperList = new ArrayList <> ( 30 );
    public static ArrayList < Project > projectsList = new ArrayList ( );
    public static AegisMainWindowController aegisMainWindowController;//OLD XX
    public static Label progressLabel = new Label ( );
    public static ProgressBar progressBar = new ProgressBar ( 0 );

    HashSet < String > runningScans = new HashSet ( 100 );
    HashMap < String, Process > processes = new HashMap <> ( 100 );
    //Popup error when nothing is selected
    Alert NoSelectedAlert = new Alert ( Alert.AlertType.ERROR );

    //Temp arrayList to store dummy objects that contain icons
    ArrayList < TreeItem > list = new ArrayList <> ( );

    Stage targetSelection;
    TargetWindowController target = new TargetWindowController ( );
    //VBox leftVBox = new VBox();
    @FXML
    MenuItem newProject;
    @FXML
    MenuItem loadScan;
    @FXML
    MenuItem saveScan;
    @FXML
    MenuItem exitItem;
    @FXML
    MenuItem quickScan;
    @FXML
    BorderPane mainBorderPane;
    TabPane tp = new TabPane ( );

    Stage advancedSettingsStage;
    TreeItem leftRoot = new TreeItem ( "Scans" );
    TreeView leftTree = new TreeView ( leftRoot );


    Browser browser = new Browser ( );
    BrowserView view = new BrowserView ( browser );

    @FXML
    public void initialize ( ) {
        view.setPadding ( new Insets ( 10, 10, 10, 10 ) );
        mainBorderPane.setCenter ( tp );
        bottomHbox.setStyle ( "-fx-border-color: #c8c8c8" );

        //Adding ListView to the right

        /// Projects Context Menu

        //Here we check if a group is selected so we can display the report

        bottomHbox.getChildren ( ).addAll ( progressBar, progressLabel );
        progressBar.setPadding ( new Insets ( 7, 7, 7, 10 ) );
        progressBar.setMinWidth ( 252 );

        mainBorderPane.setBottom ( bottomHbox );
        initQuick ( );

        initHB ( );

        //Initializing the error popup when nothing is selected
        initPopup ( );

        ContextMenu rootContext = new ContextMenu ( );
        MenuItem newProjectMenuItem = new MenuItem ( "New Project" );
        rootContext.getItems ( ).add ( 0, newProjectMenuItem );
        ContextMenu projectsContext = new ContextMenu ( );
        MenuItem newGroupMenuItem = new MenuItem ( "New Group" );
        ContextMenu startScan = new ContextMenu ( );
        MenuItem scan = new MenuItem ( "Start Scan" );
        MenuItem kill = new MenuItem ( "Kill Scan" );
        MenuItem CVEItem = new MenuItem ( "CVEDetails Report" );
        MenuItem advancedOptionsMenuItem = new MenuItem ( "Advanced Scan Options" );
        startScan.getItems ( ).addAll ( scan, advancedOptionsMenuItem, CVEItem, kill );
        projectsContext.getItems ( ).add ( 0, newGroupMenuItem );
        ///
        //Setting a handler for each context menu
        newProjectMenuItem.setOnAction ( event -> {
            addProject ( );
        } );
        newGroupMenuItem.setOnAction ( event -> {
            addGroup ( ( TreeItem ) leftTree.getSelectionModel ( ).getSelectedItems ( ).get ( 0 ) );
        } );
        CVEItem.setOnAction ( event -> {
            Group g = findGroup ( ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) ) );
            if ( g.getAdvancedScan ( ).getSd ( ).isReady ( ) && g.isComplete ( ) ) {
                final Service thread2 = new Service < Integer > ( ) {
                    @Override
                    public Task createTask ( ) {
                        return new Task < Integer > ( ) {
                            @Override
                            protected Integer call ( ) throws Exception {
                                g.getCveDetailsItem ( ).pre ( g );
                                return null;
                            }
                        };
                    }
                };
                if ( ! g.isCveDetailsItemCheck ( ) ) {
                    thread2.start ( );
                    g.setCveDetailsItemCheck ( true );
                }
                g.getCveDetailsItem ( ).stage.show ( );

            } else {
                Alert alert = new Alert ( Alert.AlertType.ERROR );
                alert.setTitle ( "Scan Not Performed" );
                alert.setHeaderText ( "No scan started or CVEDetails option not selected." );
                alert.setContentText ( "Check the 'CVEDetails' option in advanced scan options and start the scan" );
                alert.showAndWait ( );
            }
        } );


        //Checking for right click
        mainBorderPane.setLeft ( leftTree );


        //Listening for when the scan is started
        scan.setOnAction ( event -> {

            Group g = findGroup ( ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) ) );
            if ( runningScans.contains ( g.getGroupName ( ) ) ) {
                g.getCveDetailsItem ( ).killSearch ( );
                processes.remove ( g.getGroupName ( ) );
            }
            leftClick ( g );
            String command = "nmap " + g.getHostsString ( ) + " ";

            command += g.getAdvancedScan ( ).getOs ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getSd ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getDns ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getScanTypes ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getScanOptions ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getFirewall ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getDecoys ( ).getCommand ( );
            command += g.getAdvancedScan ( ).getSpoofing ( ).getCommand ( );

            System.out.println ( command );
            scan ( g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ), command, g );
        } );

        //When a kill request is issued
        kill.setOnAction ( event -> {
            Group g = findGroup ( ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) ) );
            if ( processes.containsKey ( g.getGroupName ( ) ) ) {
                processes.get ( g.getGroupName ( ) ).destroyForcibly ( );
                processes.remove ( g.getGroupName ( ) );
                runningScans.remove ( g.getGroupName ( ) );
                progressBar.setProgress ( 0 );
                Alert killed = new Alert ( Alert.AlertType.INFORMATION );
                killed.setTitle ( "Scan Killed" );
                killed.setHeaderText ( "Scan Successfully Killed" );
                g.getBrowser ( ).loadURL ( "file:///" + System.getProperty ( "user.dir" )
                        + "/out/production/Aegis/styles/notStarted.html" );
                killed.show ( );
            } else {
                Alert running = new Alert ( Alert.AlertType.ERROR );
                running.setTitle ( "Scan Not Runnung" );
                running.setHeaderText ( "This scan has not been initiated yet" );
                running.show ( );
            }
        } );

        advancedOptionsMenuItem.setOnAction ( event -> {
            try {
                openSettings ( );
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        } );

        //Handling clicking left tree element
        //When a right click is made
        leftTree.setOnMouseClicked ( new javafx.event.EventHandler < javafx.scene.input.MouseEvent > ( ) {
            @Override
            public void handle ( javafx.scene.input.MouseEvent event ) {

                //A right click !!!
                rootContext.hide ( );
                startScan.hide ( );
                if ( event.getButton ( ) == MouseButton.SECONDARY ) {
                    if ( leftTree.getSelectionModel ( ).getSelectedItem ( ) != null ) {
                        if ( isRoot ( ( TreeItem ) leftTree.getSelectionModel ( ).getSelectedItem ( ) ) ) {
                            rootContext.show ( mainBorderPane, event.getScreenX ( ), event.getScreenY ( ) );
                        } else if ( isGroup ( ( TreeItem ) leftTree.getSelectionModel ( ).getSelectedItem ( ) ) ) {
                            startScan.show ( mainBorderPane, event.getScreenX ( ), event.getScreenY ( ) );
                        }
                    }
                } else {

                    //A left click !!
                    TreeItem < String > selectedItem = ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) );
                    if ( isGroup ( selectedItem ) ) {
                        Group g = findGroup ( selectedItem );
                        leftClick ( g );
                    }

                }
            }
        } );
        //When a left click is made


        leftRoot.setExpanded ( true );

        newProject.setAccelerator ( new KeyCodeCombination ( KeyCode.N, KeyCombination.CONTROL_DOWN ) );
        quickScan.setAccelerator ( new KeyCodeCombination ( KeyCode.Q, KeyCombination.CONTROL_DOWN ) );
        //Too dangerous
        //exitItem.setAccelerator ( new KeyCodeCombination ( KeyCode.O, KeyCombination.CONTROL_DOWN ) );
        newProject.setOnAction ( new javafx.event.EventHandler < javafx.event.ActionEvent > ( ) {
            @Override
            public void handle ( javafx.event.ActionEvent event ) {
                addProject ( );
            }
        } );

        aegisMainWindowController = this;


    }


    //Adds a new project to the tree
    private void addProject ( ) {
        Stage projectStage = new Stage ( );
        newProjectController projectController;
        FXMLLoader projectLoader = new FXMLLoader ( );
        projectLoader.setLocation ( getClass ( ).getResource ( "newProject.fxml" ) );
        projectStage.setTitle ( "New Project" );

        try {
            projectLoader.load ( );
        } catch ( IOException exception ) {
            System.out.println ( exception );
        }

        Parent projectRoot = projectLoader.getRoot ( );
        projectStage.setScene ( new Scene ( projectRoot, 406, 160 ) );

        projectStage.showAndWait ( );
        projectController = projectLoader.getController ( );
        Project p = new Project ( projectController.getProjectText ( ).getText ( ) );

        if ( p.getProjectName ( ) != null && projectClosed ) {
            TreeItem temp = new TreeItem ( projectController.getProjectText ( ).getText ( ), p.getProjectIcon ( ) );
            leftRoot.getChildren ( ).add ( temp );


            Hyperlink adder = new Hyperlink ( "+ Add Group" );
            temp.getChildren ( ).add ( new TreeItem < Hyperlink > ( adder ) );
            adder.setOnAction ( event -> {
                addGroup ( temp );
                adder.setVisited ( false );
            } );

            String path = projectController.getLocationText ( ).getText ( );
            if ( path.isEmpty ( ) ) {
                path = "/" + p.getProjectName ( );
            } else {
                path += "/" + p.getProjectName ( );
            }
            p.setFilePath ( path );
            projectsList.add ( p );
            File pj = new File ( p.getFilePath ( ) );
            try {
                pj.mkdir ( );
            } catch ( Exception ex ) {
                ex.printStackTrace ( );
            }
            temp.setExpanded ( true );
            projectClosed = false;
        }
    }

    @FXML
    private void openSettings ( ) throws IOException {
        advancedSettingsStage = new Stage ( );
        //If nothing is selected
//        if (leftTree.getSelectionModel().getSelectedItem() == null) {
//            advancedSettingsStage = new Stage();
//            BorderPane root;
//            root = FXMLLoader.load(getClass().getResource("AdvancedSettings.fxml"));
//            root.setTop(projectsH);
//            advancedSettingsStage.setScene(new Scene(root, 700, 580));
//            advancedSettingsStage.setTitle ("Settings");
//            advancedSettingsStage.show();
//        } else {
//            if (isRoot((TreeItem) (leftTree.getSelectionModel().getSelectedItem()))){
//                noSelectedPopUp.show();
//            }
//            else if (isProject((TreeItem) (leftTree.getSelectionModel().getSelectedItem()))) {
//                    getSelectedProject((TreeItem)(leftTree.getSelectionModel().getSelectedItem())).getGroupList().get(0)
//                            .getSettingsStage().show();
//            }
//        }

        if ( isGroup ( ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) ) ) ) {
            findGroup ( ( TreeItem ) ( leftTree.getSelectionModel ( ).getSelectedItem ( ) ) ).getSettingsStage ( )
                    .show ( );
        } else {
            NoSelectedAlert.show ( );
        }
    }

    @FXML
    private void manageGroups ( ) {

    }

    public void leftClick ( Group g ) {
        File checker = new File ( g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ) + ".html" );
        System.out.println ( "Checker location : " + checker.getPath ( ) );
        int loc = tabPaneContains ( g.getGroupName ( ) );
        System.out.println ( "Location is: " + loc );
        //If the scan is done
        if ( checker.exists ( ) ) {
            //If the tab is never opened
            if ( loc == - 1 ) {
                tp.getTabs ( ).add ( new Tab ( g.getGroupName ( ) ) );
                tp.getSelectionModel ( ).select ( tp.getTabs ( ).size ( ) - 1 );
                tp.getTabs ( ).get ( tp.getTabs ( ).size ( ) - 1 ).setContent ( g.getView ( ) );
            } else {
                tp.getSelectionModel ( ).select ( loc );
                g.getBrowser ( ).loadURL ( "file:///" + g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ) + ".html" );
                tp.getTabs ( ).get ( loc ).setContent ( g.getView ( ) );
            }
        } else {
            if ( loc != - 1 ) {
                tp.getSelectionModel ( ).select ( loc );
                if ( runningScans.contains ( g.getGroupName ( ) ) ) {
                    g.getBrowser ( ).loadURL ( "file:///" + System.getProperty ( "user.dir" ) + "/out/production/Aegis/styles/loadingAnimation.html" );

                } else {
                    g.getBrowser ( ).loadURL ( "file:///" + System.getProperty ( "user.dir" ) + "/out/production/Aegis/styles/notStarted.html" );
                }
                tp.getTabs ( ).get ( loc ).setContent ( g.getView ( ) );
            } else {
                tp.getTabs ( ).add ( new Tab ( g.getGroupName ( ) ) );
                tp.getSelectionModel ( ).select ( tp.getTabs ( ).size ( ) - 1 );
                g.getBrowser ( ).loadURL ( "file:///" + System.getProperty ( "user.dir" ) + "/out/production/Aegis/styles/notStarted.html" );
                tp.getTabs ( ).get ( tp.getTabs ( ).size ( ) - 1 ).setContent ( g.getView ( ) );

            }
        }
    }

    @FXML
    private void exitProc ( ) {

        if ( processes.size ( ) != 0 ) {
            Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
            alert.setTitle ( "Scans in progress" );
            alert.setHeaderText ( "Some scans are still running.\n Are you sure you want to exit?" );
            String procs = "";
            for ( String x : processes.keySet ( ) ) {
                procs += "-" + x + "\n";
            }
            alert.setContentText ( "Scans running:\n" + procs );
            alert.showAndWait ( );
            if ( alert.getResult ( ) == ButtonType.OK ) {
                for ( String x : processes.keySet ( ) ) {
                    processes.get ( x ).destroyForcibly ( );
                }
                System.exit ( 0 );
            }
        } else {
            System.exit ( 0 );
        }
    }

    public void addGroup ( TreeItem parent ) {
        Parent root = new BorderPane ( );
        targetSelection = new Stage ( );
        FXMLLoader loader = new FXMLLoader ( );
        try {
            loader.setLocation ( getClass ( ).getResource ( "TargetWindow.fxml" ) );
            loader.load ( );
            root = loader.getRoot ( );
        } catch ( IOException exception ) {
            System.out.println ( exception );
        }

        targetSelection.setTitle ( "New Group" );
        targetSelection.setScene ( new Scene ( root, 385, 500 ) );
        targetSelection.showAndWait ( );

        if ( groupClosed ) {
            target = loader.getController ( );
            String temp = target.getGroupName ( );
            Group g = new Group ( temp );

            TreeItem toAdd;
            if ( parent.getChildren ( ).size ( ) == 0 ) {
                toAdd = ( TreeItem ) parent.getChildren ( ).remove ( 0 );
            } else {
                toAdd = ( TreeItem ) parent.getChildren ( ).remove ( parent.getChildren ( ).size ( ) - 1 );
            }

            TreeItem newGroup = new TreeItem <> ( g.getGroupName ( ), g.getProjectIcon ( ) );
            parent.getChildren ( ).add ( newGroup );
            parent.getChildren ( ).add ( toAdd );
            target = loader.getController ( );
            g.setOutputLocationFilename ( target.getFileText ( ) + "/" + target.getGroupName ( ) );
            for ( Object x : target.getIpList ( ).getItems ( ) ) {
                g.getTargets ( ).getIncludedTargetsList ( ).add ( x.toString ( ) );
                newGroup.getChildren ( ).add ( new TreeItem <> ( x, new ImageView ( new Image
                        ( getClass ( ).getResourceAsStream ( "/img/target.png" ) ) ) ) );

            }
            g.setOutputLocationFilename ( getSelectedProject ( parent ).getFilePath ( ) + "/" + g.getGroupName ( ) );
            new File ( g.getOutputLocationFilename ( ) ).mkdir ( );
            newGroup.setExpanded ( true );
            g.initHostString ( );
            projectsList.get ( findProject ( parent ) ).addGroup ( g );
            g.getTargets ( ).writeIncludedTargetsToFile ( g.getOutputLocationFilename ( ) + "/targets.targets" );
        }
        groupClosed = false;

    }

    public static boolean proExists ( String p ) {
        for ( Project x : projectsList ) {
            if ( p.equals ( x.getProjectName ( ) ) ) {
                return true;
            }
        }
        return false;
    }

    public int findProject ( TreeItem parent ) {
        int counter = 0;
        //TreeItem [ value: alpha ]
        System.out.println ( parent.toString ( ) );
        for ( Project x : projectsList ) {
            if ( ( "TreeItem [ value: " + x.getProjectName ( ) + " ]" ).equals ( parent.toString ( ) ) ) {
                return counter;
            }
            counter++;
        }
        return - 1;
    }

    public boolean isRoot ( TreeItem item ) {
        try {
            item.getParent ( );
        } catch ( Exception e ) {
            return true;
        }
        return false;
    }

    public boolean isProject ( TreeItem item ) {
        if ( ! isRoot ( item ) ) {
            try {
                if ( item.getParent ( ).getParent ( ) == null ) {
                    return true;
                }
            } catch ( Exception e ) {
                return true;
            }
        }
        return false;
    }

    public boolean isGroup ( TreeItem item ) {
        if ( item == null || item.toString ( ).contains ( "+ Add Group" ) ) return false;
        if ( ! isRoot ( item ) && ! isProject ( item ) ) {
            try {
                if ( item.getParent ( ).getParent ( ).getParent ( ) == null ) {
                    return true;
                }
            } catch ( Exception e ) {
                return true;
            }
        }
        return false;
    }

    private void initHB ( ) {
        Label l = new Label ( "Project: " );
        ChoiceBox pc = new ChoiceBox ( );
        pc.getItems ( ).addAll ( leftRoot.getChildren ( ) );
        projectsH.getChildren ( ).addAll ( l, pc );
    }

    public Project getSelectedProject ( TreeItem item ) {
        for ( Project x : projectsList ) {
            if ( ( "TreeItem [ value: " + x.getProjectName ( ) + " ]" ).equals ( item.toString ( ) ) ) {
                return x;
            }
        }
        return null;
    }

    private void initPopup ( ) {

        NoSelectedAlert.setTitle ( "Error" );
        NoSelectedAlert.setHeaderText ( "No Group Selected" );
        NoSelectedAlert.setContentText ( "Select a group from the scans tree!" );

        /*Button ok = new Button("Ok");
        ok.setMinSize(50.0, 23.0);
        ok.setOnMouseClicked(event -> noSelectedPopUp.close());
        ok.setPadding(new Insets(4, 4, 4, 4));
        Label no = new Label("No group selected");
        no.setPadding(new Insets(0, 0, 10, 0));
        VBox vbox = new VBox(no, ok);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));
        noSelectedPopUp.setScene(new Scene(vbox));*/

    }

    public Group findGroup ( TreeItem item ) {
        for ( Group x : getSelectedProject ( item.getParent ( ) ).getGroupList ( ) ) {
            if ( x.getGroupName ( ).equals ( item.getValue ( ) ) ) return x;
        }
        return null;
    }

    private void initQuick ( ) {

        quickScan.setOnAction ( event -> {
            String temp = "";
            //new Specs.TargetSpec ( ).validateHostString ( temp )
            TextInputDialog dialog = new TextInputDialog ( "8.8.8.8" );
            dialog.setTitle ( "َQuick Scan" );
            dialog.setHeaderText ( "A quick default nmap scan" );
            dialog.setContentText ( "Enter ip or hostname (Masks are accepted): " );
            dialog.setOnCloseRequest ( e -> System.out.println ( "Closed!!" ) );
            Optional < String > result = dialog.showAndWait ( );
            if ( result.isPresent ( ) ) {
                System.out.println ( ".get is " + result.get ( ) );
                if ( Specs.TargetSpec.validateHostString ( result.get ( ) ) ) {
                    tp.getTabs ( ).add ( new Tab ( "Quick Scan" ) );
                    tp.getSelectionModel ( ).select ( tp.getTabs ( ).size ( ) - 1 );
                    Group toSend = new Group ( "Quick" );
                    //TODO: Set to global
                    scan ( "/home/wintson/Desktop/scanThis/meh", "nmap -O " + result.get ( ), toSend );
                } else {
                    Alert alert = new Alert ( Alert.AlertType.ERROR );
                    alert.setTitle ( "Invalid String" );
                    alert.setHeaderText ( "Wrong IP/Hostname" );
                    alert.setContentText ( "Re-enter a valid IP/Hostname" );
                    alert.showAndWait ( );
                }
            }
        } );

        // The Java 8 way to get the response value (with lambda expression).
//        result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    private void scan ( String target, String command, Group group ) {
        /*This is an old bad code*/
        /*new Thread ( new Runnable ( ) {
            @Override
            public void run ( ) {
                System.out.println ( "My dir is :" + System.getProperty ( "user.dir" ) );
//What used to be wrong: took us 2 hours to go from this to what is below that works
//        String command = "cd /home/wintson/Desktop/quickScan\n" +
//                "rm quickFile*\n" +
//                "nmap " + target + " -oX /home/wintson/Desktop/quickScan/quickFile.xml" +
//                "\nxsltproc quickFile.xml -o quickFile.html" +
//                "\nmv quickFile.html quickFiles.html\n";
//        System.out.println("Thecommand is :" + command);

            }
        } ).start ( );*/


        final Service < Integer > thread = new Service < Integer > ( ) {
            @Override
            public Task createTask ( ) {
                return new Task < Integer > ( ) {
                    @Override
                    protected Integer call ( ) throws Exception {
                        //Code
                        runningScans.add ( group.getGroupName ( ) );
                        try {
                            new File ( target + ".xml" ).delete ( );
                            new File ( target + ".html" ).delete ( );
//                            System.out.println ( "sudo " + command + " -oX " + target + ".xml --stats-every 100ms" );
                            if ( group.getAdvancedScan ( ).getSd ( ).isEnabled ( ) ) {
                                group.getAdvancedScan ( ).getSd ( ).setReady ( true );
                            } else {
                                group.getAdvancedScan ( ).getSd ( ).setReady ( false );
                            }

                            group.setComplete ( false );
                            String interval = "500ms";
                            if ( command.contains ( "firewalk" ) ) {
                                interval = "500ms";
                            }
                            Process p = Runtime.getRuntime ( ).exec ( "sudo " + command + " -oX " + target +
                                    ".xml --stats-every " + interval );
                            processes.put ( group.getGroupName ( ), p );
                            group.getBrowser ( ).loadURL ( "file:///" + System.getProperty ( "user.dir" ) + "/out/production/Aegis/styles/loadingAnimation.html" );
                            Percentage percentage = new Percentage ( p );
                            percentage.work ( );
                            p.waitFor ( );
                            System.setIn ( p.getInputStream ( ) );
                            Scanner input = new Scanner ( System.in );
                            while ( input.hasNextLine ( ) ) {
                                System.out.println ( input.nextLine ( ) );
                            }
                            p = Runtime.getRuntime ( ).exec ( "xsltproc " + target + ".xml" +
                                    " -o " + target + ".html" );
                            p.waitFor ( );
                            System.setIn ( p.getInputStream ( ) );
                            input = new Scanner ( System.in );
                            while ( input.hasNextLine ( ) ) {
                                System.out.println ( input.nextLine ( ) );
                            }
                            //Turns out we don't need this any more
//                        Thread.currentThread ( ).join ( 700 );
                        } catch ( IOException | InterruptedException ex ) {
                            System.out.println ( ex );
                        }
                        group.getBrowser ( ).loadURL ( "file://" + target + ".html" );
                        group.setComplete ( true );
                        progressBar.setProgress ( 0 );
                        runningScans.remove ( group.getGroupName ( ) );
                        processes.remove ( group.getGroupName ( ) );
                        //End Code
                        return null;
                    }
                };
            }
        };

        if ( runningScans.contains ( group.getGroupName ( ) ) ) {
            Alert running = new Alert ( Alert.AlertType.ERROR );
            running.setTitle ( "Error" );
            running.setHeaderText ( "Scan Already Running" );
            running.setContentText ( "Wait for the scan to finish or stop the scan." );
            running.show ( );
        } else {
            group.resetCVE ( );
            thread.start ( );
        }

        tp.getSelectionModel ( ).getSelectedItem ( ).setContent ( group.getView ( ) );
    }

    private int tabPaneContains ( String tabName ) {
        for ( int i = 0 ; i < tp.getTabs ( ).size ( ) ; i++ ) {
            if ( tp.getTabs ( ).get ( i ).getText ( ).equals ( tabName ) ) return i;
        }


        return - 1;
    }

    public HashMap < String, Process > getProcesses ( ) {
        return processes;
    }
}
