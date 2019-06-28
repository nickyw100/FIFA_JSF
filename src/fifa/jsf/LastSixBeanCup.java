// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastSixBeanCup.java

package fifa.jsf;

import fifa.dao.StatsDao;

import java.util.List;

// Referenced classes of package fifa.jsf:
//            LastSixBean

public class LastSixBeanCup extends LastSixBean
{

    public LastSixBeanCup()
    {
    }

    public List getResults()
    {
        if(results == null)
        {
            StatsDao statsDao = new StatsDao();
            results = statsDao.getLastSix("C");
        }
        return results;
    }

    private static final long serialVersionUID = 0x53b1a105L;
    private List results;
}
