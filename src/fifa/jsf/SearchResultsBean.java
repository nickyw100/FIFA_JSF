package fifa.jsf;

import fifa.utilities.PropertiesUtilities;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@ManagedBean
@RequestScoped
public class SearchResultsBean
        extends AbstractResultBean {
    private static final long serialVersionUID = 9024576010797062889L;
    private String myTeamId;
    private String myTeamName;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamLogo;
    private String awayTeamLogo;
    private String playerComments;
    private StatsBean.GameTypeEnum gameType;
    private String countryId;
    private String countryName;
    private String gameDateTimeStr;
    private StatsBean.HomeAwayEnum homeAway;
    private int division;
    private int opponentDivision;
    private boolean matchAbandoned;
    private String logoImage;
    private String myLogoImage;
    private String flagImage;

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
