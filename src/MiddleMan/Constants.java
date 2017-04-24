package MiddleMan;

import java.util.HashMap;

public class Constants {

    static final private java.util.regex.Pattern portPattern = java.util.regex.Pattern
            .compile ( "\\d{1,5}(-\\d{1,5})?" );

    static final private java.util.regex.Pattern hostIPPattern = java.util.regex.Pattern
            .compile ( "(\\d{1,3}(-\\d{1,3})?\\.){3}\\d{1,3}(-\\d{1,3})?(/\\d{1,2})?" );

    static final private java.util.regex.Pattern hostNamePattern = java.util.regex.Pattern
            .compile ( "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*" +
                    "([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])(/\\d{1,2})?$" );

    static final private java.util.HashMap < String, PortHelper > portsByNumberMap =
            new java.util.HashMap <> ( 20000 );

    static final private java.util.HashMap < String, PortHelper > portsByNameMap =
            new java.util.HashMap <> ( 20000 );

    /**
     *
     */
    private Constants ( ) {
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
            portsByNameMap.put ("notThere", new PortHelper ("0", "Unknown", "Unknown", "Unknown"));
            portsByNumberMap.put("notThere", new PortHelper("0", "Unknown", "Unknown", "Unknown"));
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
