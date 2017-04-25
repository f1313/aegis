package Specs;

public class PortSpec {

    //included ports
    private boolean isIncludedPortsConfigured;
    private java.util.ArrayList < String > includedPortsList;
    private java.util.ArrayList < String > defaultIncludedPortsList;
    private boolean isIncludedPortsLoadedFromFile, isIncludedPortsWrittenToFile;
    private java.io.File includedPortsFile;

    //excluded ports
    private boolean isExcludedPortsConfigured;
    private java.util.ArrayList < String > excludedPortsList;
    private java.util.ArrayList < String > defaultExcludedPortsList;
    private boolean isExcludedPortsLoadedFromFile, isExcludedPortsWrittenToFile;
    private java.io.File excludedPortsFile;

    /**
     * A no-arg constructor that sets default values for a port specification object.
     */
    public PortSpec ( ) {
        isIncludedPortsConfigured = false;
        includedPortsList = new java.util.ArrayList <> ( 10 );
        defaultIncludedPortsList = new java.util.ArrayList <> ( 10 );
        isIncludedPortsLoadedFromFile = false;

        isExcludedPortsConfigured = false;
        excludedPortsList = new java.util.ArrayList <> ( 10 );
        defaultExcludedPortsList = new java.util.ArrayList <> ( 10 );
        isExcludedPortsLoadedFromFile = false;
    }

    /**
     * @return
     */
    public boolean isIncludedPortsConfigured ( ) {
        return isIncludedPortsConfigured;
    }

    /**
     * @param includedPortsConfigured
     */
    public void setIncludedPortsConfigured ( boolean includedPortsConfigured ) {
        isIncludedPortsConfigured = includedPortsConfigured;
    }

    /**
     * @return
     */
    public boolean isIncludedPortsLoadedFromFile ( ) {
        return isIncludedPortsLoadedFromFile;
    }

    /**
     * @param includedPortsLoadedFromFile
     */
    public void setIncludedPortsLoadedFromFile ( boolean includedPortsLoadedFromFile ) {
        isIncludedPortsLoadedFromFile = includedPortsLoadedFromFile;
    }

    /**
     * @return
     */
    public boolean isIncludedPortsWrittenToFile ( ) {
        return isIncludedPortsWrittenToFile;
    }

    /**
     * @param includedPortsWrittenToFile
     */
    public void setIncludedPortsWrittenToFile ( boolean includedPortsWrittenToFile ) {
        isIncludedPortsWrittenToFile = includedPortsWrittenToFile;
    }

    /**
     * @return
     */
    public boolean isExcludedPortsConfigured ( ) {
        return isExcludedPortsConfigured;
    }

    /**
     * @param excludedPortsConfigured
     */
    public void setExcludedPortsConfigured ( boolean excludedPortsConfigured ) {
        isExcludedPortsConfigured = excludedPortsConfigured;
    }

    /**
     * @return
     */
    public boolean isExcludedPortsLoadedFromFile ( ) {
        return isExcludedPortsLoadedFromFile;
    }

    /**
     * @param excludedPortsLoadedFromFile
     */
    public void setExcludedPortsLoadedFromFile ( boolean excludedPortsLoadedFromFile ) {
        isExcludedPortsLoadedFromFile = excludedPortsLoadedFromFile;
    }

    /**
     * @return
     */
    public boolean isExcludedPortsWrittenToFile ( ) {
        return isExcludedPortsWrittenToFile;
    }

    /**
     * @param excludedPortsWrittenToFile
     */
    public void setExcludedPortsWrittenToFile ( boolean excludedPortsWrittenToFile ) {
        isExcludedPortsWrittenToFile = excludedPortsWrittenToFile;
    }

    /**
     * Returns the arrayList of excluded ports.
     * <p>
     *
     * @return
     */
    public java.util.ArrayList < String > getDefaultExcludedPortsList ( ) {
        return defaultExcludedPortsList;
    }

