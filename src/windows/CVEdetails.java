package windows;

import com.sun.javafx.tk.Toolkit;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import Util.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Util.*;

/**
 * Created by wintson on 5/15/17.
 */
public class CVEdetails {
    TreeView < VBox > tree;
    TreeItem < VBox > root = new TreeItem ( "Services" );
    public BorderPane serviceBorderPane = new BorderPane ( );
    Stage stage = new Stage ( );
    ListView leftList = new ListView <> ( );
    Group g = null;
    TextField min = new TextField ( );
    TextField max = new TextField ( );
    Browser browser = new Browser ( );
    BrowserView view = new BrowserView ( browser );
    HBox hb = new HBox ( );
    VBox bottom = new VBox ( );
    ProgressIndicator pi = new ProgressIndicator ( );
    Button start = new Button ( "Start" );

    public CVEdetails ( ) {
        tree = new TreeView ( root );
        this.stage.setScene ( new Scene ( this.serviceBorderPane ) );
        setupHB ( );
        root.setExpanded ( true );
        serviceBorderPane.setTop ( hb );
        serviceBorderPane.setLeft ( tree );
        serviceBorderPane.setMinSize ( 900, 600 );
        serviceBorderPane.setCenter ( view );
        System.out.println ( "Out of here" );
    }

    public void setupHB ( ) {
        Label l = new Label ( "CVE Details Scan" );
        hb.setPadding ( new Insets ( 10, 10, 10, 10 ) );
        hb.getChildren ( ).add ( l );
        Label l1 = new Label ( "Min Score" );
        l1.setPadding ( new Insets ( 0, 7, 0, 50 ) );
        hb.getChildren ( ).add ( l1 );
        min.setText ( "0.0" );
        min.setMaxWidth ( 50 );
        hb.getChildren ( ).add ( min );
        Label l2 = new Label ( "Max Score" );
        l2.setPadding ( new Insets ( 0, 7, 0, 20 ) );
        max.setMaxWidth ( 50 );
        max.setText ( "10.0" );
        Separator separator = new Separator ( );
        separator.setOrientation ( Orientation.HORIZONTAL );
        separator.setMinWidth ( 20 );
        separator.setVisible ( false );
        hb.getChildren ( ).add ( l2 );
        hb.getChildren ( ).add ( max );
        hb.getChildren ( ).add ( separator );
        hb.getChildren ( ).add ( start );
        pi.setProgress ( ProgressIndicator.INDETERMINATE_PROGRESS );
        pi.setMaxSize ( 26, 26 );
        Separator sep = new Separator ( );
        sep.setOrientation ( Orientation.HORIZONTAL );
        sep.setMinWidth ( 15 );
        sep.setVisible ( false );
        hb.getChildren ( ).add ( sep );
        hb.getChildren ( ).add ( pi );
        pi.setVisible ( false );
    }

    public void pre ( Group g ) {
        this.g = g;
        final Service thread = new Service < Integer > ( ) {
            @Override
            public Task createTask ( ) {
                return new Task < Integer > ( ) {
                    @Override
                    protected Integer call ( ) throws Exception {
                        pi.setVisible ( true );
                        setUpParser ( Double.parseDouble ( min.getText ( ) ),
                                Double.parseDouble ( max.getText ( ) ) );
                        pi.setVisible ( false );
                        return null;
                    }
                };
            }
        };


        start.setOnMouseClicked ( event -> {
            if ( ! g.isCveDetailsItemCheck ( ) ) {
                thread.start ( );
                g.setCveDetailsItemCheck ( true );
            }
        } );

        tree.setOnMouseClicked ( event -> {
            TreeItem < VBox > temp = tree.getSelectionModel ( ).getSelectedItem ( );
            try {
                String fileName = ( ( Label ) ( temp.getValue ( ).getChildren ( ).get ( 0 ) ) ).getText ( );
//                System.out.println ( "File name is : " + fileName );
                if ( fileName.contains ( "CVE-" ) ) {
                    browser.loadURL ( "file:///" + g.getOutputLocationFilename ( ) + "Files/" + fileName + ".html" );
                }
            } catch ( Exception e ) {

            }
        } );

    }

