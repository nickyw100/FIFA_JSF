package fifa.jsf;

import fifa.dao.OverallResultsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class OverallFriendlyResultsBean
        extends OverallResultsBean {
    public List<Integer> getOverallResultsList(String versionId) {
        OverallResultsDao overallResultsDao = new OverallResultsDao();
        this.overallResultsList = overallResultsDao.getOverallResultsList(versionId, "F");
        return this.overallResultsList;
    }
}