    /**
     * @param defaultExcludedPortsList
     */
    public void setDefaultExcludedPortsList ( java.util.ArrayList < String > defaultExcludedPortsList ) {
        this.defaultExcludedPortsList = defaultExcludedPortsList;
    }

    /**
     * @return
     */
    public java.util.ArrayList < String > getDefaultIncludedPortsList ( ) {
        return defaultIncludedPortsList;
    }

    /**
     * @param defaultIncludedPortsList
     */
    public void setDefaultIncludedPortsList ( java.util.ArrayList < String > defaultIncludedPortsList ) {
        this.defaultIncludedPortsList = defaultIncludedPortsList;
    }

    /**
     * @return
     */
    public java.io.File getExcludedPortsFile ( ) {
        return excludedPortsFile;
    }

    /**
     * @param excludedPortsFile
     */
    public void setExcludedPortsFile ( java.io.File excludedPortsFile ) {
        this.excludedPortsFile = excludedPortsFile;
    }

    /**
     * @return
     */
    public java.util.ArrayList < String > getExcludedPortsList ( ) {
        return excludedPortsList;
    }

    /**
     * @param excludedPortsList
     */
    public void setExcludedPortsList ( java.util.ArrayList < String > excludedPortsList ) {
        this.excludedPortsList = excludedPortsList;
    }

    /**
     * @return
     */
    public java.io.File getIncludedPortsFile ( ) {
        return includedPortsFile;
    }

    /**
     * @param includedPortsFile
     */
    public void setIncludedPortsFile ( java.io.File includedPortsFile ) {
        this.includedPortsFile = includedPortsFile;
    }

    /**
     * @return
     */
    public java.util.ArrayList < String > getIncludedPortsList ( ) {
        return includedPortsList;
    }

    /**
     * @param includedPortsList
     */
    public void setIncludedPortsList ( java.util.ArrayList < String > includedPortsList ) {
        this.includedPortsList = includedPortsList;
    }

    /**
     * @return
     */
    public boolean isIsExcludedPortsConfigured ( ) {
        return isExcludedPortsConfigured;
    }

    /**
     * @param isExcludedPortsConfigured
     */
    public void setIsExcludedPortsConfigured ( boolean isExcludedPortsConfigured ) {
        this.isExcludedPortsConfigured = isExcludedPortsConfigured;
    }

    /**
     * @return
     */
    public boolean isIsExcludedPortsLoadedFromFile ( ) {
        return isExcludedPortsLoadedFromFile;
    }

    /**
     * @param isExcludedPortsLoadedFromFile
     */
    public void setIsExcludedPortsLoadedFromFile ( boolean isExcludedPortsLoadedFromFile ) {
        this.isExcludedPortsLoadedFromFile = isExcludedPortsLoadedFromFile;
    }

    /**
     * @return
     */
    public boolean isIsIncludedPortsConfigured ( ) {
        return isIncludedPortsConfigured;
    }

    /**
     * @param isIncludedPortsConfigured
     */
    public void setIsIncludedPortsConfigured ( boolean isIncludedPortsConfigured ) {
        this.isIncludedPortsConfigured = isIncludedPortsConfigured;
    }

    /**
     * @return
     */
    public boolean isIsIncludedPortsLoadedFromFile ( ) {
        return isIncludedPortsLoadedFromFile;
    }

    /**
     * @param isIncludedPortsLoadedFromFile
     */
    public void setIsIncludedPortsLoadedFromFile ( boolean isIncludedPortsLoadedFromFile ) {
        this.isIncludedPortsLoadedFromFile = isIncludedPortsLoadedFromFile;
    }

