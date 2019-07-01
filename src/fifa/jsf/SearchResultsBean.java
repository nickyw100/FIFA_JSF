package fifa.jsf;

import fifa.utilities.PropertiesUtilities;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@ManagedBean
@RequestScoped
public class SearchResultsBean extends AbstractResultBean {
    private static final long serialVersionUID = 9024576010797062889L;
    protected String myTeamId;
    protected String myTeamName;
    protected String homeTeamName;
    protected String awayTeamName;
    protected String homeTeamLogo;
    protected String awayTeamLogo;
    protected String playerComments;
    protected StatsBean.GameTypeEnum gameType;
    protected String countryId;
    protected String countryName;
    protected String gameDateTimeStr;
    protected StatsBean.HomeAwayEnum homeAway;
    protected int division;
    protected boolean matchAbandoned;
    protected String logoImage;
    protected String myLogoImage;
    protected String flagImage;

    public SearchResultsBean () {

    }

    public SearchResultsBean (String versionId, String myTeamId, String myTeamName, String homeTeamName, String awayTeamName, String homeTeamLogo, String awayTeamLogo,
                              String playerComments, StatsBean.GameTypeEnum gameType, String countryId, String countryName, Date gameDateTime,
                              StatsBean.HomeAwayEnum homeAway, int division, boolean matchAbandoned, String logoImage, String myLogoImage, String flagImage,
                              String playerName, int goalsFor, int goalsAgainst, boolean extraTime, int penaltiesFor, int penaltiesAgainst,
                              int possessionPercentage, int shotAccuracyPercentage, int shots, int shotsOnTarget, int opponentShots, int opponentShotsOnTarget,
                              int opponentDivision, String gameComments){

        this.versionId = versionId;
        this.myTeamId = myTeamId;
        this.myTeamName = myTeamName;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamLogo = awayTeamLogo;
        this.playerName = playerName;
        this.playerComments = playerComments;
        this.gameType = gameType;
        this.countryId = countryId;
        this.countryName = countryName;
        this.gameDateTime = gameDateTime;
        this.homeAway = homeAway;
        this.division = division;
        this.matchAbandoned = matchAbandoned;
        this.logoImage = logoImage;
        this.myLogoImage = myLogoImage;
        this.flagImage = flagImage;

        this.playerName = playerName;
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

    }

    public void setGameDateTime(Date gameDateTime) {
        this.gameDateTime = gameDateTime;
    }


    public int getDivision() {
        return this.division;
    }


    public void setDivision(int division) {
        this.division = division;
    }


    public boolean isMatchAbandoned() {
        return this.matchAbandoned;
    }


    public void setMatchAbandoned(boolean matchAbandoned) {
        this.matchAbandoned = matchAbandoned;
    }


    public String getCountryId() {
        return this.countryId;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }


    public String getLogoImage() {
        return this.logoImage;
    }


    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }


    public String getGameDateTimeStr() {
        Date gameDateTime = getGameDateTime();
        this.gameDateTimeStr = formatDate(gameDateTime);
        return this.gameDateTimeStr;
    }


    public void setGameDateTimeStr(String gameDateTimeStr) {
        this.gameDateTimeStr = gameDateTimeStr;
    }


    private String formatDate(Date inputDate) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


        return df.format(inputDate);
    }


    public String getFlagImage() {
        return this.flagImage;
    }


    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
    }


    public String getCountryName() {
        return this.countryName;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public boolean isWin() {
        if (getGoalsFor() > getGoalsAgainst()) {
            return true;
        }
        return getPenaltiesFor() > getPenaltiesAgainst();

    }

    public String getGameTypeDescription() {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String competition = "";
        if (this.gameType != null) {
            competition = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), this.gameType.getValue());
            if (this.gameType.equals(StatsBean.GameTypeEnum.Season)) {
                competition = competition + ": " + getDivision();
            }
        }
        return competition;
    }


    public String getPlayerComments() {
        return this.playerComments;
    }


    public void setPlayerComments(String playerComments) {
        this.playerComments = playerComments;
    }


    public String getHomeTeamName() {
        return this.homeTeamName;
    }


    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }


    public String getAwayTeamName() {
        return this.awayTeamName;
    }


    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }


    public String getMyTeamId() {
        return this.myTeamId;
    }


    public void setMyTeamId(String myTeamId) {
        this.myTeamId = myTeamId;
    }


    public String getMyTeamName() {
        return this.myTeamName;
    }


    public void setMyTeamName(String myTeamName) {
        this.myTeamName = myTeamName;
    }


    public String getMyLogoImage() {
        return this.myLogoImage;
    }


    public void setMyLogoImage(String myLogoImage) {
        this.myLogoImage = myLogoImage;
    }


    public String getHomeTeamLogo() {
        return this.homeTeamLogo;
    }


    public void setHomeTeamLogo(String homeTeamLogo) {
        this.homeTeamLogo = homeTeamLogo;
    }


    public String getAwayTeamLogo() {
        return this.awayTeamLogo;
    }


    public void setAwayTeamLogo(String awayTeamLogo) {
        this.awayTeamLogo = awayTeamLogo;
    }


    public String getResultColor() {
        if (getGoalsFor() == getGoalsAgainst() && !isExtraTime()) {
            return "black";
        }
        if (getGoalsFor() > getGoalsAgainst()) {
            return "green";
        }
        if (getPenaltiesFor() > getPenaltiesAgainst()) {
            return "green";
        }
        return "red";
    }


    public int getAggregateScore() {
        return getGoalsFor() + getPenaltiesFor() - getGoalsAgainst() + getPenaltiesAgainst();
    }


    public int getOpponentDivision() {
        return this.opponentDivision;
    }


    public void setOpponentDivision(int opponentDivision) {
        this.opponentDivision = opponentDivision;
    }


    public StatsBean.GameTypeEnum getGameType() {
        return this.gameType;
    }


    public void setGameType(StatsBean.GameTypeEnum gameType) {
        this.gameType = gameType;
    }


    public StatsBean.HomeAwayEnum getHomeAway() {
        return this.homeAway;
    }


    public void setHomeAway(StatsBean.HomeAwayEnum homeAway) {
        this.homeAway = homeAway;
    }
}
