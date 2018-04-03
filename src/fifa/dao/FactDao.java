package fifa.dao;

import fifa.facts.FactBean;
import fifa.jsf.StatsBean;
import fifa.utilities.BuildFactsException;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FactDao implements FIFAConstants
{
  private final String sqlException = "Error building facts! Check the logs!";
  
  public FactDao() {}
  
  public List<FactBean> getFacts() { Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    List<FactBean> facts = new ArrayList();
    
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.factsSelect"));
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          FactBean factBean = new FactBean();
          factBean.setFactId(rs.getString("factId"));
          factBean.setFactDescription(rs.getString("factDescription"));
          factBean.setVersionId(rs.getString("versionId"));
          factBean.setFactResult(rs.getString("factResult"));
          factBean.setGoodFact(rs.getBoolean("goodFact"));
          factBean.setActive(Boolean.valueOf(rs.getBoolean("active")));
          facts.add(factBean);
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return facts;
  }
  
  public String getFactDescription(String versionId, String factId) {
    String factDescription = null;
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
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.factDescriptionSelect"));
        
        preparedStatement.setString(1, versionId);
        preparedStatement.setString(2, factId);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          factDescription = rs.getString("factDescription");
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return factDescription;
  }
  
  public void addFact(FactBean factBean) {
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
        
        preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.factInsert"));
        
        preparedStatement.setString(1, factBean.getVersionId());
        preparedStatement.setString(2, factBean.getFactId());
        preparedStatement.setString(3, factBean.getFactDescription());
        preparedStatement.setString(4, factBean.getFactResult());
        preparedStatement.setBoolean(5, factBean.getGoodFact());
        preparedStatement.setBoolean(6, factBean.getActive().booleanValue());
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (se.getMessage().startsWith("Duplicate entry"))
      {
        fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fact ID already exists", 
          "Check to see if this fact ID & version already exists"));
      } else {
        fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", 
          "Something terrible has happened while trying to add a fact!"));
        System.err.println(se.getLocalizedMessage());
      }
    }
  }
  
  public void updateFact(FactBean factBean) {
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
        
        preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.factUpdate"));
        
        preparedStatement.setString(1, factBean.getFactDescription());
        preparedStatement.setString(2, factBean.getFactResult());
        preparedStatement.setBoolean(3, factBean.getGoodFact());
        
        Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
        preparedStatement.setTimestamp(4, sqlTimestamp);
        preparedStatement.setBoolean(5, factBean.getActive().booleanValue());
        
        preparedStatement.setString(6, factBean.getVersionId());
        preparedStatement.setString(7, factBean.getFactId());
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", 
        "Something terrible has happened while trying to update a fact!"));
      System.err.println(se.getLocalizedMessage());
    }
  }
  
  public int getGamesSinceLoss(String versionId) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    int numberOfGames = 0;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.currentWinStreak"));
        
        preparedStatement.setString(1, versionId);
        preparedStatement.setString(2, versionId);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
        {
          numberOfGames = rs.getInt(1);
        }
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return numberOfGames;
  }
  
  public int getGamesSinceWin(String versionId) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    int numberOfGames = 0;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.currentLossStreak"));
        
        preparedStatement.setString(1, versionId);
        preparedStatement.setString(2, versionId);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
        {
          numberOfGames = rs.getInt(1);
        }
      }
    }
    catch (SQLException se)
    {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return numberOfGames;
  }
  
  public int getHighestDivisionReached(String versionId) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    int highestDivision = 0;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.highestDivisionReached"));
        
        preparedStatement.setString(1, versionId);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
        {
          highestDivision = rs.getInt(2);
        }
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return highestDivision;
  }
  
  public StatsBean getTeamLeastPlayed(String versionId) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    String teamName = null;
    StatsBean statsBean = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.teamLeastPlayed"));
        
        preparedStatement.setString(1, versionId);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
          statsBean = new StatsBean();
          String countryId = rs.getString(1);
          String teamId = rs.getString(2);
          
          if (teamId != null) {
            TeamDao teamDao = new TeamDao();
            teamName = teamDao.getTeamName(countryId, teamId);
          }
          statsBean.setTeamName(teamName);
          

          statsBean.setGoalsFor(rs.getInt(3));
        }
      }
    }
    catch (SQLException se)
    {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return statsBean;
  }
  
  public StatsBean getTeamMostPlayed(String versionId) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    String teamName = null;
    StatsBean statsBean = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.teamMostPlayed"));
        
        preparedStatement.setString(1, versionId);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
          statsBean = new StatsBean();
          String countryId = rs.getString(1);
          String teamId = rs.getString(2);
          
          if (teamId != null) {
            TeamDao teamDao = new TeamDao();
            teamName = teamDao.getTeamName(countryId, teamId);
          }
          statsBean.setCountryId(countryId);
          statsBean.setTeamId(teamId);
          statsBean.setTeamName(teamName);
          

          statsBean.setGoalsFor(rs.getInt(3));
        }
      }
    }
    catch (SQLException se)
    {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return statsBean;
  }
  
  public StatsBean getMostGoalsScored(String versionId, String homeAway) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    StatsBean statsBean = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.mostGoalsScored"));
        
        preparedStatement.setString(1, versionId);
        preparedStatement.setString(2, homeAway);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
          statsBean = new StatsBean();
          statsBean.setTeamName(rs.getString(2));
          statsBean.setGoalsFor(rs.getInt(3));
          statsBean.setGameDateTime(rs.getDate(4));
        }
      }
    }
    catch (SQLException se)
    {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null) {
        jdbcConnect.closeConnection(conn);
      }
    }
    return statsBean;
  }
  
  public StatsBean getMostGoalsConceeded(String versionId, String homeAway) throws BuildFactsException
  {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    StatsBean statsBean = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        

        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.mostGoalsConceeded"));
        
        preparedStatement.setString(1, versionId);
        preparedStatement.setString(2, homeAway);
        
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
          statsBean = new StatsBean();
          statsBean.setTeamName(rs.getString(2));
          statsBean.setGoalsAgainst(rs.getInt(3));
          statsBean.setGameDateTime(rs.getDate(4));
        }
      }
    }
    catch (SQLException se) {
      System.err.println(se.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    }
    catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
      throw new BuildFactsException("Error building facts! Check the logs!");
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return statsBean;
  }
  
  public String getLastFactBuildDate() {
    String factBuildDate = null;
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
        
        preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), 
          "sql.factsLastBuilt"));
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          factBuildDate = rs.getString(1);
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return factBuildDate;
  }
}