    /**
     * This method validates a single port string. The string can be either a single port number ( 0 - 65535) or
     * a range in the form ( x - y ) where x and y conform to the condtion above, there can be no spaces or
     * multiple spaces between components of the expression.
     * <p>
     *
     * @param port <p>
     * @return
     */
    public boolean validatePortString ( String port ) {
        try {
            java.util.regex.Matcher portMatcher = Constants.getPortPattern ( ).matcher ( port );
            if ( portMatcher.matches ( ) ) {
                if ( port.contains ( "-" ) ) {//Then it is a port range
                    String[] portStrings = port.split ( "-" );
                    int port1 = Integer.parseInt ( portStrings[ 0 ] ), port2 = Integer.parseInt ( portStrings[ 1 ] );
                    if ( port1 >= 0 && port1 <= 65535 && port2 >= 0 && port2 <= 65535 ) {
                        return true;
                    }
                } else {
                    int portNum = Integer.parseInt ( port );
                    if ( portNum >= 0 && portNum <= 65535 ) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch ( NumberFormatException | NullPointerException e ) {
            return false;
        }
    }

    /**
     * Validates all port specification strings in an arrayList. see doc for validatePortString() for more details.
     * <p>
     *
     * @param portsStrings <p>
     * @return
     */
    public boolean validateAllPortStrings ( java.util.ArrayList < String > portsStrings ) {
        for ( String portString : portsStrings ) {
            if ( ! validatePortString ( portString ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param fileName
     * @return
     */
    public boolean writeIncludedPortsToFile ( String fileName ) {
        try {
            includedPortsFile = new java.io.File ( fileName );
            java.io.PrintWriter pr = new java.io.PrintWriter ( includedPortsFile );
            for ( String s : includedPortsList ) {
                pr.println ( s );
            }
        } catch ( Exception ex ) {
            return false;
        }
        isIncludedPortsWrittenToFile = true;
        return true;
    }

    /**
     * @param fileName
     * @return
     */
    public boolean writeExcludedPortsToFile ( String fileName ) {
        try {
            excludedPortsFile = new java.io.File ( fileName );
            java.io.PrintWriter pr = new java.io.PrintWriter ( excludedPortsFile );
            for ( String s : excludedPortsList ) {
                pr.println ( s );
            }
        } catch ( Exception ex ) {
            return false;
        }
        isExcludedPortsWrittenToFile = true;
        return true;
    }

    /**
     * @param fileName
     * @return
     */
    public boolean readIncludedPortsFromFile ( String fileName ) {
        try {
            new java.io.PrintWriter ( new java.io.FileOutputStream ( fileName, true ), true ).println ( "ENDENDEND" );
            DynamicInput input = new DynamicInput ( );
            input.init ( new java.io.FileInputStream ( fileName ) );
            String portNumberString;
            while ( ! ( portNumberString = input.reader.readLine ( ) ).equals ( "ENDENDEND" ) ) {
                if ( ! validatePortString ( portNumberString ) ) {
                    return false;
                }
                includedPortsList.add ( portNumberString );
            }
        } catch ( Exception ex ) {
            return false;
        }
        isIncludedPortsLoadedFromFile = true;
        return true;
    }

    /**
     * @param fileName
     * @return
     */
    public boolean readExcludedPortsFromFile ( String fileName ) {
        try {
            new java.io.PrintWriter ( new java.io.FileOutputStream ( fileName, true ), true ).println ( "ENDENDEND" );
            DynamicInput input = new DynamicInput ( );
            input.init ( new java.io.FileInputStream ( fileName ) );
            String portNumberString;
            while ( ! ( portNumberString = input.reader.readLine ( ) ).equals ( "ENDENDEND" ) ) {
                if ( ! validatePortString ( portNumberString ) ) {
                    return false;
                }
                excludedPortsList.add ( portNumberString );
            }
        } catch ( Exception ex ) {
            return false;
        }
        isExcludedPortsLoadedFromFile = true;
        return true;
    }

    /**
     * @param number
     * @return
     */
    public PortHelper getPortHelperByNumber ( String number ) {
        return Constants.getPortsByNumberSet ( ).get ( number );
    }

    /**
     * @param name
     * @return
     */
    public PortHelper getPortHelperByName ( String name ) {
        PortHelper toReturn = Constants.getPortsByNameSet().get(name);
        return Constants.getPortsByNameSet ( ).get ( name );
    }
}
