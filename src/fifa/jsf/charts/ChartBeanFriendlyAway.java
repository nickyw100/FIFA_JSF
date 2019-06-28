package fifa.jsf.charts;

import fifa.jsf.AwayFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanFriendlyAway
        extends ChartBeanAway
        implements FIFAConstants {
    private PieChartModel pieFriendlyAwayModel;

    public PieChartModel getPieModel() {
        return this.pieFriendlyAwayModel;
    }


    protected void createPieModel() {
        this.pieFriendlyAwayModel = new PieChartModel();
        this.pieFriendlyAwayModel.setTitle("Away Friendly Results");
        this.pieFriendlyAwayModel.setShowDataLabels(true);
        this.pieFriendlyAwayModel.setLegendPosition("w");
        this.pieFriendlyAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieFriendlyAwayModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        AwayFriendlyResultsBean awayFriendlyResultsBean = new AwayFriendlyResultsBean();

        List<Integer> awayFriendlyResults = awayFriendlyResultsBean.getAwayResultsList(versionId);

        if (awayFriendlyResults != null) {
            this.pieFriendlyAwayModel.set("Won", (Number) awayFriendlyResults.get(0));
            this.pieFriendlyAwayModel.set("Drawn", (Number) awayFriendlyResults.get(1));
            this.pieFriendlyAwayModel.set("Lost", (Number) awayFriendlyResults.get(2));
        }
    }
}
