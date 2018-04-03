// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TeamDao.java

package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.TeamBean;
import fifa.utilities.*;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class TeamDao
    implements FIFAConstants
{

    public TeamDao()
    {
    }

    public List getTeamsEdit()
    {
        List teamsEdit;
        Connection conn;
        JDBCConnect jdbcConnect;
        teamsEdit = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            int restrictRows = getRestrictedRows();
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamsEditSelect");
            if(StringUtils.isEmptyOrWhitespaceOnly(sql))
                throw new IOException("SQL Select statement is null.");
            if(restrictRows > 1)
                sql = (new StringBuilder(String.valueOf(sql))).append(" LIMIT ").append(restrictRows).append(";").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(";").toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            TeamBean team;
            for(rs = preparedStatement.executeQuery(); rs.next(); teamsEdit.add(team))
            {
                team = new TeamBean();
                team.setTeamId(rs.getString("teamId"));
                team.setCountryId(rs.getString("countryId"));
                team.setTeamName(rs.getString("teamName"));
                team.setLogoImage(rs.getString("logoImage"));
                team.setTeamComments(rs.getString("teamComments"));
            }

            rs.close();
        }
        break MISSING_BLOCK_LABEL_335;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_345;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_345;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return teamsEdit;
    }

    private int getRestrictedRows()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        int restrictRows = 0;
        if(session.getAttribute("teamsRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("teamsRestrictRows")).intValue();
        return restrictRows;
    }

    public List getTeamNames()
    {
        List teamNames;
        Connection conn;
        JDBCConnect jdbcConnect;
        teamNames = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamsSelect");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next(); teamNames.add(rs.getString("teamName")));
            rs.close();
        }
        break MISSING_BLOCK_LABEL_176;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_186;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_186;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return teamNames;
    }

    public String getTeamName(String countryId, String teamId)
    {
        String teamName;
        Connection conn;
        JDBCConnect jdbcConnect;
        teamName = null;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamSelect"));
            preparedStatement.setString(1, countryId);
            preparedStatement.setString(2, teamId);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next();)
                teamName = rs.getString("teamName");

            rs.close();
        }
        break MISSING_BLOCK_LABEL_191;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_203;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_203;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return teamName;
    }

    public TeamBean getTeamByName(String teamName)
    {
        TeamBean teamBean;
        Connection conn;
        JDBCConnect jdbcConnect;
        teamBean = new TeamBean();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamBeanSelect"));
            preparedStatement.setString(1, teamName);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next(); teamBean.setTeamName(teamName))
            {
                teamBean.setTeamId(rs.getString("teamId"));
                teamBean.setCountryId(rs.getString("countryId"));
            }

            rs.close();
        }
        break MISSING_BLOCK_LABEL_199;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_209;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_209;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return teamBean;
    }

    public String getTeamLogo(String countryId, String teamId)
    {
        String logoImage;
        Connection conn;
        JDBCConnect jdbcConnect;
        logoImage = null;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamLogoSelect"));
            preparedStatement.setString(1, countryId);
            preparedStatement.setString(2, teamId);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next();)
                logoImage = rs.getString("logoImage");

            rs.close();
        }
        break MISSING_BLOCK_LABEL_191;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_203;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_203;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return logoImage;
    }

    public void addTeam(String teamId, String countryId, String teamName, String teamComments, String logoImage)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamInsert"));
                preparedStatement.setString(1, teamId);
                preparedStatement.setString(2, countryId);
                preparedStatement.setString(3, teamName);
                if(StringUtils.isEmptyOrWhitespaceOnly(teamComments))
                    teamComments = null;
                preparedStatement.setString(4, teamComments);
                preparedStatement.setString(5, logoImage);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            if(se.getMessage().startsWith("Duplicate entry"))
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team/Country already exists", "Check to see if this team/country already exists"));
            else
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        }
    }

    public void updateTeam(String teamId, String countryId, String teamName, String teamComments, String logoImage)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamUpdate"));
                preparedStatement.setString(1, teamName);
                if(StringUtils.isEmptyOrWhitespaceOnly(teamComments))
                    teamComments = null;
                preparedStatement.setString(2, teamComments);
                preparedStatement.setString(3, logoImage);
                preparedStatement.setString(4, teamId);
                preparedStatement.setString(5, countryId);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        }
    }

    public void deleteTeam(String teamId, String countryId)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.teamDelete"));
                preparedStatement.setString(1, teamId);
                preparedStatement.setString(2, countryId);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            if(se.getMessage().startsWith("Cannot delete"))
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team ID/Country ID still in use", "Check to see where this team is still being used"));
            else
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        }
    }
}
