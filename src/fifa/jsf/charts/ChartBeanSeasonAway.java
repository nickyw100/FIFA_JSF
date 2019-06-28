// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanSeasonAway.java

package fifa.jsf.charts;

import fifa.jsf.AwaySeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanAway

public class ChartBeanSeasonAway extends ChartBeanAway
{

    public ChartBeanSeasonAway()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieSeasonAwayModel;
    }

    protected void createPieModel()
    {
        pieSeasonAwayModel = new PieChartModel();
        pieSeasonAwayModel.setTitle("Away Season Results");
        pieSeasonAwayModel.setShowDataLabels(true);
        pieSeasonAwayModel.setLegendPosition("w");
        pieSeasonAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieSeasonAwayModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        AwaySeasonResultsBean awaySeasonResultsBean = new AwaySeasonResultsBean();
        List awaySeasonResults = awaySeasonResultsBean.getAwayResultsList(versionId);
        if(awaySeasonResults != null)
        {
            pieSeasonAwayModel.set("Won", (Number)awaySeasonResults.get(0));
            pieSeasonAwayModel.set("Drawn", (Number)awaySeasonResults.get(1));
            pieSeasonAwayModel.set("Lost", (Number)awaySeasonResults.get(2));
        }
    }

    private PieChartModel pieSeasonAwayModel;
}
