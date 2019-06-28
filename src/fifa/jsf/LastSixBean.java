package fifa.jsf;

import fifa.dao.StatsDao;

import javax.faces.bean.ManagedBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@ManagedBean
public class LastSixBean
        extends AbstractResultBean {
    private static final long serialVersionUID = -6485511691963816181L;
    private List<LastSixBean> results;
    private String winLossDraw;
    private String gameDateTimeStr;

    public void setGameDateTime(Date gameDateTime) {
        setGameDateTimeStr(formatDate(gameDateTime));
    }


    public String getWinLossDraw() {
        return this.winLossDraw;
    }


    public void setWinLossDraw(String winLossDraw) {
        this.winLossDraw = winLossDraw;
    }


    public List<LastSixBean> getResults() {
        StatsDao statsDao = new StatsDao();

        this.results = statsDao.getLastSix("C");

        return this.results;
    }


    public void setResults(List<LastSixBean> results) {
        this.results = results;
    }


    public String getGameDateTimeStr() {
        return this.gameDateTimeStr;
    }


    public void setGameDateTimeStr(String gameDateTimeStr) {
        this.gameDateTimeStr = gameDateTimeStr;
    }


    private String formatDate(Date inputDate) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


        return df.format(inputDate);
    }


    public String getWinComment() {
        if (getPenaltiesAgainst() == 0 && getPenaltiesFor() == 0) {
            return "Beat " + getTeamName() + " " + getGoalsFor() + "-" + getGoalsAgainst() + " played by " + getPlayerName() +
                    " on " + getGameDateTimeStr();
        }
        return "Beat " + getTeamName() + " " + getGoalsFor() + "-" + getGoalsAgainst() + " (" + getPenaltiesFor() + " - " +
                getPenaltiesAgainst() + " P) played by " + getPlayerName() + " on " + getGameDateTimeStr();
    }


    public String getLossComment() {
        if (getPenaltiesAgainst() == 0 && getPenaltiesFor() == 0) {
            return "Lost to " + getTeamName() + " " + getGoalsFor() + "-" + getGoalsAgainst() + " played by " + getPlayerName() +
                    " on " + getGameDateTimeStr();
        }
        return "Lost to " + getTeamName() + " " + getGoalsFor() + "-" + getGoalsAgainst() + " (" + getPenaltiesFor() + " - " +
                getPenaltiesAgainst() + " P) played by " + getPlayerName() + " on " + getGameDateTimeStr();
    }


    public String getDrawComment() {
        return "Drew with " + getTeamName() + " " + getGoalsFor() + "-" + getGoalsAgainst() + " played by " + getPlayerName() +
                " on " + getGameDateTimeStr();
    }
}
