package MiddleMan;

import java.util.ArrayList;

public class DNSSpecs {

    private boolean isDNSOptionsEnabled;
    private boolean isTracerouteEnabled;
    private boolean isAliveOnlyChosen; //Opposite is for all
    private boolean isSystemDNSUsed;//Otherwise it is custom dns server
    private java.util.ArrayList<String> DNSServersList;

    /**
     * @return
     */
    public boolean isDNSOptionsEnabled() {
        return isDNSOptionsEnabled;
    }

    /**
     * @param DNSOptionsEnabled
     */
    public void setDNSOptionsEnabled(boolean DNSOptionsEnabled) {
        isDNSOptionsEnabled = DNSOptionsEnabled;
    }

    /**
     * @return
     */
    public boolean isTracerouteEnabled() {
        return isTracerouteEnabled;
    }

    /**
     * @param tracerouteEnabled
     */
    public void setTracerouteEnabled(boolean tracerouteEnabled) {
        isTracerouteEnabled = tracerouteEnabled;
    }

    /**
     * @return
     */
    public boolean isAliveOnlyChosen() {
        return isAliveOnlyChosen;
    }

    /**
     * @param aliveOnlyChosen
     */
    public void setAliveOnlyChosen(boolean aliveOnlyChosen) {
        isAliveOnlyChosen = aliveOnlyChosen;
    }

    /**
     * @return
     */
    public boolean isSystemDNSUsed() {
        return isSystemDNSUsed;
    }

    /**
     * @param systemDNSUsed
     */
    public void setSystemDNSUsed(boolean systemDNSUsed) {
        isSystemDNSUsed = systemDNSUsed;
    }

    /**
     * @return
     */
    public ArrayList<String> getDNSServersList() {
        return DNSServersList;
    }

    /**
     * @param DNSServersList
     */
    public void setDNSServersList(ArrayList<String> DNSServersList) {
        this.DNSServersList = DNSServersList;
    }

    /**
     * @param hostString
     * @return
     */
    public boolean validateDNSString(String hostString) {
        try {
            java.util.regex.Matcher hostMatcher = Constants.getHostNamePattern().matcher(hostString),
                    ipMatcher = Constants.getHostIPPattern().matcher(hostString);
            if (ipMatcher.matches()) {
                String[] components = hostString.split("[./]");
                for (int i = 0; i < 4; i++) {
                    if (!octetChecker(components[i])) {
                        return false;
                    }
                }
                if (components.length == 5) {
                    return false;
                } else {
                    return true;
                }
            } else if (hostMatcher.matches()) {
                if (hostString.contains("/")) {
                    return false;
                } else {//String without mask specification
                    return true;//Assuming true for now, might do a url checker later on.
                }
            } else {//String does not match ip or hostname patterns
                return false;
            }
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    /**
     * @param octet
     * @return
     */
    private boolean octetChecker(String octet) {
        if (octet.contains("-")) {
            return octetRangeChecker(octet);
        } else {
            return singleOctetChecker(octet);
        }
    }

    /**
     * @param octetRange
     * @return
     */
    private boolean octetRangeChecker(String octetRange) {
        String[] components = octetRange.split("-");
        int num1 = Integer.parseInt(components[0]),
                num2 = Integer.parseInt(components[1]);
//        return num1 < num2                 /*see the failure*/
//                && num1 >= 0 && num1 <= 255
//                && num2 >= 0 && num2 <= 255;
        return 0 <= num1 && num1 < num2 && num2 <= 255;
    }

    /**
     * @param octet
     * @return
     */
    private boolean singleOctetChecker(String octet) {
        int num = Integer.parseInt(octet);
        return num >= 0 && num <= 255;
    }

}
