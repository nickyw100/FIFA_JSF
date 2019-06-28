package fifa.edit;

import fifa.dao.TeamDao;
import fifa.jsf.TeamBean;
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
import java.util.Iterator;
import java.util.List;


@ManagedBean
@ViewScoped
public class TeamBeanEdit
        implements Serializable, FIFAConstants {
    private static final long serialVersionUID = 1L;
    private List<TeamBean> teamList;
    private DataModel<TeamBean> model;
    private TeamBean teamBean = new TeamBean();

    private boolean edit;
    private Integer restrictRows;
    private List<TeamBean> filteredTeams;

    @PostConstruct
    public void init() {
        int restrictRows = 0;
        restrictRows = getTeamRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));

        TeamDao teamDao = new TeamDao();
        this.teamList = teamDao.getTeamsEdit();
    }


    private int getTeamRestrictRows(int restrictRows) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("teamsRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("teamsRestrictRows")).intValue();
        }
        return restrictRows;
    }


    public String add() {
        TeamDao teamDao = new TeamDao();
        teamDao.addTeam(this.teamBean.getTeamId(), this.teamBean.getCountryId(), this.teamBean.getTeamName(), this.teamBean.getTeamComments(),
                this.teamBean.getLogoImage());
        refreshPage();
        return null;
    }


    public String editTeam() {
        this.teamBean = this.model.getRowData();
        setEdit(true);
        return null;
    }

    public String save() {
        TeamDao teamDao = new TeamDao();
        teamDao.updateTeam(this.teamBean.getTeamId(), this.teamBean.getCountryId(), this.teamBean.getTeamName(), this.teamBean.getTeamComments(),
                this.teamBean.getLogoImage());
        this.teamBean = new TeamBean();
        setEdit(false);

        refreshPage();
        return null;
    }

    public String cancel() {
        resetPlaceHolder();
        refreshPage();
        return null;
    }

    public String delete() {
        TeamDao teamDao = new TeamDao();
        this.teamBean = this.model.getRowData();
        teamDao.deleteTeam(this.teamBean.getTeamId(), this.teamBean.getCountryId());
        this.teamBean = new TeamBean();
        setEdit(false);

        refreshPage();
        return null;
    }


    public List<TeamBean> getTeamList() {
        return this.teamList;
    }


    public DataModel<TeamBean> getModel() {
        if (this.model == null) {
            this.model = new ListDataModel(this.teamList);
        }

        return this.model;
    }


    public TeamBean getTeamBean() {
        return this.teamBean;
    }


    public boolean isEdit() {
        return this.edit;
    }


    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    public List<TeamBean> getFilteredTeams() {
        return this.filteredTeams;
    }


    public void setFilteredTeams(List<TeamBean> filteredTeams) {
        this.filteredTeams = filteredTeams;
    }


    private void resetPlaceHolder() {
        this.teamBean = new TeamBean();
        setEdit(false);
    }


    public Integer getRestrictRows() {
        return this.restrictRows;
    }


    public void setRestrictRows(Integer restrictRows) {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("teamsRestrictRows", restrictRows);
    }

    public String refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> messages = fc.getMessages();
        if (!messages.hasNext()) {

            String url = "editTeam_template.jsf";
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
