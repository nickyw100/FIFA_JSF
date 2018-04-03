// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OverallResultsBean.java

package fifa.jsf;

import fifa.dao.OverallResultsDao;
import fifa.utilities.FIFAConstants;
import java.util.ArrayList;
import java.util.List;

public class OverallResultsBean
    implements FIFAConstants
{

    public OverallResultsBean()
    {
        overallResultsList = new ArrayList();
    }

    public Integer getGamesWon()
    {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon)
    {
        this.gamesWon = gamesWon;
    }

    public Integer getGamesDrawn()
    {
        return gamesDrawn;
    }

    public void setGamesDrawn(Integer gamesDrawn)
    {
        this.gamesDrawn = gamesDrawn;
    }

    public Integer getGamesLost()
    {
        return gamesLost;
    }

    public void setGamesLost(Integer gamesLost)
    {
        this.gamesLost = gamesLost;
    }

    public List getOverallResultsList(String versionId)
    {
        OverallResultsDao overallResultsDao = new OverallResultsDao();
        overallResultsList = overallResultsDao.getOverallResultsList(versionId, "A");
        return overallResultsList;
    }

    public void setOverallResultsList(List resultsList)
    {
        overallResultsList = resultsList;
    }

    private Integer gamesWon;
    private Integer gamesDrawn;
    private Integer gamesLost;
    protected List overallResultsList;
}
