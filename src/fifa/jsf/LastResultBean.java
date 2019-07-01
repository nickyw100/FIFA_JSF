package fifa.jsf;

import fifa.dao.StatsDao;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;

@ManagedBean
public class LastResultBean extends SearchResultsBean {
    private static final long serialVersionUID = 6199312392098189907L;
    private static Logger logger = Logger.getLogger(LastResultBean.class);

    private List<LastResultBean> lastResult;

    public LastResultBean () {

    }

    public LastResultBean(String versionId, String teamName, String playerName, Date gameDateTime, int goalsFor, int goalsAgainst, boolean extraTime,
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

    }


    public List<LastResultBean> getLastResult() {
        logger.debug("Entering fifa.jsf.LastResultBean.getLastResult()");

        StatsDao statsDao = new StatsDao();

        if (this.lastResult == null) {
            this.lastResult = statsDao.getLastResult();
        }

        return this.lastResult;
    }


    public void setLastResult(List<LastResultBean> results) {
        this.lastResult = results;
    }
}
