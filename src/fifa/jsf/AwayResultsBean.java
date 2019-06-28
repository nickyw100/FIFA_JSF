package fifa.jsf;

import fifa.dao.AwayResultsDao;
import fifa.utilities.FIFAConstants;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
public class AwayResultsBean
        implements FIFAConstants {
    protected List<Integer> awayResultsList = new ArrayList();
    private int gamesWon;
    private int gamesDrawn;
    private int gamesLost;

    public int getGamesWon() {
        return this.gamesWon;
    }


    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }


    public int getGamesDrawn() {
        return this.gamesDrawn;
    }


    public void setGamesDrawn(int gamesDrawn) {
        this.gamesDrawn = gamesDrawn;
    }


    public int getGamesLost() {
        return this.gamesLost;
    }


    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }


    public List<Integer> getResultsList() {
        return this.awayResultsList;
    }

    public void setResultsList(List resultsList) {
        awayResultsList = Arrays.asList(Integer.valueOf(getGamesWon()), Integer.valueOf(getGamesDrawn()), Integer.valueOf(getGamesLost()));
    }

    public List<Integer> getAwayResultsList(String versionId) {
        AwayResultsDao awayResultsDao = new AwayResultsDao();
        this.awayResultsList = awayResultsDao.getAwayResultsList(versionId, "A");
        return this.awayResultsList;
    }


    public void setAwayResultsList(List<Integer> resultsList) {
        this.awayResultsList = resultsList;
    }
}
