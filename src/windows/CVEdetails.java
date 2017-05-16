package windows;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import Util.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wintson on 5/15/17.
 */
public class CVEdetails {
    public BorderPane mainBorderPane = new BorderPane ( );
    Stage stage = new Stage ( );
    ListView leftList = new ListView <> ( );
    Group g = null;

    Browser browser = new Browser ( );
    BrowserView view = new BrowserView ( browser );

    public void init ( Group g ) {
        System.out.println ("Init" );
        this.g = g;
        stage.setScene ( new Scene ( mainBorderPane ) );
        Label l = new Label ( "CVE Details Scan" );
        VBox vb = new VBox ( );
        vb.getChildren ( ).add ( l );
        mainBorderPane.setTop ( vb );
        mainBorderPane.setLeft ( leftList );
        l.setPadding ( new Insets ( 10, 10, 10, 10 ) );
        mainBorderPane.setMinSize ( 500, 500 );
        mainBorderPane.setCenter ( view );
        l.setAlignment ( Pos.CENTER );
        System.out.println ("Calling Parser" );
        setUpParser ( );


        leftList.setOnMouseClicked ( event -> {
            String selected = ( String ) ( leftList.getSelectionModel ( ).getSelectedItem ( ) );
            int index = leftList.getSelectionModel ( ).getSelectedIndex ( );
            browser.loadURL ( g.getOutputLocationFilename ( ) + "Files/" + ( index + 1 ) + ".html" );
        } );


    }


    public void setUpParser ( ) {
        System.out.println ( "Setting Up" );
        ArrayList < String > list = parseHTML ( g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ) + ".html" );
        for ( String s : list ) {
            ArrayList < String > links = getLink ( s, 0.0, 10.0 );
            if ( links != null && links.size ( ) != 0 ) {
                leftList.getItems ( ).add ( s );
                saveHTML ( links );
            }
        }

        System.out.println ( "Done Setting Up" );

    }


    public static ArrayList < String > parseHTML ( String path ) {
        System.out.println ("Parsing" );
        System.out.println ( );
        ArrayList < String > list = new ArrayList ( );
        System.out.println ( path );
        File in = new File ( path );
        Document d = null;
        try {
            d = Jsoup.parse ( in, null );
        } catch ( IOException ex ) {
            ex.printStackTrace ( );
        }
        Elements e = d.getElementsByClass ( "open" );
        for ( Element es : e ) {
            String res = "";
            String temp[] = es.toString ( ).split ( "\n" );
            res += ( temp[ 6 ].replaceAll ( "</?td>", "" ).trim ( ) ) + " ";
            res += ( temp[ 7 ].replaceAll ( "</?td>", "" ).trim ( ) ) + "";
            res = res.replaceAll ( "&nbsp;", "" ).trim ( );
            if ( res.length ( ) != 0 ) {
                list.add ( res );
            }
        }
        System.out.println ("Done Parsing" );
        return list;
    }

    public static ArrayList < String > startVulns ( String q, double min, double max ) {
        System.out.println ("Starting Vulns" );
        ArrayList < String > res = getLink ( q, min, max );
        System.out.println ("Done Vulns" );
        if ( res == null || res.size ( ) == 0 ) {
            return null;
        } else {
            return res;
        }
    }

    public static ArrayList getLink ( String service, double min, double max ) {
        System.out.println ("Getting Link" );
        String res = "";
        String u = "http://www.cvedetails.com/google-search-results.php?q=";
        service = service.replaceAll ( " ", "+" );
        String complete = u + service;
        service = service.replaceAll ( " ", "+" );

        try {
            Thread.sleep ( 5000 );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace ( );
        }

        for ( int i = 0 ; i < 2 ; i++ ) {
            try {
                Document doc = Jsoup
                        .connect ( "https://www.google.com/search?q=cvedetails " + service )
                        .userAgent ( "Mozilla/5.0" )
                        .timeout ( 5000 ).get ( );
                Elements links = doc.getElementsByAttributeValueContaining ( "href", "https://www.cvedetails.com/vulnerability-list" );
                Elements links2 = doc.getElementsByAttributeValueContaining ( "href", "cve/CVE" );

                String link = "";
                for ( Element li : links ) {
                    link = ( li.attr ( "href" ) );
                    break;
                }
                try {
                    res = ( link.substring ( 7 ) );
                    break;
                } catch ( Exception e ) {
                    //Returns null if no result is found.
                    return null;
                }
            } catch ( IOException ex ) {
                ex.printStackTrace ( );
            }
        }

        System.out.println ("Done Getting Link" );
        ///---------Got link---------///
        return listVulns ( res, min, max );

    }

    public static ArrayList listVulns ( String link, double min, double max ) {
        System.out.println ("Listing Vulns" );
        ArrayList < String > result = new ArrayList ( );
        try {
            Document doc = Jsoup.connect ( link ).get ( );
            Elements e = doc.getElementsByClass ( "srrowns" );
            for ( Element i : e ) {
                //<a href="/cve/CVE-2005-4873/" title="CVE-2005-4873 security vulnerability details">CVE-2005-4873</a>
                String href = "http://www.cvedetails.com" + i.getElementsByAttribute ( "href" ).attr ( "href" );
                double score = Double.parseDouble ( i.getElementsByClass ( "cvssbox" ).text ( ) );
                if ( score >= min && score <= max ) {
                    result.add ( href );
                }
            }
        } catch ( IOException ex ) {
            ex.printStackTrace ( );
        }
        System.out.println ("Done Listing Vulns" );
        return result;
    }

    public void saveHTML ( ArrayList < String > list ) {
        System.out.println ("Saving HTML" );
        for ( int i = 0 ; i < list.size ( ) ; i++ ) {
            try {
                Document doc = Jsoup
                        .connect ( list.get ( i ) )
                        .userAgent ( "Mozilla/5.0" )
                        .timeout ( 5000 ).get ( );

                File h = new File ( g.getOutputLocationFilename ( ) + "Files/" + i + ".html" );
                PrintWriter writer = new PrintWriter ( h );
                writer.write ( doc.toString ( ) );
                writer.close ( );

            } catch ( IOException ex ) {
                ex.printStackTrace ( );
            }
        }
        System.out.println ("Done Saving HTML" );
    }


}
