// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StatsDao.java

package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.*;
import fifa.utilities.*;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// Referenced classes of package fifa.dao:
//            TeamDao

public class StatsDao
    implements FIFAConstants
{

    public StatsDao()
    {
    }

    public List getStatsEdit()
    {
        List statsEdit;
        Connection conn;
        JDBCConnect jdbcConnect;
        statsEdit = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            int restrictRows = getRestrictedRows();
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.statsEditSelect");
            if(StringUtils.isEmptyOrWhitespaceOnly(sql))
                throw new IOException("SQL Select statement is null.");
            if(restrictRows > 1)
                sql = (new StringBuilder(String.valueOf(sql))).append(" LIMIT ").append(restrictRows).append(";").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(";").toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            StatsBean stats;
            for(rs = preparedStatement.executeQuery(); rs.next(); statsEdit.add(stats))
            {
                stats = new StatsBean();
                loadStatsValues(rs, stats);
            }

            rs.close();
        }
        break MISSING_BLOCK_LABEL_273;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_283;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_283;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return statsEdit;
    }

    private void loadStatsValues(ResultSet rs, StatsBean stats)
        throws SQLException
    {
        stats.setVersionId(rs.getString("versionId"));
        stats.setTeamId(rs.getString("teamId"));
        stats.setCountryId(rs.getString("countryId"));
        stats.setPlayerName(rs.getString("playerName"));
        stats.setDivision(rs.getInt("division"));
        stats.setGameType(fifa.jsf.StatsBean.GameTypeEnum.findByValue(rs.getString("gameType")));
        stats.setHomeAway(fifa.jsf.StatsBean.HomeAwayEnum.findByValue(rs.getString("homeAway")));
        stats.setGoalsFor(rs.getInt("goalsFor"));
        stats.setGoalsAgainst(rs.getInt("goalsAgainst"));
        stats.setGameDateTime(rs.getTimestamp("gameDateTime"));
        stats.setGameComments(rs.getString("gameComments"));
        stats.setMatchAbandoned(rs.getBoolean("matchAbandoned"));
        stats.setExtraTime(rs.getBoolean("extraTime"));
        stats.setPenaltiesFor(rs.getInt("penaltiesFor"));
        stats.setPenaltiesAgainst(rs.getInt("penaltiesAgainst"));
        stats.setPossessionPercentage(rs.getInt("possessionPercentage"));
        stats.setShots(rs.getInt("shots"));
        stats.setShotsOnTarget(rs.getInt("shotsOnTarget"));
        stats.setOpponentShots(rs.getInt("opponentShots"));
        stats.setOpponentShotsOnTarget(rs.getInt("opponentShotsOnTarget"));
        stats.setOpponentDivision(rs.getInt("opponentDivision"));
    }

    private int getRestrictedRows()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        int restrictRows = 0;
        if(session.getAttribute("statsRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("statsRestrictRows")).intValue();
        return restrictRows;
    }

    public List getLastSix(String competition)
    {
        List results;
        Connection conn;
        JDBCConnect jdbcConnect;
        results = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String last6Select = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.last6Select");
            String orderBy = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.last6OrderBy");
            String sql = (new StringBuilder(String.valueOf(last6Select))).append(getWhereClause(competition, propertiesUtilities)).append(" ").append(orderBy).toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next(); prepareLast6ResultDetails(results, rs));
            rs.close();
        }
        break MISSING_BLOCK_LABEL_224;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        if(results.size() < 6)
        {
            int y = 6 - results.size();
            LastSixBean lastSixBean = new LastSixBean();
            lastSixBean.setWinLossDraw("N");
            for(int x = 0; x < y; x++)
                results.add(lastSixBean);

        }
        return results;
    }

    public List getLastResult()
    {
        List results;
        Connection conn;
        JDBCConnect jdbcConnect;
        results = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.lastResultSelect");
            sql = (new StringBuilder(String.valueOf(sql))).append(propertiesUtilities.addVersionWhere(true)).toString();
            sql = (new StringBuilder(String.valueOf(sql))).append(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.lastResultOrderBy")).toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next(); prepareLastResultDetails(results, rs));
            rs.close();
        }
        break MISSING_BLOCK_LABEL_227;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_237;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_237;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return results;
    }

    private void prepareLast6ResultDetails(List results, ResultSet rs)
    {
        LastSixBean lastSixBean = new LastSixBean();
        int goalsFor = 0;
        int goalsAgainst = 0;
        boolean extraTime = false;
        int penaltiesFor = 0;
        int penaltiesAgainst = 0;
        int possessionPercentage = 0;
        int shots = 0;
        int shotsOnTarget = 0;
        int opponentShots = 0;
        int opponentShotsOnTarget = 0;
        int opponentDivision = 0;
        String teamName = null;
        String playerName = null;
        String gameComments = null;
        Date gameDateTime = null;
        String versionId = null;
        try
        {
            goalsFor = rs.getInt("s.goalsFor");
            goalsAgainst = rs.getInt("s.goalsAgainst");
            extraTime = rs.getBoolean("s.extraTime");
            penaltiesFor = rs.getInt("s.penaltiesFor");
            penaltiesAgainst = rs.getInt("s.penaltiesAgainst");
            possessionPercentage = rs.getInt("s.possessionPercentage");
            shots = rs.getInt("s.shots");
            shotsOnTarget = rs.getInt("s.shotsOnTarget");
            opponentShots = rs.getInt("s.opponentShots");
            opponentShotsOnTarget = rs.getInt("s.opponentShotsOnTarget");
            opponentDivision = rs.getInt("s.opponentDivision");
            playerName = rs.getString("s.playerName");
            gameComments = rs.getString("s.gameComments");
            teamName = rs.getString("t.teamName");
            versionId = rs.getString("s.versionId");
            gameDateTime = rs.getTimestamp("s.gameDateTime");
        }
        catch(SQLException se)
        {
            System.err.println(se.getLocalizedMessage());
        }
        loadSixBeanValues(lastSixBean, goalsFor, goalsAgainst, extraTime, penaltiesFor, penaltiesAgainst, possessionPercentage, shots, shotsOnTarget, opponentShots, opponentShotsOnTarget, opponentDivision, teamName, playerName, gameDateTime, versionId, gameComments);
        results.add(lastSixBean);
    }

    private void loadSixBeanValues(LastSixBean lastSixBean, int goalsFor, int goalsAgainst, boolean extraTime, int penaltiesFor, int penaltiesAgainst, int possessionPercentage, 
            int shots, int shotsOnTarget, int opponentShots, int opponentShotsOnTarget, int opponentDivision, String teamName, String playerName, 
            Date gameDateTime, String versionId, String gameComments)
    {
        if(goalsFor > goalsAgainst)
            lastSixBean.setWinLossDraw("W");
        else
        if(goalsFor < goalsAgainst)
            lastSixBean.setWinLossDraw("L");
        else
        if(extraTime && penaltiesFor > penaltiesAgainst)
            lastSixBean.setWinLossDraw("W");
        else
        if(extraTime && penaltiesFor < penaltiesAgainst)
            lastSixBean.setWinLossDraw("L");
        else
            lastSixBean.setWinLossDraw("D");
        lastSixBean.setVersionId(versionId);
        lastSixBean.setPlayerName(playerName);
        lastSixBean.setTeamName(teamName);
        lastSixBean.setGoalsAgainst(goalsAgainst);
        lastSixBean.setGoalsFor(goalsFor);
        lastSixBean.setPenaltiesAgainst(penaltiesAgainst);
        lastSixBean.setPossessionPercentage(possessionPercentage);
        lastSixBean.setShots(shots);
        lastSixBean.setShotsOnTarget(shotsOnTarget);
        lastSixBean.setOpponentShots(opponentShots);
        lastSixBean.setOpponentShotsOnTarget(opponentShotsOnTarget);
        lastSixBean.setOpponentDivision(opponentDivision);
        lastSixBean.setExtraTime(extraTime);
        lastSixBean.setPenaltiesFor(penaltiesFor);
        lastSixBean.setGameDateTime(gameDateTime);
        lastSixBean.setGameComments(gameComments);
    }

    private void prepareLastResultDetails(List results, ResultSet rs)
        throws SQLException
    {
        LastResultBean lastResultBean = new LastResultBean();
        int goalsFor = 0;
        int goalsAgainst = 0;
        int penaltiesFor = 0;
        int penaltiesAgainst = 0;
        int possessionPercentage = 0;
        int shots = 0;
        int shotsOnTarget = 0;
        int opponentShots = 0;
        int opponentShotsOnTarget = 0;
        int opponentDivision = 0;
        int division = 0;
        String teamName = null;
        String playerName = null;
        String playerComments = null;
        String gameComments = null;
        Date gameDateTime = null;
        String myTeamName = null;
        String myTeamId = null;
        String myLogoImage = null;
        fifa.jsf.StatsBean.HomeAwayEnum homeAway = null;
        fifa.jsf.StatsBean.GameTypeEnum gameType = null;
        String logoImage = null;
        String flagImage = null;
        String countryId = null;
        String countryName = null;
        String versionId = null;
        boolean matchAbandoned = false;
        boolean extraTime = false;
        try
        {
            goalsFor = rs.getInt("s.goalsFor");
            goalsAgainst = rs.getInt("s.goalsAgainst");
            penaltiesFor = rs.getInt("s.penaltiesFor");
            penaltiesAgainst = rs.getInt("s.penaltiesAgainst");
            possessionPercentage = rs.getInt("s.possessionPercentage");
            shots = rs.getInt("s.shots");
            shotsOnTarget = rs.getInt("s.shotsOnTarget");
            opponentShots = rs.getInt("s.opponentShots");
            opponentShotsOnTarget = rs.getInt("s.opponentShotsOnTarget");
            opponentDivision = rs.getInt("s.opponentDivision");
            division = rs.getInt("s.division");
            playerName = rs.getString("s.playerName");
            teamName = rs.getString("t.teamName");
            myTeamId = rs.getString("s.myTeamId");
            homeAway = fifa.jsf.StatsBean.HomeAwayEnum.findByValue(rs.getString("s.homeAway"));
            gameType = fifa.jsf.StatsBean.GameTypeEnum.findByValue(rs.getString("s.gameType"));
            logoImage = rs.getString("t.logoImage");
            flagImage = rs.getString("c.flagImage");
            countryName = rs.getString("c.countryName");
            playerComments = rs.getString("p.playerComments");
            gameComments = rs.getString("s.gameComments");
            matchAbandoned = rs.getBoolean("s.matchAbandoned");
            extraTime = rs.getBoolean("s.extraTime");
            versionId = rs.getString("s.versionId");
            gameDateTime = rs.getTimestamp("s.gameDateTime");
        }
        catch(SQLException se)
        {
            System.err.println(se.getLocalizedMessage());
        }
        loadLastResultBean(lastResultBean, goalsFor, goalsAgainst, penaltiesFor, penaltiesAgainst, possessionPercentage, shots, shotsOnTarget, opponentShots, opponentShotsOnTarget, opponentDivision, division, teamName, playerName, playerComments, gameComments, gameDateTime, myTeamName, myTeamId, myLogoImage, homeAway, gameType, logoImage, flagImage, countryId, countryName, versionId, matchAbandoned, extraTime);
        results.add(lastResultBean);
    }

    private void loadLastResultBean(LastResultBean lastResultBean, int goalsFor, int goalsAgainst, int penaltiesFor, int penaltiesAgainst, int possessionPercentage, int shots, 
            int shotsOnTarget, int opponentShots, int opponentShotsOnTarget, int opponentDivision, int division, String teamName, String playerName, 
            String playerComments, String gameComments, Date gameDateTime, String myTeamName, String myTeamId, String myLogoImage, fifa.jsf.StatsBean.HomeAwayEnum homeAway, 
            fifa.jsf.StatsBean.GameTypeEnum gameType, String logoImage, String flagImage, String countryId, String countryName, String versionId, boolean matchAbandoned, 
            boolean extraTime)
    {
        lastResultBean.setPlayerName(playerName);
        lastResultBean.setTeamName(teamName);
        lastResultBean.setMyTeamId(myTeamId);
        lastResultBean.setGoalsAgainst(goalsAgainst);
        lastResultBean.setGoalsFor(goalsFor);
        lastResultBean.setPenaltiesAgainst(penaltiesAgainst);
        lastResultBean.setPenaltiesFor(penaltiesFor);
        lastResultBean.setPossessionPercentage(possessionPercentage);
        lastResultBean.setShots(shots);
        lastResultBean.setShotsOnTarget(shotsOnTarget);
        lastResultBean.setOpponentShots(opponentShots);
        lastResultBean.setOpponentShotsOnTarget(opponentShotsOnTarget);
        lastResultBean.setOpponentDivision(opponentDivision);
        lastResultBean.setGameDateTime(gameDateTime);
        lastResultBean.setDivision(division);
        lastResultBean.setHomeAway(homeAway);
        lastResultBean.setGameType(gameType);
        lastResultBean.setGameDateTime(gameDateTime);
        lastResultBean.setMatchAbandoned(matchAbandoned);
        lastResultBean.setExtraTime(extraTime);
        lastResultBean.setGameComments(gameComments);
        lastResultBean.setFlagImage(flagImage);
        lastResultBean.setCountryId(countryId);
        lastResultBean.setCountryName(countryName);
        lastResultBean.setVersionId(versionId);
        lastResultBean.setLogoImage(logoImage);
        lastResultBean.setPlayerComments(playerComments);
        if(StringUtils.isNullOrEmpty(myTeamName))
        {
            TeamDao teamDao = new TeamDao();
            myTeamName = teamDao.getTeamName("ENG", lastResultBean.getMyTeamId());
            myLogoImage = teamDao.getTeamLogo("ENG", lastResultBean.getMyTeamId());
        }
        lastResultBean.setMyTeamName(myTeamName);
        lastResultBean.setMyLogoImage(myLogoImage);
        if(lastResultBean.getHomeAway().equals(fifa.jsf.StatsBean.HomeAwayEnum.Home))
        {
            lastResultBean.setAwayTeamName(lastResultBean.getTeamName());
            lastResultBean.setHomeTeamName(myTeamName);
            lastResultBean.setHomeTeamLogo(myLogoImage);
            lastResultBean.setAwayTeamLogo(lastResultBean.getLogoImage());
        } else
        {
            lastResultBean.setHomeTeamName(lastResultBean.getTeamName());
            lastResultBean.setAwayTeamName(myTeamName);
            lastResultBean.setHomeTeamLogo(lastResultBean.getLogoImage());
            lastResultBean.setAwayTeamLogo(myLogoImage);
        }
    }

    private String getWhereClause(String competition, PropertiesUtilities propertiesUtilities)
    {
        String whereClause = null;
        if(!competition.equalsIgnoreCase("A"))
        {
            whereClause = (new StringBuilder(" WHERE s.gameType = '")).append(competition).append("'").toString();
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append(propertiesUtilities.addVersionWhere(false)).toString();
        } else
        {
            whereClause = propertiesUtilities.addVersionWhere(true);
        }
        return whereClause;
    }

    public void addStat(StatsBean statsBean)
    {
        TeamDao teamDao = new TeamDao();
        TeamBean teamBean = teamDao.getTeamByName(statsBean.getTeamName());
        if(teamBean != null)
        {
            statsBean.setCountryId(teamBean.getCountryId());
            statsBean.setTeamId(teamBean.getTeamId());
        }
        if(statsBean.getPenaltiesAgainst() > 0 || statsBean.getPenaltiesFor() > 0)
            statsBean.setExtraTime(true);
        if(statsBean.getGameType().equals(fifa.jsf.StatsBean.GameTypeEnum.Friendly))
        {
            statsBean.setOpponentDivision(0);
            statsBean.setDivision(0);
        } else
        if(statsBean.getGameType().equals(fifa.jsf.StatsBean.GameTypeEnum.Cup))
            statsBean.setDivision(0);
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
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.statInsert"));
                preparedStatement.setString(1, statsBean.getVersionId());
                preparedStatement.setString(2, statsBean.getTeamId());
                preparedStatement.setString(3, statsBean.getCountryId());
                preparedStatement.setString(4, statsBean.getPlayerName());
                preparedStatement.setString(5, statsBean.getGameType().getValue());
                Timestamp sqlTimestamp = new Timestamp(statsBean.getGameDateTime().getTime());
                preparedStatement.setTimestamp(6, sqlTimestamp);
                preparedStatement.setString(7, statsBean.getHomeAway().getValue());
                preparedStatement.setInt(8, statsBean.getDivision());
                preparedStatement.setBoolean(9, statsBean.isMatchAbandoned());
                preparedStatement.setBoolean(10, statsBean.isExtraTime());
                preparedStatement.setInt(11, statsBean.getGoalsFor());
                preparedStatement.setInt(12, statsBean.getGoalsAgainst());
                if(StringUtils.isEmptyOrWhitespaceOnly(statsBean.getGameComments()))
                    statsBean.setGameComments(null);
                preparedStatement.setString(13, statsBean.getGameComments());
                preparedStatement.setInt(14, statsBean.getPenaltiesFor());
                preparedStatement.setInt(15, statsBean.getPenaltiesAgainst());
                preparedStatement.setInt(16, statsBean.getPossessionPercentage());
                preparedStatement.setInt(17, statsBean.getShots());
                preparedStatement.setInt(18, statsBean.getShotsOnTarget());
                preparedStatement.setInt(19, statsBean.getOpponentShots());
                preparedStatement.setInt(20, statsBean.getOpponentShotsOnTarget());
                preparedStatement.setInt(21, statsBean.getOpponentDivision());
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            if(se.getMessage().startsWith("Duplicate entry"))
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Result already exists", "Check to see if this result already exists"));
            else
            if(se.getMessage().startsWith("Cannot add or update a child row: a foreign key constraint fails"))
            {
                if(se.getMessage().contains("teamId1-FK"))
                    fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't find the team ID and/or country ID!", "Enter a valid Team ID and Country ID"));
            } else
            {
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
            }
            System.err.println(se.getLocalizedMessage());
        }
    }

    public void updateStat(StatsBean statsBean)
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
                if(statsBean.getGameType().equals(fifa.jsf.StatsBean.GameTypeEnum.Friendly))
                {
                    statsBean.setOpponentDivision(0);
                    statsBean.setDivision(0);
                } else
                if(statsBean.getGameType().equals(fifa.jsf.StatsBean.GameTypeEnum.Cup))
                    statsBean.setDivision(0);
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.statUpdate"));
                preparedStatement.setString(1, statsBean.getGameType().getValue());
                preparedStatement.setString(2, statsBean.getHomeAway().getValue());
                preparedStatement.setInt(3, statsBean.getDivision());
                if(StringUtils.isEmptyOrWhitespaceOnly(statsBean.getGameComments()))
                    statsBean.setGameComments(null);
                preparedStatement.setString(4, statsBean.getGameComments());
                preparedStatement.setInt(5, statsBean.getGoalsFor());
                preparedStatement.setInt(6, statsBean.getGoalsAgainst());
                preparedStatement.setBoolean(7, statsBean.isMatchAbandoned());
                preparedStatement.setBoolean(8, statsBean.isExtraTime());
                preparedStatement.setInt(9, statsBean.getPenaltiesFor());
                preparedStatement.setInt(10, statsBean.getPenaltiesAgainst());
                preparedStatement.setInt(11, statsBean.getPossessionPercentage());
                preparedStatement.setInt(12, statsBean.getShots());
                preparedStatement.setInt(13, statsBean.getShotsOnTarget());
                preparedStatement.setInt(14, statsBean.getOpponentShots());
                preparedStatement.setInt(15, statsBean.getOpponentShotsOnTarget());
                preparedStatement.setInt(16, statsBean.getOpponentDivision());
                preparedStatement.setString(17, statsBean.getVersionId());
                preparedStatement.setString(18, statsBean.getCountryId());
                preparedStatement.setString(19, statsBean.getTeamId());
                preparedStatement.setString(20, statsBean.getPlayerName());
                Timestamp sqlTimestamp = new Timestamp(statsBean.getGameDateTime().getTime());
                preparedStatement.setTimestamp(21, sqlTimestamp);
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

    public void deleteStat(StatsBean statsbean)
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
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.statDelete"));
                preparedStatement.setString(1, statsbean.getVersionId());
                preparedStatement.setString(2, statsbean.getTeamId());
                preparedStatement.setString(3, statsbean.getCountryId());
                preparedStatement.setString(4, statsbean.getPlayerName());
                Timestamp sqlTimestamp = new Timestamp(statsbean.getGameDateTime().getTime());
                preparedStatement.setTimestamp(5, sqlTimestamp);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            if(se.getMessage().startsWith("Cannot delete"))
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, (new StringBuilder("Cannot delete this row: ")).append(se.getLocalizedMessage()).toString(), se.getMessage()));
            else
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        }
    }

    private final String STATS_EDIT_SELECT = "sql.statsEditSelect";
    private final String foreignKeyException = "Cannot add or update a child row: a foreign key constraint fails";
}
