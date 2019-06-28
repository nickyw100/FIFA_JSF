package fifa.edit;

import fifa.dao.StatsDao;
import fifa.jsf.StatsBean;
import fifa.jsf.VersionBean;
import fifa.utilities.FIFAConstants;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@ManagedBean
@ViewScoped
public class StatsBeanEdit
        implements Serializable, FIFAConstants {
    private static final long serialVersionUID = 1L;
    private List<StatsBean> list;
    private DataModel<StatsBean> model;
    private StatsBean statsBean = new StatsBean();
    private boolean edit;
    private Integer restrictRows;
    private VersionBean versionBean = new VersionBean();

    @PostConstruct
    public void init() {
        int restrictRows = 0;
        restrictRows = getStatsRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));

        StatsDao statsDao = new StatsDao();
        this.list = statsDao.getStatsEdit();

        Date gameDateTime = Calendar.getInstance().getTime();
        this.statsBean.setGameDateTime(gameDateTime);
        this.statsBean.setVersionId(this.versionBean.getVersionIdNotAll());

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        this.statsBean.setLastPlayerNameAdded((String) session.getAttribute("lastPlayerName"));
    }


    private int getStatsRestrictRows(int restrictRows) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);


        if (session.getAttribute("statsRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("statsRestrictRows")).intValue();
        }
        return restrictRows;
    }

    public String add() {
        StatsDao statsDao = new StatsDao();
        statsDao.addStat(this.statsBean);
        refreshPage();
        return null;
    }


    public String editStat() {
        this.statsBean = this.model.getRowData();
        setEdit(true);
        return null;
    }

    public String save() {
        StatsDao statsDao = new StatsDao();
        statsDao.updateStat(this.statsBean);
        refreshPage();
        return null;
    }

    public String cancel() {
        resetPlaceHolder();
        refreshPage();
        return null;
    }

    public String delete() {
        StatsDao statsDao = new StatsDao();
        this.statsBean = this.model.getRowData();
        statsDao.deleteStat(this.statsBean);
        this.list.remove(this.model.getRowData());

        resetPlaceHolder();
        return null;
    }

    private void resetPlaceHolder() {
        this.statsBean = new StatsBean();
        setEdit(false);
    }


    public List<StatsBean> getList() {
        return this.list;
    }


    public DataModel<StatsBean> getModel() {
        if (this.model == null) {
            this.model = new ListDataModel(this.list);
        }

        return this.model;
    }


    public StatsBean getStatsBean() {
        return this.statsBean;
    }


    public boolean isEdit() {
        return this.edit;
    }


    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    public Integer getRestrictRows() {
        return this.restrictRows;
    }


    public void setRestrictRows(Integer restrictRows) {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("statsRestrictRows", restrictRows);
    }

    public String refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> messages = fc.getMessages();
        if (!messages.hasNext()) {

            String url = "editStats_template.jsf";
            ExternalContext ec = fc.getExternalContext();
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }
        return null;
    }
}
