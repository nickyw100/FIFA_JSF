package fifa.jsf;

import fifa.dao.StatsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class LastSixBeanFriendly
        extends LastSixBean {
    private static final long serialVersionUID = -4972918486581715193L;
    private List<LastSixBean> results;

    public List<LastSixBean> getResults() {
        if (this.results == null) {
            StatsDao statsDao = new StatsDao();

            this.results = statsDao.getLastSix("F");
        }

        return this.results;
    }
}
