package fifa.jsf.charts;

import fifa.jsf.HomeFriendlyResultsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean
public class ChartBeanFriendlyHome
        extends ChartBeanHome
        implements FIFAConstants {
    private PieChartModel pieFriendlyHomeModel;

    public PieChartModel getPieModel() {
        return this.pieFriendlyHomeModel;
    }


    protected void createPieModel() {
        this.pieFriendlyHomeModel = new PieChartModel();
        this.pieFriendlyHomeModel.setTitle("Home Friendly Results");
        this.pieFriendlyHomeModel.setShowDataLabels(true);
        this.pieFriendlyHomeModel.setLegendPosition("w");
        this.pieFriendlyHomeModel.setSeriesColors("99FF99, E0E0E0, E34230");
        this.pieFriendlyHomeModel.setDataFormat("value");

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");

        HomeFriendlyResultsBean homeFriendlyResultsBean = new HomeFriendlyResultsBean();

        List<Integer> homeFriendlyResults = homeFriendlyResultsBean.getHomeResultsList(versionId);

        if (homeFriendlyResults != null) {
            this.pieFriendlyHomeModel.set("Won", (Number) homeFriendlyResults.get(0));
            this.pieFriendlyHomeModel.set("Drawn", (Number) homeFriendlyResults.get(1));
            this.pieFriendlyHomeModel.set("Lost", (Number) homeFriendlyResults.get(2));
        }
    }
}
