package fifa.jsf.charts;

import fifa.jsf.OverallFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanFriendlyOverall
        extends ChartBeanOverall
        implements FIFAConstants {
    private static final long serialVersionUID = 1L;
    private PieChartModel pieFriendlyOverallModel;

    public PieChartModel getPieModel() {
        return this.pieFriendlyOverallModel;
    }


    protected void createPieModel() {
        this.pieFriendlyOverallModel = new PieChartModel();
        this.pieFriendlyOverallModel.setTitle("Away Overall Results");
        this.pieFriendlyOverallModel.setShowDataLabels(true);
        this.pieFriendlyOverallModel.setLegendPosition("w");
        this.pieFriendlyOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieFriendlyOverallModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        OverallFriendlyResultsBean overallFriendlyResultsBean = new OverallFriendlyResultsBean();

        List<Integer> overallResults = overallFriendlyResultsBean.getOverallResultsList(versionId);

        if (overallResults != null) {
            this.pieFriendlyOverallModel.set("Won", (Number) overallResults.get(0));
            this.pieFriendlyOverallModel.set("Drawn", (Number) overallResults.get(1));
            this.pieFriendlyOverallModel.set("Lost", (Number) overallResults.get(2));
        }
    }
}
