// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerBeanEdit.java

package fifa.edit;

import fifa.dao.PlayerDao;
import fifa.jsf.PlayerBean;
import fifa.utilities.FIFAConstants;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

public class PlayerBeanEdit
    implements Serializable, FIFAConstants
{

    public PlayerBeanEdit()
    {
        playerBean = new PlayerBean();
    }

    public void init()
    {
        int restrictRows = 0;
        restrictRows = getPlayerRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        PlayerDao PlayerDao = new PlayerDao();
        playerList = PlayerDao.getPlayersEdit();
    }

    private int getPlayerRestrictRows(int restrictRows)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        if(session.getAttribute("playersRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("playersRestrictRows")).intValue();
        return restrictRows;
    }

    public String add()
    {
        playerBean.addPlayer();
        playerBean.setPlayers(null);
        refreshPage();
        return null;
    }

    public String refreshPage()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator messages = fc.getMessages();
        if(!messages.hasNext())
        {
            String url = "editPlayer_template.jsf";
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

    public String editPlayer()
    {
        playerBean = (PlayerBean)model.getRowData();
        setEdit(true);
        return null;
    }

    public String save()
    {
        PlayerDao PlayerDao = new PlayerDao();
        PlayerDao.updatePlayer(playerBean.getPlayerName(), playerBean.getPlayerComments());
        playerBean = new PlayerBean();
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
        PlayerDao PlayerDao = new PlayerDao();
        playerBean = (PlayerBean)model.getRowData();
        PlayerDao.deletePlayer(playerBean.getPlayerName());
        playerBean = new PlayerBean();
        setEdit(false);
        return refreshPage();
    }

    public List getPlayerList()
    {
        return playerList;
    }

    public DataModel getModel()
    {
        if(model == null)
            model = new ListDataModel(playerList);
        return model;
    }

    public PlayerBean getPlayerBean()
    {
        return playerBean;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
    }

    public List getFilteredPlayers()
    {
        return filteredPlayers;
    }

    public void setFilteredPlayers(List filteredPlayers)
    {
        this.filteredPlayers = filteredPlayers;
    }

    private void resetPlaceHolder()
    {
        playerBean = new PlayerBean();
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
        session.setAttribute("playersRestrictRows", restrictRows);
    }

    private static final long serialVersionUID = 1L;
    private List playerList;
    private transient DataModel model;
    private PlayerBean playerBean;
    private boolean edit;
    private Integer restrictRows;
    private List filteredPlayers;
}
