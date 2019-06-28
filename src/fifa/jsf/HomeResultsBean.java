package fifa.jsf;

import fifa.dao.HomeResultsDao;
import fifa.utilities.FIFAConstants;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
public class HomeResultsBean
        implements FIFAConstants {
    protected List<Integer> homeResultsList = new ArrayList();
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
        return this.homeResultsList;
    }


    public void setResultsList(List resultsList) {
        homeResultsList = Arrays.asList(Integer.valueOf(getGamesWon()), Integer.valueOf(getGamesDrawn()), Integer.valueOf(getGamesLost()));
    }

    public List<Integer> getHomeResultsList(String versionId) {
        HomeResultsDao homeResultsDao = new HomeResultsDao();
        this.homeResultsList = homeResultsDao.getHomeResultsList(versionId, "A");
        return this.homeResultsList;
    }


    public void setHomeResultsList(List<Integer> resultsList) {
        this.homeResultsList = resultsList;
    }
}
