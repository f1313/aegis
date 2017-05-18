package Util;

/**
 * Created by wintson on 5/18/17.
 */
public class input {

    public static java.io.BufferedReader reader;
    public static java.util.StringTokenizer tokenizer;

    public static void init ( java.io.InputStream input ) {
        reader = new java.io.BufferedReader ( new java.io.InputStreamReader ( input ) );
        tokenizer = new java.util.StringTokenizer ( "" );
    }

    public static String next () throws Exception {
        while (  ! tokenizer.hasMoreTokens () ) {
            tokenizer = new java.util.StringTokenizer ( reader.readLine () );
        }
        return tokenizer.nextToken ();
    }

    public static int nextInt () throws Exception {
        return Integer.parseInt ( next () );
    }

    public static long nextLong () throws Exception {
        return Long.parseLong ( next () );
    }

}