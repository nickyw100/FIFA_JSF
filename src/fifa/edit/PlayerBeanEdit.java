package fifa.edit;

import fifa.dao.PlayerDao;
import fifa.jsf.PlayerBean;
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
public class PlayerBeanEdit
        implements Serializable, FIFAConstants {
    private static final long serialVersionUID = 1L;
    private List<PlayerBean> playerList;
    private DataModel<PlayerBean> model;
    private PlayerBean playerBean = new PlayerBean();
    private boolean edit;
    private Integer restrictRows;
    private List<PlayerBean> filteredPlayers;

    @PostConstruct
    public void init() {
        int restrictRows = 0;
        restrictRows = getPlayerRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        PlayerDao PlayerDao = new PlayerDao();
        this.playerList = PlayerDao.getPlayersEdit();
    }


    private int getPlayerRestrictRows(int restrictRows) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);


        if (session.getAttribute("playersRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("playersRestrictRows")).intValue();
        }
        return restrictRows;
    }


    public String add() {
        this.playerBean.addPlayer();


        this.playerBean.setPlayers(null);

        refreshPage();
        return null;
    }

    public String refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> messages = fc.getMessages();
        if (!messages.hasNext()) {

            String url = "editPlayer_template.jsf";
            ExternalContext ec = fc.getExternalContext();
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }
        return null;
    }

    public String editPlayer() {
        this.playerBean = this.model.getRowData();
        setEdit(true);
        return null;
    }

    public String save() {
        PlayerDao PlayerDao = new PlayerDao();
        PlayerDao.updatePlayer(this.playerBean.getPlayerName(), this.playerBean.getPlayerComments());
        this.playerBean = new PlayerBean();
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
        PlayerDao PlayerDao = new PlayerDao();
        this.playerBean = this.model.getRowData();
        PlayerDao.deletePlayer(this.playerBean.getPlayerName());
        this.playerBean = new PlayerBean();
        setEdit(false);

        return refreshPage();
    }


    public List<PlayerBean> getPlayerList() {
        return this.playerList;
    }


    public DataModel<PlayerBean> getModel() {
        if (this.model == null) {
            this.model = new ListDataModel(this.playerList);
        }

        return this.model;
    }


    public PlayerBean getPlayerBean() {
        return this.playerBean;
    }


    public boolean isEdit() {
        return this.edit;
    }


    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    public List<PlayerBean> getFilteredPlayers() {
        return this.filteredPlayers;
    }


    public void setFilteredPlayers(List<PlayerBean> filteredPlayers) {
        this.filteredPlayers = filteredPlayers;
    }


    private void resetPlaceHolder() {
        this.playerBean = new PlayerBean();
        setEdit(false);
    }


    public Integer getRestrictRows() {
        return this.restrictRows;
    }


    public void setRestrictRows(Integer restrictRows) {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("playersRestrictRows", restrictRows);
    }
}
