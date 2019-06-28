// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanCupAway.java

package fifa.jsf.charts;

import fifa.jsf.AwayCupResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanAway

public class ChartBeanCupAway extends ChartBeanAway
{

    public ChartBeanCupAway()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieCupAwayModel;
    }

    protected void createPieModel()
    {
        pieCupAwayModel = new PieChartModel();
        pieCupAwayModel.setTitle("Away Cup Results");
        pieCupAwayModel.setShowDataLabels(true);
        pieCupAwayModel.setLegendPosition("w");
        pieCupAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieCupAwayModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        AwayCupResultsBean awayCupResultsBean = new AwayCupResultsBean();
        List awayCupResults = awayCupResultsBean.getAwayResultsList(versionId);
        if(awayCupResults != null)
        {
            pieCupAwayModel.set("Won", (Number)awayCupResults.get(0));
            pieCupAwayModel.set("Drawn", (Number)awayCupResults.get(1));
            pieCupAwayModel.set("Lost", (Number)awayCupResults.get(2));
        }
    }

    private PieChartModel pieCupAwayModel;
}
