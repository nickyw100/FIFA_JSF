// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanCupHome.java

package fifa.jsf.charts;

import fifa.jsf.HomeCupResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanHome

public class ChartBeanCupHome extends ChartBeanHome
{

    public ChartBeanCupHome()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieCupHomeModel;
    }

    protected void createPieModel()
    {
        pieCupHomeModel = new PieChartModel();
        pieCupHomeModel.setTitle("Home Cup Results");
        pieCupHomeModel.setShowDataLabels(true);
        pieCupHomeModel.setLegendPosition("w");
        pieCupHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieCupHomeModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        HomeCupResultsBean homeCupResultsBean = new HomeCupResultsBean();
        List homeCupResults = homeCupResultsBean.getHomeResultsList(versionId);
        if(homeCupResults != null)
        {
            pieCupHomeModel.set("Won", (Number)homeCupResults.get(0));
            pieCupHomeModel.set("Drawn", (Number)homeCupResults.get(1));
            pieCupHomeModel.set("Lost", (Number)homeCupResults.get(2));
        }
    }

    private PieChartModel pieCupHomeModel;
}
