// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastResultBean.java

package fifa.jsf;

import fifa.dao.StatsDao;
import java.util.List;
import org.apache.log4j.Logger;

// Referenced classes of package fifa.jsf:
//            SearchResultsBean

public class LastResultBean extends SearchResultsBean
{

    public LastResultBean()
    {
    }

    public List getLastResult()
    {
        logger.debug("Entering fifa.jsf.LastResultBean.getLastResult()");
        StatsDao statsDao = new StatsDao();
        if(lastResult == null)
            lastResult = statsDao.getLastResult();
        return lastResult;
    }

    public void setLastResult(List results)
    {
        lastResult = results;
    }

    private static final long serialVersionUID = 0x76b09253L;
    private static Logger logger = Logger.getLogger(fifa/jsf/LastResultBean);
    private List lastResult;

}
