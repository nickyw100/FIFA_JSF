// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchCriteriaBean.java

package fifa.jsf;

import javax.faces.model.SelectItem;
import java.io.Serializable;

// Referenced classes of package fifa.jsf:
//            StatsBean

public class SearchCriteriaBean
    implements Serializable
{

    public SearchCriteriaBean()
    {
    }

    public SelectItem[] getSearchCriteriaValues()
    {
        SelectItem items[] = new SelectItem[StatsBean.SearchCriteriaEnum.values().length];
        int i = 0;
        StatsBean.SearchCriteriaEnum asearchcriteriaenum[];
        int k = (asearchcriteriaenum = StatsBean.SearchCriteriaEnum.values()).length;
        for(int j = 0; j < k; j++)
        {
            StatsBean.SearchCriteriaEnum sce = asearchcriteriaenum[j];
            items[i++] = new SelectItem(sce, sce.name());
        }

        return items;
    }

    private static final long serialVersionUID = 0x31a42bd8L;
}
