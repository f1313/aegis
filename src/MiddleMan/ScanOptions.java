package MiddleMan;

public class ScanOptions {

    private boolean isSimpleModeSelected;

    private boolean isNoPortScanSelected;
    private boolean isNoPingScanSelected;

    private boolean isTCPScanSelected;
    private PortSpec TCPPorts;
    private boolean[] flags;//in order: URGACKPSHRSTSYNFIN

    private boolean isUDPScanSelected;
    private PortSpec UDPPorts;

    //Payload Section
    private boolean isPayloadSelected;
    private long customPayloadType; //-2: Hex, -1:String, 0- Long.MAX_LONG: random bytes with specified length
    private String payloadString;

    private int SCTPScanType;//1: init, 2: cookie, 3:echo

    private boolean isIPProtocolScanSelected;

    //Protocols section


    private boolean isFTPBounceScanSelected;
    private PortSpec FTPBouncePorts;

    //Idle Scan section
    private boolean isZombieScanSelected;
    private String zombieHost;
    private boolean isCustomPortSelected;
    private String zombiePort;

    /**
     * @param fileName
     * @return
     */
    public boolean readPayloadFromFile ( String fileName ) {
        try {
            new java.io.PrintWriter ( new java.io.FileOutputStream ( fileName, true ), true ).println ( "ENDENDEND" );
            DynamicInput in = new DynamicInput ( );
            in.init ( new java.io.FileInputStream ( fileName ) );
            StringBuilder sb = new StringBuilder ( 3000 );
            String line;
            while ( ! ( line = in.reader.readLine ( ) ).equals ( "ENDENDEND" ) ) {
                sb.append ( line );
            }
        } catch ( Exception ex ) {
            return false;
        }
        return true;
    }

    /**
     * @param fileName
     * @return
     */
    public boolean writePayloadToFile ( String fileName ) {
        try {
            new java.io.PrintWriter ( new java.io.FileOutputStream ( fileName, true ), true ).println ( payloadString );
        } catch ( Exception ex ) {
            return false;
        }
        return true;
    }

    /**
     * @param port
     * @return
     */
    public boolean checkZombiePort ( String port ) {
        try {
            int p = Integer.parseInt ( port );
            return p >= 0 && p <= 65535;
        } catch ( Exception ex ) {
            return false;
        }
    }

    /**
     * @param hostString
     * @return
     */
    public boolean checkZombieHostString ( String hostString ) {
        try {
            java.util.regex.Matcher hostMatcher = Constants.getHostNamePattern ( ).matcher ( hostString ),
                    ipMatcher = Constants.getHostIPPattern ( ).matcher ( hostString );
            if ( hostString.contains ( "/" ) ) {
                return false;
            }
            if ( ipMatcher.matches ( ) ) {
                String[] components = hostString.split ( "\\." );
                for ( int i = 0 ; i < 4 ; i++ ) {
                    if ( ! octetChecker ( components[ i ] ) ) {
                        return false;
                    }
                }
                return components.length == 4;
            } else {
                return hostMatcher.matches ( );
            }
        } catch ( Exception ex ) {
            return false;
        }
    }

    /**
     * @param octet
     * @return
     */
    private boolean octetChecker ( String octet ) {
        int num = Integer.parseInt ( octet );
        return num >= 0 && num <= 255;
    }

}
