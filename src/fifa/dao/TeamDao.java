package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.TeamBean;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class TeamDao implements fifa.utilities.FIFAConstants
{
  public TeamDao() {}
  
  public List<TeamBean> getTeamsEdit()
  {
    List<TeamBean> teamsEdit = new ArrayList();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      if (conn != null) {
        int restrictRows = getRestrictedRows();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamsEditSelect");
        
        if (StringUtils.isEmptyOrWhitespaceOnly(sql)) {
          throw new java.io.IOException("SQL Select statement is null.");
        }
        
        if (restrictRows > 1) {
          sql = sql + " LIMIT " + restrictRows + ";";
        } else {
          sql = sql + ";";
        }
        
        preparedStatement = conn.prepareStatement(sql);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next())
        {
          TeamBean team = new TeamBean();
          team.setTeamId(rs.getString("teamId"));
          team.setCountryId(rs.getString("countryId"));
          team.setTeamName(rs.getString("teamName"));
          team.setLogoImage(rs.getString("logoImage"));
          team.setTeamComments(rs.getString("teamComments"));
          teamsEdit.add(team);
        }
        
        rs.close();
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return teamsEdit;
  }
  
  private int getRestrictedRows() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
    
    int restrictRows = 0;
    
    if (session.getAttribute("teamsRestrictRows") != null) {
      restrictRows = ((Integer)session.getAttribute("teamsRestrictRows")).intValue();
    }
    return restrictRows;
  }
  


  public List<String> getTeamNames()
  {
    List<String> teamNames = new ArrayList();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamsSelect");
        
        preparedStatement = conn.prepareStatement(sql);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next())
        {
          teamNames.add(rs.getString("teamName"));
        }
        
        rs.close();
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null) {
        jdbcConnect.closeConnection(conn);
      }
    }
    return teamNames;
  }
  



  public String getTeamName(String countryId, String teamId)
  {
    String teamName = null;
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamSelect"));
        
        preparedStatement.setString(1, countryId);
        preparedStatement.setString(2, teamId);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          teamName = rs.getString("teamName");
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return teamName;
  }
  



  public TeamBean getTeamByName(String teamName)
  {
    TeamBean teamBean = new TeamBean();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamBeanSelect"));
        
        preparedStatement.setString(1, teamName);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          teamBean.setTeamId(rs.getString("teamId"));
          teamBean.setCountryId(rs.getString("countryId"));
          teamBean.setTeamName(teamName);
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return teamBean;
  }
  



  public String getTeamLogo(String countryId, String teamId)
  {
    String logoImage = null;
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamLogoSelect"));
        
        preparedStatement.setString(1, countryId);
        preparedStatement.setString(2, teamId);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          logoImage = rs.getString("logoImage");
        }
        
        rs.close();
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return logoImage;
  }
  
  public void addTeam(String teamId, String countryId, String teamName, String teamComments, String logoImage) {
    Connection conn1 = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn1 = jdbcConnect.getConnection();
      
      if (conn1 != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamInsert"));
        
        preparedStatement.setString(1, teamId);
        preparedStatement.setString(2, countryId);
        preparedStatement.setString(3, teamName);
        if (StringUtils.isEmptyOrWhitespaceOnly(teamComments)) {
          teamComments = null;
        }
        preparedStatement.setString(4, teamComments);
        preparedStatement.setString(5, logoImage);
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (se.getMessage().startsWith("Duplicate entry"))
      {

        fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team/Country already exists", 
          "Check to see if this team/country already exists"));
      } else {
        fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
          "Something terrible has happened!"));
      }
      System.err.println(se.getLocalizedMessage());
    }
  }
  
  public void updateTeam(String teamId, String countryId, String teamName, String teamComments, String logoImage) {
    Connection conn1 = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn1 = jdbcConnect.getConnection();
      
      if (conn1 != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamUpdate"));
        
        preparedStatement.setString(1, teamName);
        if (StringUtils.isEmptyOrWhitespaceOnly(teamComments)) {
          teamComments = null;
        }
        preparedStatement.setString(2, teamComments);
        preparedStatement.setString(3, logoImage);
        preparedStatement.setString(4, teamId);
        preparedStatement.setString(5, countryId);
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
  }
  
  public void deleteTeam(String teamId, String countryId) {
    Connection conn1 = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn1 = jdbcConnect.getConnection();
      
      if (conn1 != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), 
          "sql.teamDelete"));
        
        preparedStatement.setString(1, teamId);
        preparedStatement.setString(2, countryId);
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (se.getMessage().startsWith("Cannot delete"))
      {
        fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team ID/Country ID still in use", 
          "Check to see where this team is still being used"));
      } else {
        fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
          "Something terrible has happened!"));
      }
      System.err.println(se.getLocalizedMessage());
    }
  }
}
