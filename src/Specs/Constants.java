package Specs;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Constants {
    //We never realized that all these modifier combinations work!!!
    final private static java.util.regex.Pattern portPattern = java.util.regex.Pattern
            .compile ( "\\d{1,5}(-\\d{1,5})?" );

    final static private java.util.regex.Pattern hostIPPattern = java.util.regex.Pattern
            .compile ( "(\\d{1,3}(-\\d{1,3})?\\.){3}\\d{1,3}(-\\d{1,3})?(/\\d{1,2})?" );

    private static final java.util.regex.Pattern hostNamePattern = java.util.regex.Pattern
            .compile ( "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*" +
                    "([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])(/\\d{1,2})?$" );

    static final private java.util.regex.Pattern endLinePattern = java.util.regex.Pattern
            .compile ( "Nmap done.*$" );
    //This matches something like: SYN Stealth Scan Timing: About 44.57% done; ETC: 18:14 (0:00:40 remaining)

    static final private java.util.regex.Pattern percentageLinePattern = java.util.regex.Pattern
            .compile ( ".*About .*% done; ETC: .*" );

    static final private java.util.HashMap < String, PortHelper > portsByNumberMap =
            new java.util.HashMap <> ( 20000 );

    static final private java.util.HashMap < String, PortHelper > portsByNameMap =
            new java.util.HashMap <> ( 20000 );

    private static final java.util.regex.Pattern firstHalfReplacer = java.util.regex.Pattern
            .compile ( ".*About " );

    private static final java.util.regex.Pattern secondHalfReplacer = java.util.regex.Pattern
            .compile ( "% done; ETC: .*" );

    /**
     *
     * @return
     */
    public static Pattern getFirstHalfReplacer ( ) {
        return firstHalfReplacer;
    }

    /**
     *
     * @return
     */
    public static Pattern getSecondHalfReplacer ( ) {
        return secondHalfReplacer;
    }

    /**
     * @return
     */
    public static Pattern getEndLinePattern ( ) {
        return endLinePattern;
    }

    /**
     *
     */
    private Constants ( ) {
    }

    /**
     * @return
     */
    static public Pattern getPercentageLinePattern ( ) {
        return percentageLinePattern;
    }

    /**
     * @return
     */
    public static HashMap < String, PortHelper > getPortsByNumberMap ( ) {
        return portsByNumberMap;
    }

    /**
     * @return
     */
    public static HashMap < String, PortHelper > getPortsByNameMap ( ) {
        return portsByNameMap;
    }

    /**
     * @return
     */
    public static java.util.regex.Pattern getHostIPPattern ( ) {
        return hostIPPattern;
    }

    /**
     * @return
     */
    public static java.util.regex.Pattern getHostNamePattern ( ) {
        return hostNamePattern;
    }

    /**
     * @return
     */
    public static java.util.regex.Pattern getPortPattern ( ) {
        return portPattern;
    }

    /**
     * Reads all port specs and stuff from files and puts them in sets
     *
     * @return
     */
    public static boolean readPortLists ( ) {
        try {
            DynamicInput portsNumsReader = new DynamicInput ( );
            DynamicInput portsNamesReader = new DynamicInput ( );
            DynamicInput portsTypesReader = new DynamicInput ( );
            DynamicInput portsDescriptionsReader = new DynamicInput ( );

            portsNumsReader.init ( new java.io.FileInputStream ( "ports-nums.lst" ) );
            portsNamesReader.init ( new java.io.FileInputStream ( "ports-names.lst" ) );
            portsTypesReader.init ( new java.io.FileInputStream ( "ports-types.lst" ) );
            portsDescriptionsReader.init ( new java.io.FileInputStream ( "ports-descriptions.lst" ) );

            String num, name, type, desc;
            while ( ! "ENDENDEND".equals ( num = portsNumsReader.reader.readLine ( ) )
                    && ! "ENDENDEND".equals ( name = portsNamesReader.reader.readLine ( ) )
                    && ! "ENDENDEND".equals ( type = portsTypesReader.reader.readLine ( ) )
                    && ! "ENDENDEND".equals ( desc = portsDescriptionsReader.reader.readLine ( ) ) ) {
                portsByNumberMap.put ( num, new PortHelper ( num, name, type, desc ) );
                portsByNameMap.put ( name, new PortHelper ( num, name, type, desc ) );
            }
            portsByNameMap.put ( "notThere", new PortHelper ( "0", "Unknown", "Unknown", "Unknown" ) );
            portsByNumberMap.put ( "notThere", new PortHelper ( "0", "Unknown", "Unknown", "Unknown" ) );
        } catch ( Exception ex ) {
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    public static HashMap < String, PortHelper > getPortsByNumberSet ( ) {
        return portsByNumberMap;
    }

    /**
     * @return
     */
    public static HashMap < String, PortHelper > getPortsByNameSet ( ) {
        return portsByNameMap;
    }

}