    public void setUpParser ( double min, double max ) {

        System.out.println ( "Parser Set up" );
        ArrayList < String > list = parseHTML ( g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ) + ".html" );
        for ( String s : list ) {
            ArrayList < CVErecord > links = getLink ( s, min, max );
            if ( links != null && links.size ( ) != 0 ) {
                TreeItem < VBox > temp = new TreeItem ( s );
                root.getChildren ( ).add ( temp );
                saveHTML ( links, temp );
            }
        }

        g.setCveDetailsItemCheck ( false );
        System.out.println ( "Parser Done Setting up" );

    }


    public ArrayList < String > parseHTML ( String path ) {


        System.out.println ( "Parsing starting" );
        ArrayList < String > list = new ArrayList ( );
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
        System.out.println ( "Outa Here" );
        return list;
    }

    public ArrayList < String > startVulns ( String q, double min, double max ) {
        System.out.println ( "Vulns starting " );
        ArrayList < String > res = getLink ( q, min, max );
        if ( res == null || res.size ( ) == 0 ) {
            System.out.println ( "Vulns done" );
            return null;
        } else {
            return res;
        }
    }

    public ArrayList getLink ( String service, double min, double max ) {


        String res = "";
        String u = "http://www.cvedetails.com/google-search-results.php?q=";
        service = service.replaceAll ( " ", "+" );
        String complete = u + service;
        service = service.replaceAll ( " ", "+" );
        System.out.println ( "Got link " );
        try {
            Thread.sleep ( 5000 );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace ( );
        }

        for ( int i = 0 ; i < 2 ; i++ ) {
            try {
                Thread.currentThread ( ).join ( ThreadLocalRandom.current ( ).nextInt ( 5000, 10000 ) );
                Document doc = Jsoup
                        .connect ( "https://www.google.com/search?q=cvedetails " + service )
                        .userAgent ( "Mozilla/5.0" )
                        .timeout ( 20000 ).get ( );
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
            } catch ( IOException | InterruptedException ex ) {
                ex.printStackTrace ( );
            }
        }
        System.out.println ( "Outta link !" );
        ///---------Got link---------///
        return listVulns ( res, min, max );

    }

    public ArrayList listVulns ( String link, double min, double max ) {
        System.out.println ( "Listing vulns 2" );
        ArrayList < CVErecord > result = new ArrayList ( );
        try {
            Document doc = Jsoup.connect ( link ).get ( );
            Elements e = doc.getElementsByClass ( "srrowns" );
            for ( Element i : e ) {
                //<a href="/cve/CVE-2005-4873/" title="CVE-2005-4873 security vulnerability details">CVE-2005-4873</a>
                String href = "http://www.cvedetails.com" + i.getElementsByAttribute ( "href" ).attr ( "href" );
                double score = Double.parseDouble ( i.getElementsByClass ( "cvssbox" ).text ( ) );
                if ( score >= min && score <= max ) {
                    result.add ( new CVErecord ( href, score ) );
                }
            }
        } catch ( IOException ex ) {
            ex.printStackTrace ( );
        }
        System.out.println ( "Listing vulns Done 2" );
        return result;
    }

    public void saveHTML ( ArrayList < CVErecord > list, TreeItem < VBox > item ) {

        for ( int i = 0 ; i < list.size ( ) ; i++ ) {
            Document doc = null;
            try {
                doc = Jsoup
                        .connect ( list.get ( i ).getLink ( ) )
                        .userAgent ( "Mozilla/5.0" )
                        .timeout ( 20000 ).get ( );
                String cveTitle = list.get ( i ).getLink ( ).split ( "/" )[ 4 ];
                new File ( g.getOutputLocationFilename ( ) + "Files" ).mkdir ( );
                new File ( g.getOutputLocationFilename ( ) + "Files/" + cveTitle + ".html" ).createNewFile ( );
                File h = new File ( g.getOutputLocationFilename ( ) + "Files/" + cveTitle + ".html" );
                PrintWriter writer = new PrintWriter ( h );
                writer.write ( doc.toString ( ) );
                writer.close ( );
                prune ( g.getOutputLocationFilename ( ) + "Files/", cveTitle + ".html" );
                VBox vb = new VBox ( );
                Label l = new Label ( cveTitle );
                vb.getChildren ( ).add ( l );
                vb.setMaxWidth ( 150 );
                setColor ( vb, list.get ( i ).score );
                l.setPadding ( new Insets ( 4, 4, 4, 4 ) );
                item.getChildren ( ).add ( new TreeItem ( vb ) );
            } catch ( Exception e ) {
                e.printStackTrace ( );
                continue;
            }


        }
    }

    public boolean isCVE ( TreeItem < String > item ) {
        try {
            if ( item.getParent ( ) != null ) {
                return true;
            } else {
                return false;
            }
        } catch ( NullPointerException e ) {
            return false;
        }
    }

    public void setColor ( VBox vb, double score ) {
        //-fx-background-color: red
        String color = "";
        if ( score >= 0 && score <= 0.99 ) {
            color = "#00C400";
        } else if ( score >= 1 && score <= 2.99 ) {
            color = "#00E020";
        } else if ( score >= 3 && score <= 3.99 ) {
            color = "#CDFA00";
        } else if ( score >= 4 && score <= 4.99 ) {
            color = "#F5D700";
        } else if ( score >= 5 && score <= 5.99 ) {
            color = "#EFBF00";
        } else if ( score >= 6 && score <= 6.99 ) {
            color = "#EFB00F";
        } else if ( score >= 7 && score <= 7.99 ) {
            color = "#F7971F";
        } else if ( score >= 8 && score <= 8.99 ) {
            color = "#FC7E00";
        } else if ( score >= 9 && score <= 10 ) {
            color = "#F70000";
        }

        vb.setStyle ( "-fx-background-color: " + color );
    }

    public static void prune ( String path, String file ) throws Exception {
        java.io.File inFile = new java.io.File ( path + file );
        java.io.File outFile = new java.io.File ( path + "modified-" + file );
        java.io.PrintWriter output = new java.io.PrintWriter ( new java.io.FileOutputStream ( outFile, true ), true );
        Document doc = Jsoup.parse ( inFile, null );
        output.println ( "<html>" );
        output.println ( doc.getElementsByTag ( "head" )/*.toString ().replaceAll ( "/cvedetails\\.css" ,"cvedetails.css")*/ );
        output.println ( "<body>" );
        output.println ( doc.getElementById ( "contentdiv" ) );
        output.println ( "</body>" );
        output.println ( "</html>" );
        output.flush ( );
        output.close ( );
        inFile.delete ( );
        java.io.PrintWriter t = new java.io.PrintWriter ( new java.io.FileOutputStream ( outFile, true ), true );
        t.println ( "ENDENDEND" );
        t.flush ( );
        t.close ( );
        output = new java.io.PrintWriter ( new java.io.FileOutputStream ( inFile, true ), true );
        String line;
        input.init ( new java.io.FileInputStream ( outFile ) );
        while ( ! ( line = input.reader.readLine ( ) ).equals ( "ENDENDEND" ) ) {
            output.println ( line );
        }
        output.flush ( );
        output.close ( );
        outFile.delete ( );
    }

    private static void copyFileUsingStream ( File source, File dest ) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream ( source );
            os = new FileOutputStream ( dest );
            byte[] buffer = new byte[ 1024 ];
            int length;
            while ( ( length = is.read ( buffer ) ) > 0 ) {
                os.write ( buffer, 0, length );
            }
        } finally {
            is.close ( );
            os.close ( );
        }
    }

}
