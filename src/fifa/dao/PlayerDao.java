package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.PlayerBean;
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

public class PlayerDao implements fifa.utilities.FIFAConstants
{
  public PlayerDao() {}
  
  public List<String> getPlayerNames()
  {
    List<String> playerNames = new ArrayList();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    JDBCConnect jdbcConnect = null;
    try
    {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();
      
      if (conn != null)
      {
        preparedStatement = conn.prepareStatement("SELECT playerName FROM player ORDER BY playerName;");
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
          playerNames.add(rs.getString("playerName"));
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return playerNames;
  }
  
  public List<PlayerBean> getPlayersEdit() {
    List<PlayerBean> playersEdit = new ArrayList();
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
        
        String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.playerSelect");
        
        if (restrictRows > 1) {
          sql = sql + " LIMIT " + restrictRows + ";";
        } else {
          sql = sql + ";";
        }
        
        if (StringUtils.isEmptyOrWhitespaceOnly(sql)) {
          throw new java.io.IOException("SQL Select statement is null.");
        }
        
        preparedStatement = conn.prepareStatement(sql);
        
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next())
        {
          PlayerBean player = new PlayerBean();
          player.setPlayerName(rs.getString("playerName"));
          player.setPlayerComments(rs.getString("playerComments"));
          playersEdit.add(player);
        }
        rs.close();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
    catch (Exception e) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return playersEdit;
  }
  
  private int getRestrictedRows() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
    
    int restrictRows = 0;
    
    if (session.getAttribute("playersRestrictRows") != null) {
      restrictRows = ((Integer)session.getAttribute("playersRestrictRows")).intValue();
    }
    return restrictRows;
  }
  
  public void addPlayer(String playerName, String playerComments) {
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
          "sql.playerInsert"));
        
        preparedStatement.setString(1, playerName);
        if (StringUtils.isEmptyOrWhitespaceOnly(playerComments)) {
          playerComments = null;
        }
        preparedStatement.setString(2, playerComments);
        
        preparedStatement.executeUpdate();
        

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("lastPlayerName", playerName);
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (se.getMessage().startsWith("Duplicate entry"))
      {
        fc.addMessage("addPlayerName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Player name already exists", 
          "Check to see if this player already exists"));
      }
      else {
        fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
          "Something terrible has happened!"));
      }
      
      System.err.println(se.getLocalizedMessage());
    }
  }
  
  public void updatePlayer(String playerName, String playerComments) {
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
          "sql.playerUpdate"));
        
        preparedStatement.setString(1, playerComments);
        if (StringUtils.isEmptyOrWhitespaceOnly(playerComments)) {
          playerComments = null;
        }
        preparedStatement.setString(2, playerName);
        
        preparedStatement.executeUpdate();
      }
    }
    catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
        "Something terrible has happened!"));
      System.err.println(se.getLocalizedMessage());
    }
  }
  
  public void deletePlayer(String playerName) {
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
          "sql.playerDelete"));
        
        preparedStatement.setString(1, playerName);
        
        preparedStatement.executeUpdate();
      }
    } catch (SQLException se) {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (se.getMessage().startsWith("Cannot delete"))
      {
        fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Player name still in use", 
          "Check to see where this player is still being used"));
      }
      else {
        fc.addMessage("addPlayerId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", 
          "Something terrible has happened!"));
      }
      System.err.println(se.getLocalizedMessage());
    }
  }
}
