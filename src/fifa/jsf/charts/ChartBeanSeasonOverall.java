// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanSeasonOverall.java

package fifa.jsf.charts;

import fifa.jsf.OverallSeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanOverall

public class ChartBeanSeasonOverall extends ChartBeanOverall
{

    public ChartBeanSeasonOverall()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieSeasonOverallModel;
    }

    protected void createPieModel()
    {
        pieSeasonOverallModel = new PieChartModel();
        pieSeasonOverallModel.setTitle("Overall Season Results");
        pieSeasonOverallModel.setShowDataLabels(true);
        pieSeasonOverallModel.setLegendPosition("w");
        pieSeasonOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieSeasonOverallModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        OverallSeasonResultsBean overallSeasonResultsBean = new OverallSeasonResultsBean();
        List overallResults = overallSeasonResultsBean.getOverallResultsList(versionId);
        if(overallResults != null)
        {
            pieSeasonOverallModel.set("Won", (Number)overallResults.get(0));
            pieSeasonOverallModel.set("Drawn", (Number)overallResults.get(1));
            pieSeasonOverallModel.set("Lost", (Number)overallResults.get(2));
        }
    }

    private static final long serialVersionUID = 1L;
    private PieChartModel pieSeasonOverallModel;
}
