package fifa.jsf.charts;

import fifa.jsf.OverallCupResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanCupOverall
        extends ChartBeanOverall
        implements FIFAConstants {
    private static final long serialVersionUID = 1L;
    private PieChartModel pieCupOverallModel;

    public PieChartModel getPieModel() {
        return this.pieCupOverallModel;
    }


    protected void createPieModel() {
        this.pieCupOverallModel = new PieChartModel();
        this.pieCupOverallModel.setTitle("Overall Cup Results");
        this.pieCupOverallModel.setShowDataLabels(true);
        this.pieCupOverallModel.setLegendPosition("w");
        this.pieCupOverallModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieCupOverallModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        OverallCupResultsBean overallCupResultsBean = new OverallCupResultsBean();

        List<Integer> overallResults = overallCupResultsBean.getOverallResultsList(versionId);

        if (overallResults != null) {
            this.pieCupOverallModel.set("Won", (Number) overallResults.get(0));
            this.pieCupOverallModel.set("Drawn", (Number) overallResults.get(1));
            this.pieCupOverallModel.set("Lost", (Number) overallResults.get(2));
        }
    }
}
