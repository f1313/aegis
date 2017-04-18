package Util;

import javafx.scene.control.TreeItem;
import windows.*;
import java.util.ArrayList;

/**
 * Created by wintson on 4/18/17.
 */
public class Project extends TreeItem{
    char c = 'P';
    String projectName;
    ArrayList<Group> groupList;

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

    @Override
    public String toString() {
        return this.projectName;
    }
}


