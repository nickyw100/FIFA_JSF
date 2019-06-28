package fifa.jsf.charts;

import fifa.jsf.HomeSeasonResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanSeasonHome
        extends ChartBeanHome {
    private PieChartModel pieSeasonHomeModel;

    public PieChartModel getPieModel() {
        return this.pieSeasonHomeModel;
    }


    protected void createPieModel() {
        this.pieSeasonHomeModel = new PieChartModel();
        this.pieSeasonHomeModel.setTitle("Home Season Results");
        this.pieSeasonHomeModel.setShowDataLabels(true);
        this.pieSeasonHomeModel.setLegendPosition("w");
        this.pieSeasonHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieSeasonHomeModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        HomeSeasonResultsBean homeSeasonResultsBean = new HomeSeasonResultsBean();

        List<Integer> homeSeasonResults = homeSeasonResultsBean.getHomeResultsList(versionId);

        if (homeSeasonResults != null) {
            this.pieSeasonHomeModel.set("Won", (Number) homeSeasonResults.get(0));
            this.pieSeasonHomeModel.set("Drawn", (Number) homeSeasonResults.get(1));
            this.pieSeasonHomeModel.set("Lost", (Number) homeSeasonResults.get(2));
        }
    }
}
