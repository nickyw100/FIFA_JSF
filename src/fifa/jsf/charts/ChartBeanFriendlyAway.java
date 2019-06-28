// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartBeanFriendlyAway.java

package fifa.jsf.charts;

import fifa.jsf.AwayFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import java.util.List;

// Referenced classes of package fifa.jsf.charts:
//            ChartBeanAway

public class ChartBeanFriendlyAway extends ChartBeanAway
    implements FIFAConstants
{

    public ChartBeanFriendlyAway()
    {
    }

    public PieChartModel getPieModel()
    {
        return pieFriendlyAwayModel;
    }

    protected void createPieModel()
    {
        pieFriendlyAwayModel = new PieChartModel();
        pieFriendlyAwayModel.setTitle("Away Friendly Results");
        pieFriendlyAwayModel.setShowDataLabels(true);
        pieFriendlyAwayModel.setLegendPosition("w");
        pieFriendlyAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        pieFriendlyAwayModel.setDataFormat("value");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        AwayFriendlyResultsBean awayFriendlyResultsBean = new AwayFriendlyResultsBean();
        List awayFriendlyResults = awayFriendlyResultsBean.getAwayResultsList(versionId);
        if(awayFriendlyResults != null)
        {
            pieFriendlyAwayModel.set("Won", (Number)awayFriendlyResults.get(0));
            pieFriendlyAwayModel.set("Drawn", (Number)awayFriendlyResults.get(1));
            pieFriendlyAwayModel.set("Lost", (Number)awayFriendlyResults.get(2));
        }
    }

    private PieChartModel pieFriendlyAwayModel;
}
