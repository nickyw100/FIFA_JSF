// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchResultsBean.java

package fifa.jsf;

import fifa.utilities.PropertiesUtilities;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package fifa.jsf:
//            AbstractResultBean, StatsBean

public class SearchResultsBean extends AbstractResultBean
{

    public SearchResultsBean()
    {
    }

    public void setGameDateTime(Date gameDateTime)
    {
        this.gameDateTime = gameDateTime;
    }

    public int getDivision()
    {
        return division;
    }

    public void setDivision(int division)
    {
        this.division = division;
    }

    public boolean isMatchAbandoned()
    {
        return matchAbandoned;
    }

    public void setMatchAbandoned(boolean matchAbandoned)
    {
        this.matchAbandoned = matchAbandoned;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getLogoImage()
    {
        return logoImage;
    }

    public void setLogoImage(String logoImage)
    {
        this.logoImage = logoImage;
    }

    public String getGameDateTimeStr()
    {
        Date gameDateTime = getGameDateTime();
        gameDateTimeStr = formatDate(gameDateTime);
        return gameDateTimeStr;
    }

    public void setGameDateTimeStr(String gameDateTimeStr)
    {
        this.gameDateTimeStr = gameDateTimeStr;
    }

    private String formatDate(Date inputDate)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        return df.format(inputDate);
    }

    public String getFlagImage()
    {
        return flagImage;
    }

    public void setFlagImage(String flagImage)
    {
        this.flagImage = flagImage;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public boolean isWin()
    {
        if(getGoalsFor() > getGoalsAgainst())
            return true;
        return getPenaltiesFor() > getPenaltiesAgainst();
    }

    public String getGameTypeDescription()
    {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String competition = "";
        if(gameType != null)
        {
            competition = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), gameType.getValue());
            if(gameType.equals(StatsBean.GameTypeEnum.Season))
                competition = (new StringBuilder(String.valueOf(competition))).append(": ").append(getDivision()).toString();
        }
        return competition;
    }

    public String getPlayerComments()
    {
        return playerComments;
    }

    public void setPlayerComments(String playerComments)
    {
        this.playerComments = playerComments;
    }

    public String getHomeTeamName()
    {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName)
    {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName()
    {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName)
    {
        this.awayTeamName = awayTeamName;
    }

    public String getMyTeamId()
    {
        return myTeamId;
    }

    public void setMyTeamId(String myTeamId)
    {
        this.myTeamId = myTeamId;
    }

    public String getMyTeamName()
    {
        return myTeamName;
    }

    public void setMyTeamName(String myTeamName)
    {
        this.myTeamName = myTeamName;
    }

    public String getMyLogoImage()
    {
        return myLogoImage;
    }

    public void setMyLogoImage(String myLogoImage)
    {
        this.myLogoImage = myLogoImage;
    }

    public String getHomeTeamLogo()
    {
        return homeTeamLogo;
    }

    public void setHomeTeamLogo(String homeTeamLogo)
    {
        this.homeTeamLogo = homeTeamLogo;
    }

    public String getAwayTeamLogo()
    {
        return awayTeamLogo;
    }

    public void setAwayTeamLogo(String awayTeamLogo)
    {
        this.awayTeamLogo = awayTeamLogo;
    }

    public String getResultColor()
    {
        if(getGoalsFor() == getGoalsAgainst() && !isExtraTime())
            return "black";
        if(getGoalsFor() > getGoalsAgainst())
            return "green";
        if(getPenaltiesFor() > getPenaltiesAgainst())
            return "green";
        else
            return "red";
    }

    public int getAggregateScore()
    {
        return (getGoalsFor() + getPenaltiesFor()) - (getGoalsAgainst() + getPenaltiesAgainst());
    }

    public int getOpponentDivision()
    {
        return opponentDivision;
    }

    public void setOpponentDivision(int opponentDivision)
    {
        this.opponentDivision = opponentDivision;
    }

    public StatsBean.GameTypeEnum getGameType()
    {
        return gameType;
    }

    public void setGameType(StatsBean.GameTypeEnum gameType)
    {
        this.gameType = gameType;
    }

    public StatsBean.HomeAwayEnum getHomeAway()
    {
        return homeAway;
    }

    public void setHomeAway(StatsBean.HomeAwayEnum homeAway)
    {
        this.homeAway = homeAway;
    }

    private static final long serialVersionUID = 0x4c121ae9L;
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
}
