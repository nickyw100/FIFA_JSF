// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AwayResultsBean.java

package fifa.jsf;

import fifa.dao.AwayResultsDao;
import fifa.utilities.FIFAConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AwayResultsBean
    implements FIFAConstants
{

    public AwayResultsBean()
    {
        awayResultsList = new ArrayList();
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
        return awayResultsList;
    }

    public void setResultsList(List resultsList)
    {
        awayResultsList = Arrays.asList(new Integer[] {
            Integer.valueOf(getGamesWon()), Integer.valueOf(getGamesDrawn()), Integer.valueOf(getGamesLost())
        });
    }

    public List getAwayResultsList(String versionId)
    {
        AwayResultsDao awayResultsDao = new AwayResultsDao();
        awayResultsList = awayResultsDao.getAwayResultsList(versionId, "A");
        return awayResultsList;
    }

    public void setAwayResultsList(List resultsList)
    {
        awayResultsList = resultsList;
    }

    private int gamesWon;
    private int gamesDrawn;
    private int gamesLost;
    protected List awayResultsList;
}
