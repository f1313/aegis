package MiddleMan;

public class ScanOptions {
    
    private boolean isSimpleModeSelected;
    private byte intensity;
    
    private boolean isNoPortScanSelected;
    private boolean isNoPingScanSelected;
    
    private boolean isTCPScanSelected;
    private PortSpec TCPPorts;
    private boolean[] flags;//in order: URGACKPSHRSTSYNFIN
    
    private boolean isUDPScanSelected;
    private PortSpec UDPPorts;

    private boolean isPayloadSelected;
    private boolean isSimplePayloadSelected;
    private int payloadNumber;
    private long customPayloadType; //-2: Hex, -1:String, 0- Long.MAX_LONG: random bytes with specified length
    
}
