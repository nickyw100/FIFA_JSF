// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TeamBeanEdit.java

package fifa.edit;

import fifa.dao.TeamDao;
import fifa.jsf.TeamBean;
import fifa.utilities.FIFAConstants;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class TeamBeanEdit
    implements Serializable, FIFAConstants
{

    public TeamBeanEdit()
    {
        teamBean = new TeamBean();
    }

    public void init()
    {
        int restrictRows = 0;
        restrictRows = getTeamRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        TeamDao teamDao = new TeamDao();
        teamList = teamDao.getTeamsEdit();
    }

    private int getTeamRestrictRows(int restrictRows)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        if(session.getAttribute("teamsRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("teamsRestrictRows")).intValue();
        return restrictRows;
    }

    public String add()
    {
        TeamDao teamDao = new TeamDao();
        teamDao.addTeam(teamBean.getTeamId(), teamBean.getCountryId(), teamBean.getTeamName(), teamBean.getTeamComments(), teamBean.getLogoImage());
        refreshPage();
        return null;
    }

    public String editTeam()
    {
        teamBean = (TeamBean)model.getRowData();
        setEdit(true);
        return null;
    }

    public String save()
    {
        TeamDao teamDao = new TeamDao();
        teamDao.updateTeam(teamBean.getTeamId(), teamBean.getCountryId(), teamBean.getTeamName(), teamBean.getTeamComments(), teamBean.getLogoImage());
        teamBean = new TeamBean();
        setEdit(false);
        refreshPage();
        return null;
    }

    public String cancel()
    {
        resetPlaceHolder();
        refreshPage();
        return null;
    }

    public String delete()
    {
        TeamDao teamDao = new TeamDao();
        teamBean = (TeamBean)model.getRowData();
        teamDao.deleteTeam(teamBean.getTeamId(), teamBean.getCountryId());
        teamBean = new TeamBean();
        setEdit(false);
        refreshPage();
        return null;
    }

    public List getTeamList()
    {
        return teamList;
    }

    public DataModel getModel()
    {
        if(model == null)
            model = new ListDataModel(teamList);
        return model;
    }

    public TeamBean getTeamBean()
    {
        return teamBean;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
    }

    public List getFilteredTeams()
    {
        return filteredTeams;
    }

    public void setFilteredTeams(List filteredTeams)
    {
        this.filteredTeams = filteredTeams;
    }

    private void resetPlaceHolder()
    {
        teamBean = new TeamBean();
        setEdit(false);
    }

    public Integer getRestrictRows()
    {
        return restrictRows;
    }

    public void setRestrictRows(Integer restrictRows)
    {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("teamsRestrictRows", restrictRows);
    }

    public String refreshPage()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator messages = fc.getMessages();
        if(!messages.hasNext())
        {
            String url = "editTeam_template.jsf";
            ExternalContext ec = fc.getExternalContext();
            try
            {
                ec.redirect(url);
            }
            catch(IOException ex)
            {
                System.err.println(ex.getLocalizedMessage());
            }
        }
        return null;
    }

    private static final long serialVersionUID = 1L;
    private transient List teamList;
    private transient DataModel model;
    private TeamBean teamBean;
    private boolean edit;
    private Integer restrictRows;
    private transient List filteredTeams;
}
