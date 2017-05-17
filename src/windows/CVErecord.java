package windows;

/**
 * Created by wintson on 5/17/17.
 */
public class CVErecord {
    String link;
    double score;

    public CVErecord ( String link, double score ) {
        this.link = link;
        this.score = score;
    }

    public String getLink ( ) {
        return link;
    }

    public void setLink ( String link ) {
        this.link = link;
    }

    public double getScore ( ) {
        return score;
    }

    public void setScore ( double score ) {
        this.score = score;
    }
}
