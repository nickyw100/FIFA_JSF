// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OverallSeasonResultsBean.java

package fifa.jsf;

import fifa.dao.OverallResultsDao;

import java.util.List;

// Referenced classes of package fifa.jsf:
//            OverallResultsBean

public class OverallSeasonResultsBean extends OverallResultsBean
{

    public OverallSeasonResultsBean()
    {
    }

    public List getOverallResultsList(String versionId)
    {
        OverallResultsDao overallResultsDao = new OverallResultsDao();
        overallResultsList = overallResultsDao.getOverallResultsList(versionId, "S");
        return overallResultsList;
    }
}
