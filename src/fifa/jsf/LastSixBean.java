package fifa.jsf;

import fifa.dao.StatsDao;

import javax.faces.bean.ManagedBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@ManagedBean
public class LastSixBean extends AbstractResultBean {
    private static final long serialVersionUID = -6485511691963816181L;
    private List<LastSixBean> results;
    private String winLossDraw;
    private String gameDateTimeStr;

    public LastSixBean () {

    }

    public LastSixBean(String versionId, String teamName, String playerName, Date gameDateTime, int goalsFor, int goalsAgainst, boolean extraTime,
                       int penaltiesFor, int penaltiesAgainst, int possessionPercentage, int shotAccuracyPercentage, int shots, int shotsOnTarget, int opponentShots, int opponentShotsOnTarget,
                       int opponentDivision, String gameComments) {
        this.versionId = versionId;
        this.teamName = teamName;
        this.playerName = playerName;
        this.gameDateTime = gameDateTime;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.extraTime = extraTime;
        this.penaltiesFor = penaltiesFor;
        this.penaltiesAgainst = penaltiesAgainst;
        this.possessionPercentage = possessionPercentage;
        this.shotAccuracyPercentage = shotAccuracyPercentage;
        this.shots = shots;
        this.shotsOnTarget = shotsOnTarget;
        this.opponentShots = opponentShots;
        this.opponentShotsOnTarget = opponentShotsOnTarget;
        this.opponentDivision = opponentDivision;
        this.gameComments = gameComments;

        if (goalsFor > goalsAgainst) {
            this.winLossDraw = "W";

        } else if (goalsFor < goalsAgainst) {
            this.winLossDraw = "L";

        } else if ((extraTime) && (penaltiesFor > penaltiesAgainst)) {
            this.winLossDraw = "W";
        } else if ((extraTime) && (penaltiesFor < penaltiesAgainst)) {
            this.winLossDraw = "L";
        } else {
            this.winLossDraw = "D";
        }
    }

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
