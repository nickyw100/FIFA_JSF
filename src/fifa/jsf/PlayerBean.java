// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerBean.java

package fifa.jsf;

import fifa.dao.PlayerDao;
import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.StringUtils;

public class PlayerBean
    implements Serializable
{

    public void setPlayers(List players)
    {
        this.players = players;
    }

    public PlayerBean()
    {
        noMatches = Boolean.valueOf(false);
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public boolean isOriginFriend()
    {
        return originFriend;
    }

    public void setOriginFriend(boolean originFriend)
    {
        this.originFriend = originFriend;
    }

    public String getPlayerComments()
    {
        return playerComments;
    }

    public void setPlayerComments(String playerComments)
    {
        this.playerComments = playerComments;
    }

    public Boolean getNoMatches()
    {
        return noMatches;
    }

    public void setNoMatches(Boolean noMatches)
    {
        this.noMatches = noMatches;
    }

    public List getPlayers()
    {
        return players;
    }

    public List getPlayersFromDao()
    {
        PlayerDao playerDao = new PlayerDao();
        players = playerDao.getPlayerNames();
        return players;
    }

    public List completePlayer(String playerPrefix)
    {
        players = getPlayersFromDao();
        List matches = new ArrayList();
        for(Iterator iterator = players.iterator(); iterator.hasNext();)
        {
            String possiblePlayer = (String)iterator.next();
            if(possiblePlayer.toUpperCase().contains(playerPrefix.toUpperCase()))
                matches.add(possiblePlayer);
        }

        if(StringUtils.isNotEmpty(playerPrefix))
            setPlayerName(playerPrefix);
        return matches;
    }

    public void addPlayer()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if(getPlayerName() != null && getPlayerName().length() > 4)
        {
            PlayerDao playerDao = new PlayerDao();
            playerDao.addPlayer(getPlayerName(), "(Added via search Panel)");
            if(context.getMessageList().size() == 0)
            {
                context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_INFO, (new StringBuilder("Successful. Player: ")).append(getPlayerName()).append(" added.").toString(), null));
                setPlayerName(null);
            }
        } else
        if(playerName != null && playerName.length() > 0)
            context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Player name must be at least 5 characters.", null));
        else
            context.addMessage("addPlayer", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Player name entered!", "(You sir, are a dumbass.)"));
    }

    public void addPlayerFromStatsCrud()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if(getPlayerName() != null && getPlayerName().length() > 4)
        {
            PlayerDao playerDao = new PlayerDao();
            playerDao.addPlayer(getPlayerName(), "(Added via stats CRUD Panel)");
            if(context.getMessageList().size() == 0)
                context.addMessage("addPlayerName", new FacesMessage(FacesMessage.SEVERITY_INFO, (new StringBuilder("Successful. Player: ")).append(getPlayerName()).append(" added.").toString(), null));
        } else
        if(getPlayerName() != null && getPlayerName().length() > 0)
            context.addMessage("addPlayerName", new FacesMessage(FacesMessage.SEVERITY_INFO, "Player name must be at least 5 characters.", null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No Player name entered!", "(You sir, are a dumbass.)"));
    }

    public String showPlayers()
    {
        return "show-players";
    }

    private static final long serialVersionUID = 1L;
    private String playerName;
    private boolean originFriend;
    private String playerComments;
    private List players;
    private Boolean noMatches;
}
