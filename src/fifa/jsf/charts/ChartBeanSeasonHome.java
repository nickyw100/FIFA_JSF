// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanSeasonHome.java

package fifa.jsf.charts;

import fifa.jsf.HomeSeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanHome

public class ChartBeanSeasonHome extends ChartBeanHome
{

    public ChartBeanSeasonHome()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieSeasonHomeModel;
    }

    protected void createPieModel()
    {
        pieSeasonHomeModel = new PieChartModel();
        pieSeasonHomeModel.setTitle("Home Season Results");
        pieSeasonHomeModel.setShowDataLabels(true);
        pieSeasonHomeModel.setLegendPosition("w");
        pieSeasonHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieSeasonHomeModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        HomeSeasonResultsBean homeSeasonResultsBean = new HomeSeasonResultsBean();
        List homeSeasonResults = homeSeasonResultsBean.getHomeResultsList(versionId);
        if(homeSeasonResults != null)
        {
            pieSeasonHomeModel.set("Won", (Number)homeSeasonResults.get(0));
            pieSeasonHomeModel.set("Drawn", (Number)homeSeasonResults.get(1));
            pieSeasonHomeModel.set("Lost", (Number)homeSeasonResults.get(2));
        }
    }

    private PieChartModel pieSeasonHomeModel;
}
