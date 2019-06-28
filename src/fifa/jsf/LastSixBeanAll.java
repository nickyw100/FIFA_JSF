package fifa.jsf;

import fifa.dao.StatsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class LastSixBeanAll
        extends LastSixBean {
    private static final long serialVersionUID = 2415928512748859332L;
    private List<LastSixBean> results;

    public List<LastSixBean> getResults() {
        if (this.results == null) {
            StatsDao statsDao = new StatsDao();

            this.results = statsDao.getLastSix("A");
        }

        return this.results;
    }
}
