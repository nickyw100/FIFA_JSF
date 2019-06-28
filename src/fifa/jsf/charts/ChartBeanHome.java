// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanHome.java

package fifa.jsf.charts;

import fifa.jsf.HomeResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

public class ChartBeanHome
    implements FIFAConstants
{

    public ChartBeanHome()
    {
        createPieModel();
    }

    public PieChartModel getPieModel()
    {
        return pieHomeModel;
    }

    protected void createPieModel()
    {
        pieHomeModel = new PieChartModel();
        pieHomeModel.setTitle("Home Results");
        pieHomeModel.setShowDataLabels(true);
        pieHomeModel.setLegendPosition("w");
        pieHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieHomeModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        HomeResultsBean homeResultsBean = new HomeResultsBean();
        List homeResults = homeResultsBean.getHomeResultsList(versionId);
        if(homeResults != null)
        {
            pieHomeModel.set("Won", (Number)homeResults.get(0));
            pieHomeModel.set("Drawn", (Number)homeResults.get(1));
            pieHomeModel.set("Lost", (Number)homeResults.get(2));
        }
    }

    private PieChartModel pieHomeModel;
}
