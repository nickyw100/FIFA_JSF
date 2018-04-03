// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeSeasonResultsBean.java

package fifa.jsf;

import fifa.dao.HomeResultsDao;
import java.util.List;

// Referenced classes of package fifa.jsf:
//            HomeResultsBean

public class HomeSeasonResultsBean extends HomeResultsBean
{

    public HomeSeasonResultsBean()
    {
    }

    public List getHomeResultsList(String versionId)
    {
        HomeResultsDao homeResultsDao = new HomeResultsDao();
        homeResultsList = homeResultsDao.getHomeResultsList(versionId, "S");
        return homeResultsList;
    }
}
