package MiddleMan;

public class HostSpec {

    //included targets
    private boolean isIncudedTargetsConfigured;
    private java.util.ArrayList<String> includedTargetsList;
    private java.util.ArrayList<String> defaultIncludedTargetsList;
    private boolean isIncludedTargetsLoadedFromFile;
    private java.io.File includedTargetsFile;

    //excluded targets
    private boolean isExcludedTargetsConfigured;
    private java.util.ArrayList<String> excludedTargetsList;
    private java.util.ArrayList<String> defaultExcludedTargetsList;
    private boolean isExcludedTargetsLoadedFromFile;
    private java.io.File excludedTargetsFile;

    //random hosts
    private boolean isRandomHostsEnabled;
    private int randomHostsCount;

    /**
     *
     * @return
     */
    public java.util.ArrayList<String> getDefaultExcludedTargetsList () {
        return defaultExcludedTargetsList;
    }

    /**
     *
     * @param defaultExcludedTargetsList
     */
    public void setDefaultExcludedTargetsList ( java.util.ArrayList<String> defaultExcludedTargetsList ) {
        this.defaultExcludedTargetsList = defaultExcludedTargetsList;
    }

    /**
     *
     * @return
     */
    public java.util.ArrayList<String> getDefaultIncludedTargetsList () {
        return defaultIncludedTargetsList;
    }

    /**
     *
     * @param defaultIncludedTargetsList
     */
    public void setDefaultIncludedTargetsList ( java.util.ArrayList<String> defaultIncludedTargetsList ) {
        this.defaultIncludedTargetsList = defaultIncludedTargetsList;
    }

    /**
     *
     * @return
     */
    public java.io.File getExcludedTargetsFile () {
        return excludedTargetsFile;
    }

    /**
     *
     * @param excludedTargetsFile
     */
    public void setExcludedTargetsFile ( java.io.File excludedTargetsFile ) {
        this.excludedTargetsFile = excludedTargetsFile;
    }

    /**
     *
     * @return
     */
    public java.util.ArrayList<String> getExcludedTargetsList () {
        return excludedTargetsList;
    }

    /**
     *
     * @param excludedTargetsList
     */
    public void setExcludedTargetsList ( java.util.ArrayList<String> excludedTargetsList ) {
        this.excludedTargetsList = excludedTargetsList;
    }

    /**
     *
     * @return
     */
    public java.io.File getIncludedTargetsFile () {
        return includedTargetsFile;
    }

    /**
     *
     * @param includedTargetsFile
     */
    public void setIncludedTargetsFile ( java.io.File includedTargetsFile ) {
        this.includedTargetsFile = includedTargetsFile;
    }

    /**
     *
     * @return
     */
    public java.util.ArrayList<String> getIncludedTargetsList () {
        return includedTargetsList;
    }

    /**
     *
     * @param includedTargetsList
     */
    public void setIncludedTargetsList ( java.util.ArrayList<String> includedTargetsList ) {
        this.includedTargetsList = includedTargetsList;
    }

    /**
     *
     * @return
     */
    public int getRandomHostsCount () {
        return randomHostsCount;
    }

    /**
     *
     * @param randomHostsCount
     */
    public void setRandomHostsCount ( int randomHostsCount ) {
        this.randomHostsCount = randomHostsCount;
    }

    /**
     *
     * @return
     */
    public boolean isIsExcludedTargetsConfigured () {
        return isExcludedTargetsConfigured;
    }

    /**
     *
     * @param isExcludedTargetsConfigured
     */
    public void setIsExcludedTargetsConfigured ( boolean isExcludedTargetsConfigured ) {
        this.isExcludedTargetsConfigured = isExcludedTargetsConfigured;
    }

    /**
     *
     * @return
     */
    public boolean isIsExcludedTargetsLoadedFromFile () {
        return isExcludedTargetsLoadedFromFile;
    }

    /**
     *
     * @param isExcludedTargetsLoadedFromFile
     */
    public void setIsExcludedTargetsLoadedFromFile ( boolean isExcludedTargetsLoadedFromFile ) {
        this.isExcludedTargetsLoadedFromFile = isExcludedTargetsLoadedFromFile;
    }

    /**
     *
     * @return
     */
    public boolean isIsIncludedTargetsLoadedFromFile () {
        return isIncludedTargetsLoadedFromFile;
    }

    /**
     *
     * @param isIncludedTargetsLoadedFromFile
     */
    public void setIsIncludedTargetsLoadedFromFile ( boolean isIncludedTargetsLoadedFromFile ) {
        this.isIncludedTargetsLoadedFromFile = isIncludedTargetsLoadedFromFile;
    }

    /**
     *
     * @return
     */
    public boolean isIsIncudedTargetsConfigured () {
        return isIncudedTargetsConfigured;
    }

    /**
     *
     * @param isIncudedTargetsConfigured
     */
    public void setIsIncudedTargetsConfigured ( boolean isIncudedTargetsConfigured ) {
        this.isIncudedTargetsConfigured = isIncudedTargetsConfigured;
    }

    /**
     *
     * @return
     */
    public boolean isIsRandomHostsEnabled () {
        return isRandomHostsEnabled;
    }

    /**
     *
     * @param isRandomHostsEnabled
     */
    public void setIsRandomHostsEnabled ( boolean isRandomHostsEnabled ) {
        this.isRandomHostsEnabled = isRandomHostsEnabled;
    }

    /**
     * Validates all host specification strings in an arrayList. see doc for validateHostString() for more details.
     * <p>
     * @param hostsStrings
     *                     <p>
     * @return
     */
    public boolean validateAllHostStrings ( java.util.ArrayList<String> hostsStrings ) {
        for ( String hostString : hostsStrings ) {
            if (  ! validateHostString ( hostString ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a single host specification string (whether for included or excluded hosts). An acceptable string might be one of the following:
     * <p>
     * - An ip in the form x.y.z.w
     * - An ip range: a-b.c-d.e-f.g-h . Note that not all dashes are needed
     * - A hostname specification (url compliant) like host1.organization1.com
     * <p>
     * You may or may not append a mask specification to any of the above forms (/m as in /24 or /30)
     * <p>
     * @param hostString The string to be verified
     * <p>
     * @return
     */
    public boolean validateHostString ( String hostString ) {
        try {
            if ( hostString.matches ( "(d{1,3}\\.d{1,3}\\.d{1,3}\\.d{1,3})|(d{1,3}\\.d{1,3}\\.d{1,3}\\.d{1,3}/d{1,2})" ) ) {
                
                return true;
            }
        } catch ( NumberFormatException | NullPointerException e ) {
            return false;
        }
        return false;
    }

}
