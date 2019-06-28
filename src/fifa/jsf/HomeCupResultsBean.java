package fifa.jsf;

import fifa.dao.HomeResultsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class HomeCupResultsBean
        extends HomeResultsBean {
    public List<Integer> getHomeResultsList(String versionId) {
        HomeResultsDao homeResultsDao = new HomeResultsDao();
        this.homeResultsList = homeResultsDao.getHomeResultsList(versionId, "C");
        return this.homeResultsList;
    }
}
