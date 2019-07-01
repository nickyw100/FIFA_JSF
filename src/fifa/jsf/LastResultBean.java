package fifa.jsf;

import fifa.dao.StatsDao;
import fifa.dao.TeamDao;
import fifa.utilities.FIFAConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class LastResultBean extends SearchResultsBean {
    private static final long serialVersionUID = 6199312392098189907L;
    private static Logger logger = Logger.getLogger(LastResultBean.class);

    private List<LastResultBean> lastResults;

    public LastResultBean () {
        // All managed beans need a default constructor.
    }

    public LastResultBean(SearchResultsBean searchResultsBean, List<LastResultBean> lastResults) {

        this.myTeamId = searchResultsBean.myTeamId;
        this.myTeamName = searchResultsBean.myTeamName;
        this.homeTeamName = searchResultsBean.homeTeamName;
        this.awayTeamName = searchResultsBean.awayTeamName;
        this.homeTeamLogo = searchResultsBean.homeTeamLogo;
        this.awayTeamLogo = searchResultsBean.awayTeamLogo;
        this.playerComments = searchResultsBean.playerComments;
        this.gameType = searchResultsBean.gameType;
        this.countryId = searchResultsBean.countryId;
        this.countryName = searchResultsBean.countryName;
        this.gameDateTimeStr = searchResultsBean.gameDateTimeStr;
        this.homeAway = searchResultsBean.homeAway;
        this.division = searchResultsBean.division;
        this.matchAbandoned = searchResultsBean.matchAbandoned;
        this.logoImage = searchResultsBean.logoImage;
        this.myLogoImage = searchResultsBean.myLogoImage;
        this.flagImage = searchResultsBean.flagImage;
        this.teamName = searchResultsBean.teamName;
        this.extraTime = searchResultsBean.extraTime;
        this.penaltiesFor = searchResultsBean.penaltiesFor;
        this.penaltiesAgainst = searchResultsBean.penaltiesAgainst;
        this.possessionPercentage = searchResultsBean.possessionPercentage;
        this.shotAccuracyPercentage = searchResultsBean.shotAccuracyPercentage;
        this.shots = searchResultsBean.shots;
        this.shotsOnTarget = searchResultsBean.shotsOnTarget;
        this.opponentShots = searchResultsBean.opponentShots;
        this.opponentShotsOnTarget = searchResultsBean.opponentShotsOnTarget;
        this.opponentDivision = searchResultsBean.opponentDivision;
        this.gameComments = searchResultsBean.gameComments;

        if (StringUtils.isBlank(myTeamName)) {
            TeamDao teamDao = new TeamDao();

            myTeamName = teamDao.getTeamName(FIFAConstants.defaultCountry, myTeamId);
            myLogoImage = teamDao.getTeamLogo(FIFAConstants.defaultCountry, myTeamId);
        }

        if (this.homeAway.equals(StatsBean.HomeAwayEnum.Home)) {
            this.awayTeamName = teamName;
            this.homeTeamName = myTeamName;
            this.homeTeamLogo = myLogoImage;
            this.awayTeamLogo = logoImage;
        } else {
            this.homeTeamName = teamName;
            this.awayTeamName = myTeamName;
            this.homeTeamLogo = logoImage;
            this.awayTeamLogo = myLogoImage;
        }

        this.lastResults = lastResults;

    }

    public List<LastResultBean> getLastResults() {
        logger.debug("Entering fifa.jsf.LastResultBean.getLastResults()");

        StatsDao statsDao = new StatsDao();

        if (this.lastResults == null) {
            this.lastResults = statsDao.getLastResult();
        }

        return this.lastResults;
    }

    public void setLastResults(List<LastResultBean> results) {
        this.lastResults = results;
    }
}
