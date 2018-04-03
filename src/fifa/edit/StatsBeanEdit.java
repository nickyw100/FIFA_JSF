// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StatsBeanEdit.java

package fifa.edit;

import fifa.dao.StatsDao;
import fifa.jsf.StatsBean;
import fifa.jsf.VersionBean;
import fifa.utilities.FIFAConstants;
import java.io.*;
import java.util.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

public class StatsBeanEdit
    implements Serializable, FIFAConstants
{

    public StatsBeanEdit()
    {
        statsBean = new StatsBean();
        versionBean = new VersionBean();
    }

    public void init()
    {
        int restrictRows = 0;
        restrictRows = getStatsRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        StatsDao statsDao = new StatsDao();
        list = statsDao.getStatsEdit();
        java.util.Date gameDateTime = Calendar.getInstance().getTime();
        statsBean.setGameDateTime(gameDateTime);
        statsBean.setVersionId(versionBean.getVersionIdNotAll());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        statsBean.setLastPlayerNameAdded((String)session.getAttribute("lastPlayerName"));
    }

    private int getStatsRestrictRows(int restrictRows)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        if(session.getAttribute("statsRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("statsRestrictRows")).intValue();
        return restrictRows;
    }

    public String add()
    {
        StatsDao statsDao = new StatsDao();
        statsDao.addStat(statsBean);
        refreshPage();
        return null;
    }

    public String editStat()
    {
        statsBean = (StatsBean)model.getRowData();
        setEdit(true);
        return null;
    }

    public String save()
    {
        StatsDao statsDao = new StatsDao();
        statsDao.updateStat(statsBean);
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
        StatsDao statsDao = new StatsDao();
        statsBean = (StatsBean)model.getRowData();
        statsDao.deleteStat(statsBean);
        list.remove(model.getRowData());
        resetPlaceHolder();
        return null;
    }

    private void resetPlaceHolder()
    {
        statsBean = new StatsBean();
        setEdit(false);
    }

    public List getList()
    {
        return list;
    }

    public DataModel getModel()
    {
        if(model == null)
            model = new ListDataModel(list);
        return model;
    }

    public StatsBean getStatsBean()
    {
        return statsBean;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
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
        session.setAttribute("statsRestrictRows", restrictRows);
    }

    public String refreshPage()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator messages = fc.getMessages();
        if(!messages.hasNext())
        {
            String url = "editStats_template.jsf";
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
    private List list;
    private transient DataModel model;
    private StatsBean statsBean;
    private boolean edit;
    private Integer restrictRows;
    private VersionBean versionBean;
}
