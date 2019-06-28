// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanOverall.java

package fifa.jsf.charts;

import fifa.jsf.OverallResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.io.Serializable;
import java.util.List;

public class ChartBeanOverall
    implements Serializable, FIFAConstants
{

    public ChartBeanOverall()
    {
        createPieModel();
    }

    public PieChartModel getPieModel()
    {
        return pieOverallModel;
    }

    protected void createPieModel()
    {
        pieOverallModel = new PieChartModel();
        pieOverallModel.setTitle("Overall Results");
        pieOverallModel.setShowDataLabels(true);
        pieOverallModel.setLegendPosition("w");
        pieOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieOverallModel.setDataFormat("value");
        OverallResultsBean overallResultsBean = new OverallResultsBean();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        List overallResults = overallResultsBean.getOverallResultsList(versionId);
        if(overallResults != null)
        {
            pieOverallModel.set("Won", (Number)overallResults.get(0));
            pieOverallModel.set("Drawn", (Number)overallResults.get(1));
            pieOverallModel.set("Lost", (Number)overallResults.get(2));
        }
    }

    private static final long serialVersionUID = 1L;
    private PieChartModel pieOverallModel;
}
