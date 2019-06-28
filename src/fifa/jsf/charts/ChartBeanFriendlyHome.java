// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanFriendlyHome.java

package fifa.jsf.charts;

import fifa.jsf.HomeFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanHome

public class ChartBeanFriendlyHome extends ChartBeanHome
    implements FIFAConstants
{

    public ChartBeanFriendlyHome()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieFriendlyHomeModel;
    }

    protected void createPieModel()
    {
        pieFriendlyHomeModel = new PieChartModel();
        pieFriendlyHomeModel.setTitle("Home Friendly Results");
        pieFriendlyHomeModel.setShowDataLabels(true);
        pieFriendlyHomeModel.setLegendPosition("w");
        pieFriendlyHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieFriendlyHomeModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        HomeFriendlyResultsBean homeFriendlyResultsBean = new HomeFriendlyResultsBean();
        List homeFriendlyResults = homeFriendlyResultsBean.getHomeResultsList(versionId);
        if(homeFriendlyResults != null)
        {
            pieFriendlyHomeModel.set("Won", (Number)homeFriendlyResults.get(0));
            pieFriendlyHomeModel.set("Drawn", (Number)homeFriendlyResults.get(1));
            pieFriendlyHomeModel.set("Lost", (Number)homeFriendlyResults.get(2));
        }
    }

    private PieChartModel pieFriendlyHomeModel;
}
