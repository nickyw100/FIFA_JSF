package fifa.jsf;

import fifa.dao.TeamDao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@SessionScoped
public class TeamBean
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String teamId;
    private String countryId;
    private String teamName;
    private String teamComments;
    private String logoImage;
    private List<String> teams;

    public String getTeamId() {
        return this.teamId;
    }


    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }


    public String getCountryId() {
        return this.countryId;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }


    public String getTeamName() {
        return this.teamName;
    }


    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public String getTeamComments() {
        return this.teamComments;
    }


    public void setTeamComments(String teamComments) {
        this.teamComments = teamComments;
    }


    public String getLogoImage() {
        return this.logoImage;
    }


    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }


    public List<String> getTeamsFromDao() {
        TeamDao teamDao = new TeamDao();
        this.teams = teamDao.getTeamNames();
        return this.teams;
    }


    public List<String> completeTeam(String teamPrefix) {
        this.teams = getTeamsFromDao();

        List<String> matches = new ArrayList<String>();
        for (String possibleTeam : this.teams) {

            if (possibleTeam.toUpperCase().contains(teamPrefix.toUpperCase())) {
                matches.add(possibleTeam);
            }
        }
        return matches;
    }


    public String showTeams() {
        return "show-teams";
    }


    public List<String> getTeams() {
        return this.teams;
    }


    public void setTeams(List<String> teams) {
        this.teams = teams;
    }
}
