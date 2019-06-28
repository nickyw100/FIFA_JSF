package fifa.jsf;

import fifa.dao.HomeResultsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class HomeFriendlyResultsBean
        extends HomeResultsBean {
    public List<Integer> getHomeResultsList(String versionId) {
        HomeResultsDao homeResultsDao = new HomeResultsDao();
        this.homeResultsList = homeResultsDao.getHomeResultsList(versionId, "F");
        return this.homeResultsList;
    }
}
