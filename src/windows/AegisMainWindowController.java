package windows;

//jxbrowserimports

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;

////

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.IOException;

/**
 * Created by wintson on 3/25/17.
 */
public class AegisMainWindowController {
    Stage targetSelection;
    TargetWindowController target = new TargetWindowController();
    boolean addCheck = true;

    @FXML
    MenuItem newProject;
    @FXML
    MenuItem loadScan;
    @FXML
    MenuItem saveScan;
    @FXML
    BorderPane mainBorderPane;
    Stage advancedSettingsStage;
    TreeItem leftRoot = new TreeItem("Scans");
    TreeView leftTree = new TreeView(leftRoot);

    @FXML
    public void initialize() {


        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);


        browser.loadURL("file:///home/wintson/Desktop/myFile.html");
        view.setPadding(new Insets(30, 30, 30, 10));
        mainBorderPane.setCenter(view);


        mainBorderPane.setLeft(leftTree);

        TreeItem<String> rightRoot = new TreeItem("Basic Options");
        TreeItem<String> ri1 = new TreeItem("Option 1");
        TreeItem<String> ri2 = new TreeItem("Option 2");
        rightRoot.getChildren().addAll(ri1, ri2);
        TreeView<String> righTree = new TreeView(rightRoot);
        leftRoot.setExpanded(true);
        mainBorderPane.setRight(righTree);

        //Handling a new Project

        leftTree.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                            if (selectedItem.getValue().equals("Add Scan")) {
                                addScan(selectedItem.getParent());
                            }
                    }
                });


        newProject.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newProject.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Stage projectStage = new Stage();
                newProjectController projectController;
                FXMLLoader projectLoader = new FXMLLoader();
                projectLoader.setLocation(getClass().getResource("newProject.fxml"));

                try {
                    projectLoader.load();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
                Parent projectRoot = projectLoader.getRoot();
                projectStage.setScene(new Scene(projectRoot, 406, 150));
                projectStage.showAndWait();
                projectController = projectLoader.getController();
                if (projectController.getProjectText() != null) {
                    TreeItem temp = new TreeItem(projectController.getProjectText().getText());
                    TreeItem add = new TreeItem("Add Scan");
                    leftRoot.getChildren().add(temp);
                    temp.getChildren().add(add);
                    temp.setExpanded(true);

                }

            }
        });



        //Handling a new Scan. (Shortcut supported)
        /*newScan.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                targetSelection = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("TargetWindow.fxml"));
                    loader.load();
                    root = loader.getRoot();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
                targetSelection.setScene(new Scene(root, 385, 357));
                targetSelection.showAndWait();
                target = loader.getController();
                    for (Object x : target.getIpList().getItems()) {
                        leftRoot.getChildren().add(new TreeItem((String) x));
                    }
            }
        });*/


    }

    @FXML
    private void openSettings() throws IOException {
        if (advancedSettingsStage == null) {
            advancedSettingsStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AdvancedSettings.fxml"));
            advancedSettingsStage.setScene(new Scene(root, 700, 580));
        }
        advancedSettingsStage.show();
    }


    public void addScan(TreeItem<String> item){

                targetSelection = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("TargetWindow.fxml"));
                    loader.load();
                    root = loader.getRoot();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
                targetSelection.setScene(new Scene(root, 385, 357));
                targetSelection.showAndWait();
                target = loader.getController();
                    for (Object x : target.getIpList().getItems()) {
                        item.getChildren().add(new TreeItem((String) x));
                    }
            }

    }

