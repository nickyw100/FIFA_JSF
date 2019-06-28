// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchBean.java

package fifa.jsf;

import fifa.dao.SearchDao;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

// Referenced classes of package fifa.jsf:
//            PlayerBean, StatsBean

public class SearchBean
    implements Serializable
{

    public SearchBean()
    {
        searchResults = null;
    }

    public void init()
    {
        searchResults = null;
    }

    public String getVersionId()
    {
        return versionId;
    }

    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public StatsBean.GameTypeEnum getGameType()
    {
        if(gameType == null)
            gameType = StatsBean.GameTypeEnum.All;
        return gameType;
    }

    public void setGameType(StatsBean.GameTypeEnum gameType)
    {
        this.gameType = gameType;
    }

    public StatsBean.GameTypeEnum[] getGameTypeEnums()
    {
        return StatsBean.GameTypeEnum.values();
    }

    public Date getGameDateTime()
    {
        return gameDateTime;
    }

    public void setGameDateTime(Date gameDateTime)
    {
        this.gameDateTime = gameDateTime;
    }

    public StatsBean.HomeAwayEnum getHomeAway()
    {
        if(homeAway == null)
            homeAway = StatsBean.HomeAwayEnum.Both;
        return homeAway;
    }

    public void setHomeAway(StatsBean.HomeAwayEnum homeAway)
    {
        this.homeAway = homeAway;
    }

    public StatsBean.HomeAwayEnum[] getHomeAwayEnums()
    {
        return StatsBean.HomeAwayEnum.values();
    }

    public StatsBean.SearchCriteriaEnum getSearchCriteria()
    {
        if(searchCriteria == null)
            searchCriteria = StatsBean.SearchCriteriaEnum.Equals;
        return searchCriteria;
    }

    public void setSearchCriteria(StatsBean.SearchCriteriaEnum searchCriteria)
    {
        this.searchCriteria = searchCriteria;
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

    public boolean isExtraTime()
    {
        return extraTime;
    }

    public void setExtraTime(boolean extraTime)
    {
        this.extraTime = extraTime;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public int getPossessionPercentage()
    {
        return possessionPercentage;
    }

    public void setPossessionPercentage(int possessionPercentage)
    {
        this.possessionPercentage = possessionPercentage;
    }

    public void search()
    {
        SearchDao searchDao = new SearchDao();
        searchResults = searchDao.getSearchResults(getTeamName(), getPlayerName(), getHomeAway(), getFromDate(), getToDate(), getGameType(), getDivision(), getSearchCriteria(), getPossessionPercentage(), isMatchAbandoned(), isExtraTime(), getCountryName());
    }

    public List getSearchResults()
    {
        return searchResults;
    }

    public void setSearchResults(List searchResults)
    {
        this.searchResults = searchResults;
    }

    public void reset()
    {
        resetPlayerBeanName();
        setPlayerName(null);
        setTeamName(null);
        setCountryName(null);
        setToDate(null);
        setFromDate(null);
        setExtraTime(false);
        setMatchAbandoned(false);
        setDivision(0);
        setPossessionPercentage(0);
        setHomeAway(StatsBean.HomeAwayEnum.Both);
        setGameType(StatsBean.GameTypeEnum.All);
        setSearchResults(null);
    }

    private void resetPlayerBeanName()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        PlayerBean playerBean = (PlayerBean)request.getSession().getAttribute("playerBean");
        if(playerBean != null)
            playerBean.setPlayerName(null);
    }

    private static final long serialVersionUID = 1L;
    private String versionId;
    private String teamName;
    private String playerName;
    private StatsBean.GameTypeEnum gameType;
    private Date gameDateTime;
    private StatsBean.HomeAwayEnum homeAway;
    private StatsBean.SearchCriteriaEnum searchCriteria;
    private int division;
    private int possessionPercentage;
    private boolean matchAbandoned;
    private boolean extraTime;
    private Date fromDate;
    private Date toDate;
    private String countryName;
    List searchResults;
}
