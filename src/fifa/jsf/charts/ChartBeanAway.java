// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanAway.java

package fifa.jsf.charts;

import fifa.jsf.AwayResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import java.util.List;
import org.primefaces.model.chart.PieChartModel;

public class ChartBeanAway
    implements FIFAConstants
{

    public ChartBeanAway()
    {
        createPieModel();
    }

    public PieChartModel getPieModel()
    {
        return pieAwayModel;
    }

    protected void createPieModel()
    {
        pieAwayModel = new PieChartModel();
        pieAwayModel.setTitle("Away Results");
        pieAwayModel.setShowDataLabels(true);
        pieAwayModel.setLegendPosition("w");
        pieAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieAwayModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        AwayResultsBean awayResultsBean = new AwayResultsBean();
        List awayResults = awayResultsBean.getAwayResultsList(versionId);
        if(awayResults != null)
        {
            pieAwayModel.set("Won", (Number)awayResults.get(0));
            pieAwayModel.set("Drawn", (Number)awayResults.get(1));
            pieAwayModel.set("Lost", (Number)awayResults.get(2));
        }
    }

    private PieChartModel pieAwayModel;
}
