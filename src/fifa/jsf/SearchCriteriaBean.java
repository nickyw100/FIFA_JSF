package fifa.jsf;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;



@ManagedBean(name = "searchCriteriaBean")
@ViewScoped
public class SearchCriteriaBean
        implements Serializable
{
    private static final long serialVersionUID = 3415142838225349592L;

    public SelectItem[] getSearchCriteriaValues() {
        SelectItem items[] = new SelectItem[StatsBean.SearchCriteriaEnum.values().length];
        int i = 0;
        StatsBean.SearchCriteriaEnum asearchcriteriaenum[];
        int k = (asearchcriteriaenum = StatsBean.SearchCriteriaEnum.values()).length;
        for (int j = 0; j < k; j++) {
            StatsBean.SearchCriteriaEnum sce = asearchcriteriaenum[j];
            items[i++] = new SelectItem(sce, sce.name());
        }

        return items;
    }
}
