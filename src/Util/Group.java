package Util;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import windows.*;
import Specs.*;

/**
 * Created by wintson on 4/18/17.
 */
public class Group {

    Browser browser = new Browser ( );
    BrowserView view = new BrowserView ( browser );

    Stage groupStage = new Stage();

    String groupName;

    String outputLocationFilename;

    AdvancedSettingsController advancedScan;

    TargetSpec targets = new TargetSpec();

    FXMLLoader projectLoader = new FXMLLoader();

    BorderPane settingsWindow = new BorderPane();

    PortSpec ports = new PortSpec();

    private final Node groupIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/group.png")));


    public Group(String groupName) {
        view.setPadding ( new Insets ( 10,10,10,10 ) );
        this.groupName = groupName;
        try {
            init();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void init() throws Exception {
        try {
            settingsWindow = FXMLLoader.load(getClass().getResource("/windows/AdvancedSettings.fxml"));

        } catch (Exception e) {
            System.out.println(e);
        }
        groupStage.setScene(new Scene(settingsWindow, 700, 580));
        groupStage.setTitle(this.getGroupName()+"'s Settings" );
        System.out.println();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public TargetSpec getTargets() {
        return targets;
    }

    public void setTargets(TargetSpec targets) {
        this.targets = targets;
    }

    public Node getProjectIcon() {
        return groupIcon;
    }

    public Stage getSettingsStage() {
        return groupStage;
    }

    public String getOutputLocationFilename ( ) {
        return outputLocationFilename;
    }

    public void setOutputLocationFilename ( String outputLocationFilename ) {
        this.outputLocationFilename = outputLocationFilename;
    }

    public BrowserView getView ( ) {
        return view;
    }

    public void setView ( BrowserView view ) {
        this.view = view;
    }

    public Browser getBrowser ( ) {
        return browser;
    }

    public void setBrowser ( Browser browser ) {
        this.browser = browser;
    }

    @Override
    public String toString() {
        return this.groupName;
    }


}
