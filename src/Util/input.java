package Util;

/**
 * Created by wintson on 5/18/17.
 */
class input {

    static java.io.BufferedReader reader;
    static java.util.StringTokenizer tokenizer;

    static void init ( java.io.InputStream input ) {
        reader = new java.io.BufferedReader ( new java.io.InputStreamReader ( input ) );
        tokenizer = new java.util.StringTokenizer ( "" );
    }

    static String next () throws Exception {
        while (  ! tokenizer.hasMoreTokens () ) {
            tokenizer = new java.util.StringTokenizer ( reader.readLine () );
        }
        return tokenizer.nextToken ();
    }

    static int nextInt () throws Exception {
        return Integer.parseInt ( next () );
    }

    static long nextLong () throws Exception {
        return Long.parseLong ( next () );
    }

}