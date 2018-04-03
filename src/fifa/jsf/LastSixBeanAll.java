// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastSixBeanAll.java

package fifa.jsf;

import fifa.dao.StatsDao;
import java.util.List;

// Referenced classes of package fifa.jsf:
//            LastSixBean

public class LastSixBeanAll extends LastSixBean
{

    public LastSixBeanAll()
    {
    }

    public List getResults()
    {
        if(results == null)
        {
            StatsDao statsDao = new StatsDao();
            results = statsDao.getLastSix("A");
        }
        return results;
    }

    private static final long serialVersionUID = 0xa5222fc4L;
    private List results;
}
