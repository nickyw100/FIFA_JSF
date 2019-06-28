package fifa.jsf.charts;

import fifa.jsf.HomeResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanHome
        implements FIFAConstants {
    private PieChartModel pieHomeModel;

    public ChartBeanHome() {
        createPieModel();
    }


    public PieChartModel getPieModel() {
        return this.pieHomeModel;
    }


    protected void createPieModel() {
        this.pieHomeModel = new PieChartModel();
        this.pieHomeModel.setTitle("Home Results");
        this.pieHomeModel.setShowDataLabels(true);
        this.pieHomeModel.setLegendPosition("w");
        this.pieHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieHomeModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities =
                PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        HomeResultsBean homeResultsBean = new HomeResultsBean();

        List<Integer> homeResults = homeResultsBean.getHomeResultsList(versionId);

        if (homeResults != null) {
            this.pieHomeModel.set("Won", (Number) homeResults.get(0));
            this.pieHomeModel.set("Drawn", (Number) homeResults.get(1));
            this.pieHomeModel.set("Lost", (Number) homeResults.get(2));
        }
    }
}
