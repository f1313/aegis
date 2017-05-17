package windows;

import com.sun.javafx.tk.Toolkit;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import Util.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    Browser browser = new Browser ( );
    BrowserView view = new BrowserView ( browser );
    VBox vb = new VBox ( );
    VBox bottom = new VBox ( );
    Label status = new Label ( );

    public CVEdetails ( ) {
        tree = new TreeView ( root );
        this.stage.setScene ( new Scene ( this.serviceBorderPane ) );
        Label l = new Label ( "CVE Details Scan" );
        l.setPadding ( new Insets ( 10, 10, 10, 10 ) );
        vb.getChildren ( ).add ( l );
        serviceBorderPane.setTop ( vb );
        serviceBorderPane.setLeft ( tree );
        serviceBorderPane.setMinSize ( 500, 500 );
        serviceBorderPane.setCenter ( view );
        bottom.getChildren ( ).add ( status );
        status.setPadding ( new Insets ( 4, 4, 4, 4 ) );
        serviceBorderPane.setBottom ( bottom );
    }

    public void pre ( Group g ) {
        this.g = g;
        final Service thread = new Service < Integer > ( ) {
            @Override
            public Task createTask ( ) {
                return new Task < Integer > ( ) {
                    @Override
                    protected Integer call ( ) throws Exception {
                        setUpParser ( );
                        return null;
                    }
                };
            }
        };
        thread.start ( );

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


    public void setUpParser ( ) {


        ArrayList < String > list = parseHTML ( g.getOutputLocationFilename ( ) + "/" + g.getGroupName ( ) + ".html" );
        for ( String s : list ) {
            ArrayList < CVErecord > links = getLink ( s, 0.0, 10.0 );
            if ( links != null && links.size ( ) != 0 ) {
                TreeItem < VBox > temp = new TreeItem ( s );
                root.getChildren ( ).add ( temp );
                saveHTML ( links, temp );
            }
        }


    }


    public ArrayList < String > parseHTML ( String path ) {


//        System.out.println ( );
        ArrayList < String > list = new ArrayList ( );
//        System.out.println ( path );
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
        return list;
    }

    public ArrayList < String > startVulns ( String q, double min, double max ) {

        ArrayList < String > res = getLink ( q, min, max );
        if ( res == null || res.size ( ) == 0 ) {
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
            } catch ( IOException ex ) {
                ex.printStackTrace ( );
            }
        }

        ///---------Got link---------///
        return listVulns ( res, min, max );

    }

    public ArrayList listVulns ( String link, double min, double max ) {

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

}
