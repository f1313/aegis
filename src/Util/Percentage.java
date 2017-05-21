package Util;

import Specs.Constants;
import javafx.scene.layout.BorderPane;
import windows.AegisMainWindowController;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Percentage {
    private Process p;
    private String output;
    private String percentage;
    private Pattern percentagePattern = Constants.getPercentageLinePattern ( );
    private Pattern donePattern = Constants.getEndLinePattern ( );
    private Pattern firstHalfPattern = Constants.getFirstHalfReplacer ( );
    private Pattern secondHalfPatter = Constants.getSecondHalfReplacer ( );
    String log = "";
    Group g;

    public Percentage ( Process p, Group g ) {
        this.p = p;
        this.g = g;
    }

    /**
     * @return
     */
    public String getPercentage ( ) {
        return percentage;
    }

    /**
     * @param percentage
     */
    public void setPercentage ( String percentage ) {
        this.percentage = percentage;
    }

    /**
     * @return
     */
    public Process getP ( ) {
        return p;
    }

    /**
     * @param p
     */
    public void setP ( Process p ) {
        this.p = p;
    }

    /**
     * @return
     */
    public String getOutput ( ) {
        return output;
    }

    /**
     * @param output
     */
    public void setOutput ( String output ) {
        this.output = output;
    }

    public void work ( ) {
        Scanner sc = new Scanner ( p.getInputStream ( ) );
        Scanner err = new Scanner ( p.getErrorStream ( ) );
        String res = "";
        String error = "";
        String log = "";
        String sep = "--------------------------------------------";
        try {
            String line;
            while ( ! Thread.currentThread ( ).isInterrupted ( ) && sc.hasNextLine ( ) ) {
                //SYN Stealth Scan Timing: About 44.57% done; ETC: 18:14 (0:00:40 remaining)
                line = sc.nextLine ( );
                if ( ! ( line.contains ( "undergoing" ) || line.contains ( "elapsed" ) ) ) {

                    res += line + "\n";
                }
                if ( percentagePattern.matcher ( line ).matches ( ) ) {
                    line = firstHalfPattern.matcher ( line ).replaceAll ( "" );
                    line = secondHalfPatter.matcher ( line ).replaceAll ( "" );
                    percentage = line;
                    System.out.println ( line );
                    AegisMainWindowController.progressBar.setProgress ( ( Double.parseDouble ( line ) / 100.0 ) );
                } else if ( donePattern.matcher ( line ).matches ( ) ) {
                    break;
                }
            }

            while ( ! Thread.currentThread ( ).isInterrupted ( ) && err.hasNextLine ( ) ) {
                error += err.nextLine ( );
            }
            log.replaceAll ( "QUITTING!","" );
            log = "Output :\n" + res + "\n" + sep + "\nErrors:\n" + error + "\n";


        } catch ( Exception ex ) {
            ex.printStackTrace ( );
            System.out.println ( "Percentage got Interrupted" );
        }


//        System.out.println ( "Log is " + log );
        g.setLog ( log );
    }

    public String getLog ( ) {
        return log;
    }

    public void setLog ( String log ) {
        this.log = log;
    }
}
