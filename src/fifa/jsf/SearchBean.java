package fifa.jsf;

import fifa.dao.SearchDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;





@ManagedBean
@ViewScoped
public class SearchBean
        implements Serializable
{
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
    private List<SearchResultsBean> searchResults = null;


    @PostConstruct
    public void init() { this.searchResults = null; }



    public String getVersionId() { return this.versionId; }



    public void setVersionId(String versionId) { this.versionId = versionId; }



    public String getTeamName() { return this.teamName; }



    public void setTeamName(String teamName) { this.teamName = teamName; }



    public String getPlayerName() { return this.playerName; }



    public void setPlayerName(String playerName) { this.playerName = playerName; }


    public StatsBean.GameTypeEnum getGameType() {
        if (this.gameType == null) {
            this.gameType = StatsBean.GameTypeEnum.All;
        }
        return this.gameType;
    }


    public void setGameType(StatsBean.GameTypeEnum gameType) { this.gameType = gameType; }



    public StatsBean.GameTypeEnum[] getGameTypeEnums() { return StatsBean.GameTypeEnum.values(); }



    public Date getGameDateTime() { return this.gameDateTime; }



    public void setGameDateTime(Date gameDateTime) { this.gameDateTime = gameDateTime; }


    public StatsBean.HomeAwayEnum getHomeAway() {
        if (this.homeAway == null) {
            this.homeAway = StatsBean.HomeAwayEnum.Both;
        }
        return this.homeAway;
    }


    public void setHomeAway(StatsBean.HomeAwayEnum homeAway) { this.homeAway = homeAway; }



    public StatsBean.HomeAwayEnum[] getHomeAwayEnums() { return StatsBean.HomeAwayEnum.values(); }


    public StatsBean.SearchCriteriaEnum getSearchCriteria() {
        if (this.searchCriteria == null) {
            this.searchCriteria = StatsBean.SearchCriteriaEnum.Equals;
        }
        return this.searchCriteria;
    }


    public void setSearchCriteria(StatsBean.SearchCriteriaEnum searchCriteria) { this.searchCriteria = searchCriteria; }



    public int getDivision() { return this.division; }



    public void setDivision(int division) { this.division = division; }



    public boolean isMatchAbandoned() { return this.matchAbandoned; }



    public void setMatchAbandoned(boolean matchAbandoned) { this.matchAbandoned = matchAbandoned; }



    public boolean isExtraTime() { return this.extraTime; }



    public void setExtraTime(boolean extraTime) { this.extraTime = extraTime; }



    public Date getFromDate() { return this.fromDate; }



    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }



    public Date getToDate() { return this.toDate; }



    public void setToDate(Date toDate) { this.toDate = toDate; }



    public String getCountryName() { return this.countryName; }



    public void setCountryName(String countryName) { this.countryName = countryName; }



    public int getPossessionPercentage() { return this.possessionPercentage; }



    public void setPossessionPercentage(int possessionPercentage) { this.possessionPercentage = possessionPercentage; }


    public void search() {
        SearchDao searchDao = new SearchDao();
        this.searchResults = searchDao.getSearchResults(getTeamName(), getPlayerName(), getHomeAway(), getFromDate(), getToDate(),
                getGameType(), getDivision(), getSearchCriteria(), getPossessionPercentage(), isMatchAbandoned(), isExtraTime(),
                getCountryName());
    }


    public List<SearchResultsBean> getSearchResults() { return this.searchResults; }



    public void setSearchResults(List<SearchResultsBean> searchResults) { this.searchResults = searchResults; }


    public void reset() {
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

    private void resetPlayerBeanName() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

        PlayerBean playerBean = (PlayerBean)request.getSession().getAttribute("playerBean");
        if (playerBean != null)
            playerBean.setPlayerName(null);
    }
}
