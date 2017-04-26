package Util;

import Specs.Constants;
import javafx.scene.layout.BorderPane;
import windows.AegisMainWindowController;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Percentage  {
    private Process p;
    private String output;
    private String percentage;
    private Pattern percentagePattern = Constants.getPercentageLinePattern ( );
    private Pattern donePattern = Constants.getEndLinePattern ( );
    private Pattern firstHalfPattern = Constants.getFirstHalfReplacer ( );
    private Pattern secondHalfPatter = Constants.getSecondHalfReplacer ( );
    BorderPane mainBorderPane;

    public Percentage ( Process p ) {
        this.p = p;
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
        try {
            String line;
            while ( ! Thread.currentThread ( ).isInterrupted ( ) && sc.hasNextLine ( ) ) {
                //SYN Stealth Scan Timing: About 44.57% done; ETC: 18:14 (0:00:40 remaining)
                line = sc.nextLine ( );
                if ( percentagePattern.matcher ( line ).matches ( ) ) {
                    line = firstHalfPattern.matcher ( line ).replaceAll ( "" );
                    line = secondHalfPatter.matcher ( line ).replaceAll ( "" );
                    percentage = line;
                    System.out.println (line );
                    AegisMainWindowController.progressBar.setProgress ( (Double.parseDouble ( line )/100.0) );
                } else if ( donePattern.matcher ( line ).matches ( ) ) {
                    break;
                }
            }
        } catch ( Exception ex ) {
            ex.printStackTrace ( );
            System.out.println ("Percentage got Interrupted" );
        }
    }

    public BorderPane getMainBorderPane ( ) {
        return mainBorderPane;
    }

    public void setMainBorderPane ( BorderPane mainBorderPane ) {
        this.mainBorderPane = mainBorderPane;
    }
}
