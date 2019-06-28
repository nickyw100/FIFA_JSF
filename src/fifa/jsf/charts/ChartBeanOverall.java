package fifa.jsf.charts;

import fifa.jsf.OverallResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;


@ManagedBean
public class ChartBeanOverall
        implements Serializable, FIFAConstants {
    private static final long serialVersionUID = 1L;
    private PieChartModel pieOverallModel;

    public ChartBeanOverall() {
        createPieModel();
    }


    public PieChartModel getPieModel() {
        return this.pieOverallModel;
    }


    protected void createPieModel() {
        this.pieOverallModel = new PieChartModel();
        this.pieOverallModel.setTitle("Overall Results");
        this.pieOverallModel.setShowDataLabels(true);
        this.pieOverallModel.setLegendPosition("w");
        this.pieOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieOverallModel.setDataFormat("value");

        OverallResultsBean overallResultsBean = new OverallResultsBean();

        PropertiesUtilities propertiesUtilities =
                PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        List<Integer> overallResults = overallResultsBean.getOverallResultsList(versionId);

        if (overallResults != null) {
            this.pieOverallModel.set("Won", (Number) overallResults.get(0));
            this.pieOverallModel.set("Drawn", (Number) overallResults.get(1));
            this.pieOverallModel.set("Lost", (Number) overallResults.get(2));
        }
    }
}
