package fifa.jsf;

import fifa.dao.OverallResultsDao;
import fifa.utilities.FIFAConstants;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
public class OverallResultsBean
        implements FIFAConstants {
    protected List<Integer> overallResultsList = new ArrayList();
    private Integer gamesWon;
    private Integer gamesDrawn;
    private Integer gamesLost;

    public Integer getGamesWon() {
        return this.gamesWon;
    }


    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }


    public Integer getGamesDrawn() {
        return this.gamesDrawn;
    }


    public void setGamesDrawn(Integer gamesDrawn) {
        this.gamesDrawn = gamesDrawn;
    }


    public Integer getGamesLost() {
        return this.gamesLost;
    }


    public void setGamesLost(Integer gamesLost) {
        this.gamesLost = gamesLost;
    }


    public List<Integer> getOverallResultsList(String versionId) {
        OverallResultsDao overallResultsDao = new OverallResultsDao();
        this.overallResultsList = overallResultsDao.getOverallResultsList(versionId, "A");
        return this.overallResultsList;
    }


    public void setOverallResultsList(List<Integer> resultsList) {
        this.overallResultsList = resultsList;
    }
}
