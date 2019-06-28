package fifa.jsf;

import fifa.dao.StatsDao;
import java.util.List;
import javax.faces.bean.ManagedBean;



@ManagedBean
public class LastSixBeanCup
        extends LastSixBean
{
    private static final long serialVersionUID = -4593348336325385979L;
    private List<LastSixBean> results;

    public List<LastSixBean> getResults() {
        if (this.results == null) {
            StatsDao statsDao = new StatsDao();

            this.results = statsDao.getLastSix("C");
        }

        return this.results;
    }
}
