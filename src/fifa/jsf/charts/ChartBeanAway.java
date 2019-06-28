package fifa.jsf.charts;

import fifa.jsf.AwayResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanAway
        implements FIFAConstants {
    private PieChartModel pieAwayModel;

    public ChartBeanAway() {
        createPieModel();
    }


    public PieChartModel getPieModel() {
        return this.pieAwayModel;
    }


    protected void createPieModel() {
        this.pieAwayModel = new PieChartModel();
        this.pieAwayModel.setTitle("Away Results");
        this.pieAwayModel.setShowDataLabels(true);
        this.pieAwayModel.setLegendPosition("w");
        this.pieAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieAwayModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities =
                PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        AwayResultsBean awayResultsBean = new AwayResultsBean();

        List<Integer> awayResults = awayResultsBean.getAwayResultsList(versionId);

        if (awayResults != null) {
            this.pieAwayModel.set("Won", (Number) awayResults.get(0));
            this.pieAwayModel.set("Drawn", (Number) awayResults.get(1));
            this.pieAwayModel.set("Lost", (Number) awayResults.get(2));
        }
    }
}
