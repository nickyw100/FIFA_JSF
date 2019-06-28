package fifa.jsf.charts;

import fifa.jsf.HomeCupResultsBean;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanCupHome
        extends ChartBeanHome {
    private PieChartModel pieCupHomeModel;

    public PieChartModel getPieModel() {
        return this.pieCupHomeModel;
    }


    protected void createPieModel() {
        this.pieCupHomeModel = new PieChartModel();
        this.pieCupHomeModel.setTitle("Home Cup Results");
        this.pieCupHomeModel.setShowDataLabels(true);
        this.pieCupHomeModel.setLegendPosition("w");
        this.pieCupHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieCupHomeModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        HomeCupResultsBean homeCupResultsBean = new HomeCupResultsBean();

        List<Integer> homeCupResults = homeCupResultsBean.getHomeResultsList(versionId);

        if (homeCupResults != null) {
            this.pieCupHomeModel.set("Won", (Number) homeCupResults.get(0));
            this.pieCupHomeModel.set("Drawn", (Number) homeCupResults.get(1));
            this.pieCupHomeModel.set("Lost", (Number) homeCupResults.get(2));
        }
    }
}
