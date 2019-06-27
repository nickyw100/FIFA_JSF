package fifa.jsf;

import fifa.dao.StatsDao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

@ManagedBean
public class LastResultBean
        extends SearchResultsBean
{
    private static final long serialVersionUID = 6199312392098189907L;
    private static Logger logger = Logger.getLogger(LastResultBean.class);

    private List<LastResultBean> lastResult;


    public List<LastResultBean> getLastResult() {
        logger.debug("Entering fifa.jsf.LastResultBean.getLastResult()");

        StatsDao statsDao = new StatsDao();

        if (this.lastResult == null) {
            this.lastResult = statsDao.getLastResult();
        }

        return this.lastResult;
    }


    public void setLastResult(List<LastResultBean> results) { this.lastResult = results; }
}
