package windows;

import Util.*;

////


////

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import sun.reflect.generics.tree.Tree;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wintson on 3/25/17.
 */
public class AegisMainWindowController {
    HBox projectsH = new HBox();
    static boolean projectClosed = false;
    static boolean groupClosed = false;
    ArrayList<Hyperlink> hyperList = new ArrayList<>(30);
    public static ArrayList<Project> projectsList = new ArrayList();
    public static AegisMainWindowController aegisMainWindowController;

    //Popup error when nothing is selected
    Alert NoSelectedAlert = new Alert(Alert.AlertType.ERROR);

    //Temp arrayList to store dummy objects that contain icons
    ArrayList<TreeItem> list = new ArrayList<>();

    Stage targetSelection;
    TargetWindowController target = new TargetWindowController();
    //VBox leftVBox = new VBox();
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
        /// Projects Context Menu

        //Initializing the projects hbox in the advanced scan settings

        initHB();

        //Initializing the error popup when nothing is selected
        initPopup();

        ContextMenu rootContext = new ContextMenu();
        MenuItem newProjectMenuItem = new MenuItem("New Project");
        rootContext.getItems().add(0, newProjectMenuItem);
        ContextMenu projectsContext = new ContextMenu();
        MenuItem newGroupMenuItem = new MenuItem("New Group");
        projectsContext.getItems().add(0, newGroupMenuItem);
        ///
        //Setting a handler for each context menu
        newProjectMenuItem.setOnAction(event -> addProject());
        newGroupMenuItem.setOnAction(event -> addGroup((TreeItem) leftTree.getSelectionModel().getSelectedItems().get(0)));
        //Checking for right click
        mainBorderPane.setLeft(leftTree);
        leftTree.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                rootContext.hide();
                projectsContext.hide();
                if (event.getButton() == MouseButton.SECONDARY) {
                    if (leftTree.getSelectionModel().getSelectedItem() != null) {
                        if (isRoot((TreeItem) leftTree.getSelectionModel().getSelectedItem())) {
                            rootContext.show(mainBorderPane, event.getScreenX(), event.getScreenY());
                        } else if (isProject((TreeItem) leftTree.getSelectionModel().getSelectedItem())) {
                            projectsContext.show(mainBorderPane, event.getScreenX(), event.getScreenY());
                        }
                    }
                } else {
                    rootContext.hide();
                }
            }
        });

        TreeItem<String> rightRoot = new TreeItem("Basic Options");
        TreeItem<String> ri1 = new TreeItem("Option 1");

        TreeItem<String> ri2 = new TreeItem("Option 2");
        rightRoot.getChildren().addAll(ri1, ri2);
        TreeView<String> righTree = new TreeView(rightRoot);

        leftRoot.setExpanded(true);
        mainBorderPane.setRight(righTree);

        newProject.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newProject.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                addProject();
            }
        });

        aegisMainWindowController = this;
    }


    //Adds a new project to the tree
    private void addProject() {
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
        Project p = new Project(projectController.getProjectText().getText());

        if (p.getProjectName() != null && projectClosed) {
            TreeItem temp = new TreeItem(projectController.getProjectText().getText(), p.getProjectIcon());
            leftRoot.getChildren().add(temp);


            Hyperlink adder = new Hyperlink("+ Add Group");
            temp.getChildren().add(new TreeItem<Hyperlink>(adder));
            adder.setOnAction(event -> {
                addGroup(temp);
                adder.setVisited(false);
            });

            projectsList.add(p);
            temp.setExpanded(true);
            projectClosed = false;
        }
    }


    @FXML
    private void openSettings() throws IOException {
        advancedSettingsStage = new Stage();
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

        if (isGroup((TreeItem) (leftTree.getSelectionModel().getSelectedItem()))) {
            findGroup((TreeItem) (leftTree.getSelectionModel().getSelectedItem())).getSettingsStage()
                    .show();
        }else {
            NoSelectedAlert.show();
        }
    }



    @FXML
    private void manageGroups() {

    }

    public void addGroup(TreeItem parent) {
        Parent root = new BorderPane();
        targetSelection = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("TargetWindow.fxml"));
            loader.load();
            root = loader.getRoot();
        } catch (IOException exception) {
            System.out.println(exception);
        }


        targetSelection.setScene(new Scene(root, 385, 485));
        targetSelection.showAndWait();

        if (groupClosed) {
            target = loader.getController();
            String temp = target.getGroupName();
            Group g = new Group(temp);

            TreeItem toAdd;
            if (parent.getChildren().size() == 0) {
                toAdd = (TreeItem) parent.getChildren().remove(0);
            } else {
                toAdd = (TreeItem) parent.getChildren().remove(parent.getChildren().size() - 1);
            }

            TreeItem newGroup = new TreeItem<>(g.getGroupName(), g.getProjectIcon());
            parent.getChildren().add(newGroup);
            parent.getChildren().add(toAdd);
            target = loader.getController();
            int counter = 0;
            for (Object x : target.getIpList().getItems()) {
                g.getTargets().getIncludedTargetsList().add(x.toString());
                newGroup.getChildren().add(new TreeItem<>(x, new ImageView(new Image
                        (getClass().getResourceAsStream("/img/target.png")))));
                counter++;
            }

            newGroup.setExpanded(true);
            projectsList.get(findProject(parent)).addGroup(g);
        }
        groupClosed = false;

    }

    public static boolean proExists(String p) {
        for (Project x : projectsList) {
            if (p.equals(x.getProjectName())) {
                return true;
            }
        }
        return false;
    }

    public int findProject(TreeItem parent) {
        int counter = 0;
        //TreeItem [ value: alpha ]
        System.out.println(parent.toString());
        for (Project x : projectsList) {
            if (("TreeItem [ value: " + x.getProjectName() + " ]").equals(parent.toString())) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public boolean isRoot(TreeItem item) {
        try {
            item.getParent();
        }
        catch (Exception e){
            return true;
        }
        return false;
    }

    public boolean isProject(TreeItem item) {
        if (!isRoot(item)){
            try {
                item.getParent().getParent().getParent();
            }catch (Exception e){
                return true;
            }
        }
        return false;
    }

    public boolean isGroup(TreeItem item) {
        if (item.toString().contains("+ Add Group")) return false;
        if (!isRoot(item) && !isProject(item)){
            try {
                item.getParent().getParent().getParent().getParent();
            }catch (Exception e){
                return true;
            }
        }
        return false;
    }

    private void initHB() {
        Label l = new Label("Project: ");
        ChoiceBox pc = new ChoiceBox();
        pc.getItems().addAll(leftRoot.getChildren());
        projectsH.getChildren().addAll(l, pc);
    }

    public Project getSelectedProject(TreeItem item) {
        for (Project x : projectsList) {
            if (("TreeItem [ value: " + x.getProjectName() + " ]").equals(item.toString())) {
                return x;
            }
        }
        return null;
    }


    private void initPopup() {

        NoSelectedAlert.setTitle("Error");
        NoSelectedAlert.setHeaderText("No Group Selected");
        NoSelectedAlert.setContentText("Select a group from the scans tree!");

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

    public Group findGroup(TreeItem item){
        for (Group x : getSelectedProject(item.getParent()).getGroupList()){
            if (x.getGroupName().equals((String)item.getValue())) return x;
        }
        return null;
    }

}
