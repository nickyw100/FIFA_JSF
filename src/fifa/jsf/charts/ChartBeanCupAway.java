package fifa.jsf.charts;

import fifa.jsf.AwayCupResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanCupAway
        extends ChartBeanAway {
    private PieChartModel pieCupAwayModel;

    public PieChartModel getPieModel() {
        return this.pieCupAwayModel;
    }


    protected void createPieModel() {
        this.pieCupAwayModel = new PieChartModel();
        this.pieCupAwayModel.setTitle("Away Cup Results");
        this.pieCupAwayModel.setShowDataLabels(true);
        this.pieCupAwayModel.setLegendPosition("w");
        this.pieCupAwayModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieCupAwayModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        AwayCupResultsBean awayCupResultsBean = new AwayCupResultsBean();

        List<Integer> awayCupResults = awayCupResultsBean.getAwayResultsList(versionId);

        if (awayCupResults != null) {
            this.pieCupAwayModel.set("Won", awayCupResults.get(0));
            this.pieCupAwayModel.set("Drawn", awayCupResults.get(1));
            this.pieCupAwayModel.set("Lost", awayCupResults.get(2));
        }
    }
}
