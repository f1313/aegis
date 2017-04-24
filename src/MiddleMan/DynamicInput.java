package MiddleMan;

// <editor-fold defaultstate="collapsed" desc="Dynamic Input class">
public class DynamicInput {

    java.io.BufferedReader reader;
    java.util.StringTokenizer tokenizer;

    void init ( java.io.InputStream input ) {
        reader = new java.io.BufferedReader ( new java.io.InputStreamReader ( input ) );
        tokenizer = new java.util.StringTokenizer ( "" );
    }

    String next () throws Exception {
        while (  ! tokenizer.hasMoreTokens () ) {
            tokenizer = new java.util.StringTokenizer ( reader.readLine () );
        }
        return tokenizer.nextToken ();
    }

    int nextInt () throws Exception {
        return Integer.parseInt ( next () );
    }

    long nextLong () throws Exception {
        return Long.parseLong ( next () );
    }

}
// </editor-fold>
