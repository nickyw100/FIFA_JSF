package fifa.jsf;

import fifa.dao.AwayResultsDao;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class AwayCupResultsBean
        extends AwayResultsBean {
    public List<Integer> getAwayResultsList(String versionId) {
        AwayResultsDao awayResultsDao = new AwayResultsDao();
        this.awayResultsList = awayResultsDao.getAwayResultsList(versionId, "C");
        return this.awayResultsList;
    }
}
