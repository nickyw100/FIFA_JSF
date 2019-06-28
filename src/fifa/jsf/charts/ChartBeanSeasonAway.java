package fifa.jsf.charts;

import fifa.jsf.AwaySeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanSeasonAway
        extends ChartBeanAway {
    private PieChartModel pieSeasonAwayModel;

    public PieChartModel getPieModel() {
        return this.pieSeasonAwayModel;
    }


    protected void createPieModel() {
        this.pieSeasonAwayModel = new PieChartModel();
        this.pieSeasonAwayModel.setTitle("Away Season Results");
        this.pieSeasonAwayModel.setShowDataLabels(true);
        this.pieSeasonAwayModel.setLegendPosition("w");
        this.pieSeasonAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieSeasonAwayModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        AwaySeasonResultsBean awaySeasonResultsBean = new AwaySeasonResultsBean();

        List<Integer> awaySeasonResults = awaySeasonResultsBean.getAwayResultsList(versionId);

        if (awaySeasonResults != null) {
            this.pieSeasonAwayModel.set("Won", (Number) awaySeasonResults.get(0));
            this.pieSeasonAwayModel.set("Drawn", (Number) awaySeasonResults.get(1));
            this.pieSeasonAwayModel.set("Lost", (Number) awaySeasonResults.get(2));
        }
    }
}
