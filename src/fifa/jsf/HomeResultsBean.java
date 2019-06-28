// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeResultsBean.java

package fifa.jsf;

import fifa.dao.HomeResultsDao;
import fifa.utilities.FIFAConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeResultsBean
    implements FIFAConstants
{

    public HomeResultsBean()
    {
        homeResultsList = new ArrayList();
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon)
    {
        this.gamesWon = gamesWon;
    }

    public int getGamesDrawn()
    {
        return gamesDrawn;
    }

    public void setGamesDrawn(int gamesDrawn)
    {
        this.gamesDrawn = gamesDrawn;
    }

    public int getGamesLost()
    {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost)
    {
        this.gamesLost = gamesLost;
    }

    public List getResultsList()
    {
        return homeResultsList;
    }

    public void setResultsList(List resultsList)
    {
        homeResultsList = Arrays.asList(new Integer[] {
            Integer.valueOf(getGamesWon()), Integer.valueOf(getGamesDrawn()), Integer.valueOf(getGamesLost())
        });
    }

    public List getHomeResultsList(String versionId)
    {
        HomeResultsDao homeResultsDao = new HomeResultsDao();
        homeResultsList = homeResultsDao.getHomeResultsList(versionId, "A");
        return homeResultsList;
    }

    public void setHomeResultsList(List resultsList)
    {
        homeResultsList = resultsList;
    }

    private int gamesWon;
    private int gamesDrawn;
    private int gamesLost;
    protected List homeResultsList;
}
