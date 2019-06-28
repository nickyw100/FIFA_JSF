// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TeamBean.java

package fifa.jsf;

import fifa.dao.TeamDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeamBean
    implements Serializable
{

    public TeamBean()
    {
    }

    public String getTeamId()
    {
        return teamId;
    }

    public void setTeamId(String teamId)
    {
        this.teamId = teamId;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamComments()
    {
        return teamComments;
    }

    public void setTeamComments(String teamComments)
    {
        this.teamComments = teamComments;
    }

    public String getLogoImage()
    {
        return logoImage;
    }

    public void setLogoImage(String logoImage)
    {
        this.logoImage = logoImage;
    }

    public List getTeamsFromDao()
    {
        TeamDao teamDao = new TeamDao();
        teams = teamDao.getTeamNames();
        return teams;
    }

    public List completeTeam(String teamPrefix)
    {
        teams = getTeamsFromDao();
        List matches = new ArrayList();
        for(Iterator iterator = teams.iterator(); iterator.hasNext();)
        {
            String possibleTeam = (String)iterator.next();
            if(possibleTeam.toUpperCase().contains(teamPrefix.toUpperCase()))
                matches.add(possibleTeam);
        }

        return matches;
    }

    public String showTeams()
    {
        return "show-teams";
    }

    public List getTeams()
    {
        return teams;
    }

    public void setTeams(List teams)
    {
        this.teams = teams;
    }

    private static final long serialVersionUID = 1L;
    private String teamId;
    private String countryId;
    private String teamName;
    private String teamComments;
    private String logoImage;
    private List teams;
}
