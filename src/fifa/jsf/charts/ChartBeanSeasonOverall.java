package fifa.jsf.charts;

import fifa.jsf.OverallSeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanSeasonOverall
        extends ChartBeanOverall {
    private static final long serialVersionUID = 1L;
    private PieChartModel pieSeasonOverallModel;

    public PieChartModel getPieModel() {
        return this.pieSeasonOverallModel;
    }


    protected void createPieModel() {
        this.pieSeasonOverallModel = new PieChartModel();
        this.pieSeasonOverallModel.setTitle("Overall Season Results");
        this.pieSeasonOverallModel.setShowDataLabels(true);
        this.pieSeasonOverallModel.setLegendPosition("w");
        this.pieSeasonOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieSeasonOverallModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        OverallSeasonResultsBean overallSeasonResultsBean = new OverallSeasonResultsBean();

        List<Integer> overallResults = overallSeasonResultsBean.getOverallResultsList(versionId);

        if (overallResults != null) {
            this.pieSeasonOverallModel.set("Won", overallResults.get(0));
            this.pieSeasonOverallModel.set("Drawn", overallResults.get(1));
            this.pieSeasonOverallModel.set("Lost", overallResults.get(2));
        }
    }
}
