// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanFriendlyOverall.java

package fifa.jsf.charts;

import fifa.jsf.OverallFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanOverall

public class ChartBeanFriendlyOverall extends ChartBeanOverall
    implements FIFAConstants
{

    public ChartBeanFriendlyOverall()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieFriendlyOverallModel;
    }

    protected void createPieModel()
    {
        pieFriendlyOverallModel = new PieChartModel();
        pieFriendlyOverallModel.setTitle("Away Overall Results");
        pieFriendlyOverallModel.setShowDataLabels(true);
        pieFriendlyOverallModel.setLegendPosition("w");
        pieFriendlyOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieFriendlyOverallModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        OverallFriendlyResultsBean overallFriendlyResultsBean = new OverallFriendlyResultsBean();
        List overallResults = overallFriendlyResultsBean.getOverallResultsList(versionId);
        if(overallResults != null)
        {
            pieFriendlyOverallModel.set("Won", (Number)overallResults.get(0));
            pieFriendlyOverallModel.set("Drawn", (Number)overallResults.get(1));
            pieFriendlyOverallModel.set("Lost", (Number)overallResults.get(2));
        }
    }

    private static final long serialVersionUID = 1L;
    private PieChartModel pieFriendlyOverallModel;
}
