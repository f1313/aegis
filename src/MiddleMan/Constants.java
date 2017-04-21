package MiddleMan;

public class Constants {

    static final private java.util.regex.Pattern portPattern = java.util.regex.Pattern
            .compile ( "\\d{1,5}(-\\d{1,5})?" );

    static final private java.util.regex.Pattern hostIPPattern = java.util.regex.Pattern
            .compile ( "(\\d{1,3}(-\\d{1,3})?\\.){3}\\d{1,3}(-\\d{1,3})?(/\\d{1,2})?" );

    static final private java.util.regex.Pattern hostNamePattern = java.util.regex.Pattern
            .compile ( "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*" +
                    "([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])(/\\d{1,2})?$" );

    //java.net.URL url = new java.net.URL
    /**
     *
     * @return
     */
    public static java.util.regex.Pattern getHostIPPattern () {
        return hostIPPattern;
    }

    /**
     *
     * @return
     */
    public static java.util.regex.Pattern getHostNamePattern () {
        return hostNamePattern;
    }

    /**
     *
     * @return
     */
    public static java.util.regex.Pattern getPortPattern () {
        return portPattern;
    }

    private Constants () {
    }

}
