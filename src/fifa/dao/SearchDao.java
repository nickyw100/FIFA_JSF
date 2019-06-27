package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.SearchResultsBean;
import fifa.jsf.StatsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

public class SearchDao
        implements FIFAConstants
{
  private static final Logger logger = Logger.getLogger(SearchDao.class);


  public List<SearchResultsBean> getSearchResults(String teamName, String playerName, StatsBean.HomeAwayEnum homeAway, Date fromDate, Date toDate, StatsBean.GameTypeEnum gameType, int division, StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName) {
    logger.debug("Entering the getSearchResults method.");

    List<SearchResultsBean> results = new ArrayList<>();
    Connection conn = null;
    PreparedStatement preparedStatement;
    JDBCConnect jdbcConnect = null;
    String myTeamName = null;
    String myLogoImage = null;

    try {
      jdbcConnect = new JDBCConnect();
      conn = jdbcConnect.getConnection();

      if (conn != null) {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

        String orderBy = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.searchOrderBy");
        String searchSelect = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.searchSelect");
        if (StringUtils.isEmptyOrWhitespaceOnly(searchSelect)) {
          throw new IOException("SQL Select statement is null.");
        }


        String sql = String.valueOf(searchSelect) +
                getWhereClause(teamName, playerName, homeAway.getValue(), fromDate, toDate, gameType.getValue(),
                        division, searchCriteria, possessionPercentage, matchAbandoned, extraTime, countryName,
                        true) + orderBy;

        preparedStatement = conn.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();



        if (!rs.isBeforeFirst()) {

          String messageResources = propertiesUtilities.getMessageResource();
          String versionId = propertiesUtilities.getProperty(messageResources, "defaultVersion");

          if (!versionId.equalsIgnoreCase("A")) {

            rs = searchWithoutVersion(teamName, playerName, homeAway.getValue(), fromDate, toDate, gameType.getValue(),
                    division, searchCriteria, possessionPercentage, matchAbandoned, extraTime, countryName,
                    preparedStatement, orderBy, searchSelect, rs);

            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage("Nothing found for " + versionId +
                    ". Searched all versions for results.");
            context.addMessage(null, message);
          }
        }


        while (rs.next()) {

          SearchResultsBean searchResults = new SearchResultsBean();

          searchResults.setVersionId(rs.getString("s.versionId"));
          searchResults.setCountryId(rs.getString("s.countryId"));
          searchResults.setCountryName(rs.getString("c.countryName"));
          searchResults.setPlayerName(rs.getString("s.playerName"));
          searchResults.setMyTeamId(rs.getString("s.myTeamId"));
          searchResults.setTeamName(rs.getString("t.teamName"));
          searchResults.setGameType(StatsBean.GameTypeEnum.findByValue(rs.getString("s.gameType")));
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
          searchResults.setHomeAway(StatsBean.HomeAwayEnum.findByValue(rs.getString("s.homeAway")));
          searchResults.setGameDateTime(rs.getTimestamp("s.gameDateTime"));
          searchResults.setMatchAbandoned(rs.getBoolean("s.matchAbandoned"));
          searchResults.setExtraTime(rs.getBoolean("s.extraTime"));
          searchResults.setGameComments(rs.getString("s.gameComments"));
          searchResults.setFlagImage(rs.getString("c.flagImage"));
          searchResults.setLogoImage(rs.getString("t.logoImage"));
          searchResults.setPlayerComments(rs.getString("p.playerComments"));


          if (StringUtils.isNullOrEmpty(myTeamName)) {
            TeamDao teamDao = new TeamDao();

            myTeamName = teamDao.getTeamName("ENG", searchResults.getMyTeamId());
            myLogoImage = teamDao.getTeamLogo("ENG", searchResults.getMyTeamId());
          }

          searchResults.setMyTeamName(myTeamName);
          searchResults.setMyLogoImage(myLogoImage);

          if (searchResults.getHomeAway().equals(StatsBean.HomeAwayEnum.Home)) {
            searchResults.setAwayTeamName(searchResults.getTeamName());
            searchResults.setHomeTeamName(myTeamName);
            searchResults.setHomeTeamLogo(myLogoImage);
            searchResults.setAwayTeamLogo(searchResults.getLogoImage());
          } else {
            searchResults.setHomeTeamName(searchResults.getTeamName());
            searchResults.setAwayTeamName(myTeamName);
            searchResults.setHomeTeamLogo(searchResults.getLogoImage());
            searchResults.setAwayTeamLogo(myLogoImage);
          }

          results.add(searchResults);
        }

        rs.close();
      }
    } catch (SQLException se) {

      System.err.println(se.getLocalizedMessage());
    } catch (Exception e) {

      System.err.println(e.getLocalizedMessage());
    } finally {
      if (conn != null)
        jdbcConnect.closeConnection(conn);
    }
    return results;
  }





  private ResultSet searchWithoutVersion(String teamName, String playerName, String homeAway, Date fromDate, Date toDate, String gameType, int division, StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName, PreparedStatement preparedStatement, String orderBy, String searchSelect, ResultSet rs) throws SQLException {
    String sql = String.valueOf(searchSelect) +
            getWhereClause(teamName, playerName, homeAway, fromDate, toDate, gameType, division, searchCriteria, possessionPercentage, matchAbandoned, extraTime,
                    countryName, false) + " " + orderBy;

    return preparedStatement.executeQuery(sql);
  }




















  private String getWhereClause(String teamName, String playerName, String homeAway, Date fromDate, Date toDate, String gameType, int division, StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName, boolean versionSearch) {
    String whereClause = " WHERE ";
    boolean andNeeded = false;

    if (!StringUtils.isNullOrEmpty(teamName)) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "t.teamName = '" + teamName + "' ";
      andNeeded = true;
    }

    if (!StringUtils.isNullOrEmpty(playerName)) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.playerName = '" + playerName + "' ";
      andNeeded = true;
    }

    if (!StringUtils.isNullOrEmpty(homeAway) && (homeAway.equalsIgnoreCase("H") || homeAway.equalsIgnoreCase("A"))) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.homeAway = '" + homeAway + "' ";
      andNeeded = true;
    }

    if (!StringUtils.isNullOrEmpty(gameType) && (
            gameType.equalsIgnoreCase("C") || gameType.equalsIgnoreCase("S") || gameType
                    .equalsIgnoreCase("F"))) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.gameType = '" + gameType + "' ";
      andNeeded = true;
    }

    if (division > 0 && gameType.equalsIgnoreCase("S")) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.division = " + division + " ";
      andNeeded = true;
    }

    if (possessionPercentage > 0) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.possessionPercentage " + searchCriteria.getLabel() + " " + possessionPercentage + " ";
      andNeeded = true;
    }

    if (extraTime) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.extraTime = '1' ";
      andNeeded = true;
    }

    if (matchAbandoned) {
      whereClause = andNeeded(whereClause, andNeeded);
      whereClause = String.valueOf(whereClause) + "s.matchAbandoned = '1' ";
      andNeeded = true;
    }

    if (fromDate != null) {
      whereClause = andNeeded(whereClause, andNeeded);
      String fromDateStr = formatDate(fromDate);
      fromDateStr = String.valueOf(fromDateStr) + " 00:00:00";
      whereClause = String.valueOf(whereClause) + "s.gameDateTime >= '" + fromDateStr + "' ";
      andNeeded = true;
    }

    if (toDate != null) {
      whereClause = andNeeded(whereClause, andNeeded);
      String toDateStr = formatDate(toDate);
      toDateStr = String.valueOf(toDateStr) + " 23:59:59";
      whereClause = String.valueOf(whereClause) + "s.gameDateTime <= '" + toDateStr + "' ";
      andNeeded = true;
    }
    if (!StringUtils.isNullOrEmpty(countryName)) {

      CountryDao countryDao = new CountryDao();
      String countryId = countryDao.getCountryId(countryName);
      if (!StringUtils.isNullOrEmpty(countryId)) {
        whereClause = andNeeded(whereClause, andNeeded);
        whereClause = String.valueOf(whereClause) + "s.countryId = '" + countryId + "' ";
        andNeeded = true;
      }
    }
    if (whereClause.equals(" WHERE ")) {
      whereClause = "";
    }

    if (versionSearch) {
      PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
      whereClause = String.valueOf(whereClause) + propertiesUtilities.addVersionWhere(!andNeeded);
    }

    if (whereClause.equals(" WHERE ") || whereClause.equals("WHERE ")) {
      whereClause = " ";
    }
    return whereClause;
  }







  private String andNeeded(String whereClause, boolean andNeeded) {
    if (andNeeded) {
      whereClause = String.valueOf(whereClause) + "AND ";
    }
    return whereClause;
  }

  private String formatDate(Date inputDate) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");



    return df.format(inputDate);
  }
}
