package fifa.jsf;

import fifa.dao.PlayerDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.StringUtils;



@ManagedBean
@SessionScoped
public class PlayerBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String playerName;
    private boolean originFriend;
    private String playerComments;
    private List<String> players;
    private Boolean noMatches = Boolean.valueOf(false);


    public void setPlayers(List<String> players) { this.players = players; }







    public String getPlayerName() { return this.playerName; }



    public void setPlayerName(String playerName) { this.playerName = playerName; }



    public boolean isOriginFriend() { return this.originFriend; }



    public void setOriginFriend(boolean originFriend) { this.originFriend = originFriend; }



    public String getPlayerComments() { return this.playerComments; }



    public void setPlayerComments(String playerComments) { this.playerComments = playerComments; }



    public Boolean getNoMatches() { return this.noMatches; }



    public void setNoMatches(Boolean noMatches) { this.noMatches = noMatches; }



    public List<String> getPlayers() { return this.players; }


    public List<String> getPlayersFromDao() {
        PlayerDao playerDao = new PlayerDao();
        this.players = playerDao.getPlayerNames();
        return this.players;
    }


    public List<String> completePlayer(String playerPrefix) {
        this.players = getPlayersFromDao();
        List<String> matches = new ArrayList<String>();

        for (String possiblePlayer : this.players) {
            if (possiblePlayer.toUpperCase().contains(playerPrefix.toUpperCase())) {
                matches.add(possiblePlayer);
            }
        }
        if (StringUtils.isNotEmpty(playerPrefix))
        {
            setPlayerName(playerPrefix);
        }

        return matches;
    }





    public void addPlayer() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (getPlayerName() != null && getPlayerName().length() > 4) {
            PlayerDao playerDao = new PlayerDao();
            playerDao.addPlayer(getPlayerName(), "(Added via search Panel)");

            if (context.getMessageList().size() == 0) {
                context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful. Player: " + getPlayerName() + " added.", null));
                setPlayerName(null);
            }

        } else if (this.playerName != null && this.playerName.length() > 0) {
            context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Player name must be at least 5 characters.",
                    null));
        } else {
            context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Player name entered!",
                    "(You sir, are a dumbass.)"));
        }
    }



    public void addPlayerFromStatsCrud() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (getPlayerName() != null && getPlayerName().length() > 4) {
            PlayerDao playerDao = new PlayerDao();
            playerDao.addPlayer(getPlayerName(), "(Added via stats CRUD Panel)");

            if (context.getMessageList().size() == 0) {
                context.addMessage("addPlayerName", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful. Player: " + getPlayerName() + " added.", null));
            }
        }
        else if (getPlayerName() != null && getPlayerName().length() > 0) {
            context.addMessage("addPlayerName", new FacesMessage(FacesMessage.SEVERITY_INFO, "Player name must be at least 5 characters.",
                    null));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No Player name entered!",
                    "(You sir, are a dumbass.)"));
        }
    }




    public String showPlayers() { return "show-players"; }
}
