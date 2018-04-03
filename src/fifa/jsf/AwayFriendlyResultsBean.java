// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AwayFriendlyResultsBean.java

package fifa.jsf;

import fifa.dao.AwayResultsDao;
import java.util.List;

// Referenced classes of package fifa.jsf:
//            AwayResultsBean

public class AwayFriendlyResultsBean extends AwayResultsBean
{

    public AwayFriendlyResultsBean()
    {
    }

    public List getAwayResultsList(String versionId)
    {
        AwayResultsDao awayResultsDao = new AwayResultsDao();
        awayResultsList = awayResultsDao.getAwayResultsList(versionId, "F");
        return awayResultsList;
    }
}
