package Util;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import windows.*;
import MiddleMan.*;

import java.lang.annotation.Target;

/**
 * Created by wintson on 4/18/17.
 */
public class Group {
    String groupName;
    AdvancedSettingsController advancedScan;
    TargetSpec targets = new TargetSpec();
    PortSpec ports = new PortSpec();
    private final Node groupIcon = new ImageView(new Image(getClass().getResourceAsStream("/group.png")));

    public Group(String groupName) {
        this.groupName = groupName;
        advancedScan = new AdvancedSettingsController();
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

    @Override
    public String toString() {
        return this.groupName;
    }

    public Node getProjectIcon() {
        return groupIcon;
    }
}
