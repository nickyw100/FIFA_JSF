// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanCupOverall.java

package fifa.jsf.charts;

import fifa.jsf.OverallCupResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanOverall

public class ChartBeanCupOverall extends ChartBeanOverall
    implements FIFAConstants
{

    public ChartBeanCupOverall()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieCupOverallModel;
    }

    protected void createPieModel()
    {
        pieCupOverallModel = new PieChartModel();
        pieCupOverallModel.setTitle("Overall Cup Results");
        pieCupOverallModel.setShowDataLabels(true);
        pieCupOverallModel.setLegendPosition("w");
        pieCupOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieCupOverallModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        OverallCupResultsBean overallCupResultsBean = new OverallCupResultsBean();
        List overallResults = overallCupResultsBean.getOverallResultsList(versionId);
        if(overallResults != null)
        {
            pieCupOverallModel.set("Won", (Number)overallResults.get(0));
            pieCupOverallModel.set("Drawn", (Number)overallResults.get(1));
            pieCupOverallModel.set("Lost", (Number)overallResults.get(2));
        }
    }

    private static final long serialVersionUID = 1L;
    private PieChartModel pieCupOverallModel;
}
