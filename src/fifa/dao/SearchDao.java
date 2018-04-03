// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchDao.java

package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.SearchResultsBean;
import fifa.jsf.StatsBean;
import fifa.utilities.*;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

// Referenced classes of package fifa.dao:
//            TeamDao, CountryDao

public class SearchDao
    implements FIFAConstants
{

    public SearchDao()
    {
    }

    public List getSearchResults(String teamName, String playerName, fifa.jsf.StatsBean.HomeAwayEnum homeAway, Date fromDate, Date toDate, fifa.jsf.StatsBean.GameTypeEnum gameType, int division, 
            fifa.jsf.StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName)
    {
        List results;
        Connection conn;
        JDBCConnect jdbcConnect;
        String myTeamName;
        String myLogoImage;
        logger.debug("Entering the getSearchResults method.");
        results = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        myTeamName = null;
        myLogoImage = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String orderBy = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.searchOrderBy");
            String searchSelect = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.searchSelect");
            if(StringUtils.isEmptyOrWhitespaceOnly(searchSelect))
                throw new IOException("SQL Select statement is null.");
            String sql = (new StringBuilder(String.valueOf(searchSelect))).append(getWhereClause(teamName, playerName, homeAway.getValue(), fromDate, toDate, gameType.getValue(), division, searchCriteria, possessionPercentage, matchAbandoned, extraTime, countryName, true)).append(orderBy).toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.isBeforeFirst())
            {
                String messageResources = propertiesUtilities.getMessageResource();
                String versionId = propertiesUtilities.getProperty(messageResources, "defaultVersion");
                if(!versionId.equalsIgnoreCase("A"))
                {
                    rs = searchWithoutVersion(teamName, playerName, homeAway.getValue(), fromDate, toDate, gameType.getValue(), division, searchCriteria, possessionPercentage, matchAbandoned, extraTime, countryName, preparedStatement, orderBy, searchSelect, rs);
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesMessage message = new FacesMessage((new StringBuilder("Nothing found for ")).append(versionId).append(". Searched all versions for results.").toString());
                    context.addMessage(null, message);
                }
            }
            SearchResultsBean searchResults;
            for(; rs.next(); results.add(searchResults))
            {
                searchResults = new SearchResultsBean();
                searchResults.setVersionId(rs.getString("s.versionId"));
                searchResults.setCountryId(rs.getString("s.countryId"));
                searchResults.setCountryName(rs.getString("c.countryName"));
                searchResults.setPlayerName(rs.getString("s.playerName"));
                searchResults.setMyTeamId(rs.getString("s.myTeamId"));
                searchResults.setTeamName(rs.getString("t.teamName"));
                searchResults.setGameType(fifa.jsf.StatsBean.GameTypeEnum.findByValue(rs.getString("s.gameType")));
                searchResults.setGoalsFor(rs.getInt("s.goalsFor"));
                searchResults.setGoalsAgainst(rs.getInt("s.goalsAgainst"));
                searchResults.setPenaltiesFor(rs.getInt("s.penaltiesFor"));
                searchResults.setPenaltiesAgainst(rs.getInt("s.penaltiesAgainst"));
                searchResults.setPossessionPercentage(rs.getInt("s.possessionPercentage"));
                searchResults.setShots(rs.getInt("s.shots"));
                searchResults.setShotsOnTarget(rs.getInt("s.shotsOnTarget"));
                searchResults.setOpponentShots(rs.getInt("s.opponentShots"));
                searchResults.setOpponentShotsOnTarget(rs.getInt("s.opponentShotsOnTarget"));
                searchResults.setOpponentDivision(rs.getInt("s.opponentDivision"));
                searchResults.setDivision(rs.getInt("s.division"));
                searchResults.setHomeAway(fifa.jsf.StatsBean.HomeAwayEnum.findByValue(rs.getString("s.homeAway")));
                searchResults.setGameDateTime(rs.getTimestamp("s.gameDateTime"));
                searchResults.setMatchAbandoned(rs.getBoolean("s.matchAbandoned"));
                searchResults.setExtraTime(rs.getBoolean("s.extraTime"));
                searchResults.setGameComments(rs.getString("s.gameComments"));
                searchResults.setFlagImage(rs.getString("c.flagImage"));
                searchResults.setLogoImage(rs.getString("t.logoImage"));
                searchResults.setPlayerComments(rs.getString("p.playerComments"));
                if(StringUtils.isNullOrEmpty(myTeamName))
                {
                    TeamDao teamDao = new TeamDao();
                    myTeamName = teamDao.getTeamName("ENG", searchResults.getMyTeamId());
                    myLogoImage = teamDao.getTeamLogo("ENG", searchResults.getMyTeamId());
                }
                searchResults.setMyTeamName(myTeamName);
                searchResults.setMyLogoImage(myLogoImage);
                if(searchResults.getHomeAway().equals(fifa.jsf.StatsBean.HomeAwayEnum.Home))
                {
                    searchResults.setAwayTeamName(searchResults.getTeamName());
                    searchResults.setHomeTeamName(myTeamName);
                    searchResults.setHomeTeamLogo(myLogoImage);
                    searchResults.setAwayTeamLogo(searchResults.getLogoImage());
                } else
                {
                    searchResults.setHomeTeamName(searchResults.getTeamName());
                    searchResults.setAwayTeamName(myTeamName);
                    searchResults.setHomeTeamLogo(searchResults.getLogoImage());
                    searchResults.setAwayTeamLogo(myLogoImage);
                }
            }

            rs.close();
        }
        break MISSING_BLOCK_LABEL_943;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_955;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_955;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return results;
    }

    private ResultSet searchWithoutVersion(String teamName, String playerName, String homeAway, Date fromDate, Date toDate, String gameType, int division, 
            fifa.jsf.StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName, PreparedStatement preparedStatement, String orderBy, 
            String searchSelect, ResultSet rs)
        throws SQLException
    {
        String sql = (new StringBuilder(String.valueOf(searchSelect))).append(getWhereClause(teamName, playerName, homeAway, fromDate, toDate, gameType, division, searchCriteria, possessionPercentage, matchAbandoned, extraTime, countryName, false)).append(" ").append(orderBy).toString();
        rs = preparedStatement.executeQuery(sql);
        return rs;
    }

    private String getWhereClause(String teamName, String playerName, String homeAway, Date fromDate, Date toDate, String gameType, int division, 
            fifa.jsf.StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName, boolean versionSearch)
    {
        String whereClause = " WHERE ";
        boolean andNeeded = false;
        if(!StringUtils.isNullOrEmpty(teamName))
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("t.teamName = '").append(teamName).append("' ").toString();
            andNeeded = true;
        }
        if(!StringUtils.isNullOrEmpty(playerName))
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.playerName = '").append(playerName).append("' ").toString();
            andNeeded = true;
        }
        if(!StringUtils.isNullOrEmpty(homeAway) && (homeAway.equalsIgnoreCase("H") || homeAway.equalsIgnoreCase("A")))
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.homeAway = '").append(homeAway).append("' ").toString();
            andNeeded = true;
        }
        if(!StringUtils.isNullOrEmpty(gameType) && (gameType.equalsIgnoreCase("C") || gameType.equalsIgnoreCase("S") || gameType.equalsIgnoreCase("F")))
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.gameType = '").append(gameType).append("' ").toString();
            andNeeded = true;
        }
        if(division > 0 && gameType.equalsIgnoreCase("S"))
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.division = ").append(division).append(" ").toString();
            andNeeded = true;
        }
        if(possessionPercentage > 0)
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.possessionPercentage ").append(searchCriteria.getLabel()).append(" ").append(possessionPercentage).append(" ").toString();
            andNeeded = true;
        }
        if(extraTime)
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.extraTime = '1' ").toString();
            andNeeded = true;
        }
        if(matchAbandoned)
        {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.matchAbandoned = '1' ").toString();
            andNeeded = true;
        }
        if(fromDate != null)
        {
            whereClause = andNeeded(whereClause, andNeeded);
            String fromDateStr = formatDate(fromDate);
            fromDateStr = (new StringBuilder(String.valueOf(fromDateStr))).append(" 00:00:00").toString();
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.gameDateTime >= '").append(fromDateStr).append("' ").toString();
            andNeeded = true;
        }
        if(toDate != null)
        {
            whereClause = andNeeded(whereClause, andNeeded);
            String toDateStr = formatDate(toDate);
            toDateStr = (new StringBuilder(String.valueOf(toDateStr))).append(" 23:59:59").toString();
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.gameDateTime <= '").append(toDateStr).append("' ").toString();
            andNeeded = true;
        }
        if(!StringUtils.isNullOrEmpty(countryName))
        {
            CountryDao countryDao = new CountryDao();
            String countryId = countryDao.getCountryId(countryName);
            if(!StringUtils.isNullOrEmpty(countryId))
            {
                whereClause = andNeeded(whereClause, andNeeded);
                whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.countryId = '").append(countryId).append("' ").toString();
                andNeeded = true;
            }
        }
        if(whereClause.equals(" WHERE "))
            whereClause = "";
        if(versionSearch)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append(propertiesUtilities.addVersionWhere(!andNeeded)).toString();
        }
        if(whereClause.equals(" WHERE ") || whereClause.equals("WHERE "))
            whereClause = " ";
        return whereClause;
    }

    private String andNeeded(String whereClause, boolean andNeeded)
    {
        if(andNeeded)
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("AND ").toString();
        return whereClause;
    }

    private String formatDate(Date inputDate)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(inputDate);
    }

    private static final Logger logger = Logger.getLogger(fifa/dao/SearchDao);

}
