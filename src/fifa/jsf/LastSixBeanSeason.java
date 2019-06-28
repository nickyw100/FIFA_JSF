package fifa.jsf;

import fifa.dao.StatsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class LastSixBeanSeason
        extends LastSixBean {
    private static final long serialVersionUID = 3416340403948758542L;
    private List<LastSixBean> results;

    public List<LastSixBean> getResults() {
        if (this.results == null) {
            StatsDao statsDao = new StatsDao();
            this.results = statsDao.getLastSix("S");
        }

        return this.results;
    }
}
