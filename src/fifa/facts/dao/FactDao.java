// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FactDao.java

package fifa.facts.dao;

import fifa.dao.TeamDao;
import fifa.facts.FactBean;
import fifa.facts.utilities.BuildFactsException;
import fifa.jsf.StatsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactDao
        implements FIFAConstants {

    private final String sqlException = "Error building facts! Check the logs!";

    public FactDao() {
    }

    public List getFacts() {
        Connection conn;
        JDBCConnect jdbcConnect;
        List facts;
        PropertiesUtilities propertiesUtilities;
        String factVersionId;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        facts = new ArrayList();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        propertiesUtilities = PropertiesUtilities.getInstance();
        factVersionId = (String) session.getAttribute("factVersion");
        if (StringUtils.isBlank(factVersionId))
            factVersionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        try {
            if (conn != null) {

                if (StringUtils.isBlank(factVersionId) || factVersionId.equals("ALL")) {
                    preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factsSelect"));
                } else {
                    preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factsVersionSelect"));
                    preparedStatement.setString(1, factVersionId);
                }
                ResultSet rs;
                FactBean factBean;
                for (rs = preparedStatement.executeQuery(); rs.next(); facts.add(factBean)) {
                    factBean = new FactBean();
                    factBean.setFactId(rs.getString("factId"));
                    factBean.setFactDescription(rs.getString("factDescription"));
                    factBean.setVersionId(rs.getString("versionId"));
                    factBean.setFactResult(rs.getString("factResult"));
                    factBean.setGoodFact(rs.getBoolean("goodFact"));
                    factBean.setActive(Boolean.valueOf(rs.getBoolean("active")));
                }

                rs.close();
            }
        } catch (SQLException se) {

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return facts;
    }

    public String getFactDescription(String versionId, String factId) {
        String factDescription;
        Connection conn;
        JDBCConnect jdbcConnect;
        factDescription = null;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        try {
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factDescriptionSelect"));
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, factId);
                ResultSet rs;
                for (rs = preparedStatement.executeQuery(); rs.next(); )
                    factDescription = rs.getString("factDescription");

                rs.close();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return factDescription;
    }

    public void addFact(FactBean factBean) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factInsert"));
                preparedStatement.setString(1, factBean.getVersionId());
                preparedStatement.setString(2, factBean.getFactId());
                preparedStatement.setString(3, factBean.getFactDescription());
                preparedStatement.setString(4, factBean.getFactResult());
                preparedStatement.setBoolean(5, factBean.getGoodFact());
                preparedStatement.setBoolean(6, factBean.getActive().booleanValue());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (se.getMessage().startsWith("Duplicate entry")) {
                fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fact ID already exists", "Check to see if this fact ID & version already exists"));
            } else {
                fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened while trying to add a fact!"));
                System.err.println(se.getLocalizedMessage());
            }
        }
    }

    public void updateFact(FactBean factBean) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factUpdate"));
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
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened while trying to update a fact!"));
            System.err.println(se.getLocalizedMessage());
        }
    }

    public int getGamesSinceLoss(String versionId)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        int numberOfGames;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        numberOfGames = 0;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.currentGamesSinceLoss"));
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, versionId);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); )
                    numberOfGames = rs.getInt(1);

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return numberOfGames;
    }

    public int getGamesSinceWin(String versionId)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        int numberOfGames;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        numberOfGames = 0;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.currentGamesSinceWin"));
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, versionId);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); )
                    numberOfGames = rs.getInt(1);

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return numberOfGames;
    }

    public int getHighestDivisionReached(String versionId)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        int highestDivision;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        highestDivision = 0;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.highestDivisionReached"));
                preparedStatement.setString(1, versionId);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); )
                    highestDivision = rs.getInt(2);

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }
        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return highestDivision;
    }

    public StatsBean getTeamLeastPlayed(String versionId)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        String teamName;
        StatsBean statsBean;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        teamName = null;
        statsBean = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.teamLeastPlayed"));
                preparedStatement.setString(1, versionId);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); statsBean.setGoalsFor(rs.getInt(3))) {
                    statsBean = new StatsBean();
                    String countryId = rs.getString(1);
                    String teamId = rs.getString(2);
                    if (teamId != null) {
                        TeamDao teamDao = new TeamDao();
                        teamName = teamDao.getTeamName(countryId, teamId);
                    }
                    statsBean.setTeamName(teamName);
                }

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return statsBean;
    }

    public StatsBean getTeamMostPlayed(String versionId)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        String teamName;
        StatsBean statsBean;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        teamName = null;
        statsBean = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.teamMostPlayed"));
                preparedStatement.setString(1, versionId);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); statsBean.setGoalsFor(rs.getInt(3))) {
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
                }

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return statsBean;
    }

    public StatsBean getMostGoalsScored(String versionId, String homeAway)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        StatsBean statsBean;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        statsBean = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.mostGoalsScored"));
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, homeAway);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); statsBean.setGameDateTime(rs.getDate(4))) {
                    statsBean = new StatsBean();
                    statsBean.setTeamName(rs.getString(2));
                    statsBean.setGoalsFor(rs.getInt(3));
                }

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return statsBean;
    }

    public StatsBean getMostGoalsConceeded(String versionId, String homeAway)
            throws BuildFactsException {
        Connection conn;
        JDBCConnect jdbcConnect;
        StatsBean statsBean;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        statsBean = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.mostGoalsConceeded"));
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, homeAway);
                for (ResultSet rs = preparedStatement.executeQuery(); rs.next(); statsBean.setGameDateTime(rs.getDate(4))) {
                    statsBean = new StatsBean();
                    statsBean.setTeamName(rs.getString(2));
                    statsBean.setGoalsAgainst(rs.getInt(3));
                }

            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            throw new BuildFactsException("Error building facts! Check the logs!");
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return statsBean;
    }

    public String getLastFactBuildDate() {
        String factBuildDate;
        Connection conn;
        JDBCConnect jdbcConnect;
        factBuildDate = null;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        try {
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getFactResource(), "sql.factsLastBuilt"));
                ResultSet rs;
                for (rs = preparedStatement.executeQuery(); rs.next(); )
                    factBuildDate = rs.getString(1);

                rs.close();
            }
        } catch (SQLException se) {

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        } catch (Exception e) {

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addFactId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error building facts! Check the logs!", "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }

        if (conn != null)
            jdbcConnect.closeConnection(conn);
        return factBuildDate;
    }
}
