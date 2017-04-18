package Util;

import windows.*;

/**
 * Created by wintson on 4/18/17.
 */
public class Group {
    String groupName;
    AdvancedSettingsController advancedScan;



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


    @Override
    public String toString() {
        return this.groupName;
    }
}
