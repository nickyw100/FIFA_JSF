package fifa.jsf;

import fifa.dao.AwayResultsDao;
import java.util.List;
import javax.faces.bean.ManagedBean;



@ManagedBean
public class AwaySeasonResultsBean
        extends AwayResultsBean
{
    public List<Integer> getAwayResultsList(String versionId) {
        AwayResultsDao awayResultsDao = new AwayResultsDao();
        this.awayResultsList = awayResultsDao.getAwayResultsList(versionId, "S");
        return this.awayResultsList;
    }
}
