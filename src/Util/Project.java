package Util;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import windows.*;
import java.util.ArrayList;

/**
 * Created by wintson on 4/18/17.
 */
public class Project extends TreeItem{
    char c = 'P';
    String projectName;
    ArrayList<Group> groupList;
    private final Node projectIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/project.png")));
    public Project(String projectName) {
        this.projectName = projectName;
        groupList = new ArrayList();
    }

    public boolean hasItem(String item){
        for(Group g : groupList){
            if (g.getGroupName().equals(item)){
                return true;
            }
        }
        return false;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return this.projectName;
    }

    public void addGroup(Group g){
        groupList.add(g);
    }

    public Node getProjectIcon() {
        return projectIcon;
    }
}


