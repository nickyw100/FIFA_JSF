package fifa.dao;

import fifa.jsf.SearchResultsBean;
import fifa.jsf.StatsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

public class SearchDao
        implements FIFAConstants {
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
                if (StringUtils.isBlank(searchSelect)) {
                    throw new IOException("SQL Select statement is null.");
                }


                String sql = searchSelect +
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

                    SearchResultsBean searchResults = new SearchResultsBean(rs.getString("s.versionId"), rs.getString("s.myTeamId"), rs.getString("t.teamName"),
                             rs.getString("p.playerComments"), StatsBean.GameTypeEnum.findByValue(rs.getString("s.gameType")),
                            rs.getString("s.countryId"), rs.getTimestamp("s.gameDateTime"),
                            StatsBean.HomeAwayEnum.findByValue(rs.getString("s.homeAway")), rs.getInt("s.division"), rs.getBoolean("s.matchAbandoned"),
                            rs.getString("t.logoImage"), null, rs.getString("c.flagImage"), rs.getString("s.playerName"),
                            rs.getInt("s.goalsFor"), rs.getInt("s.goalsAgainst"), rs.getBoolean("s.extraTime"), rs.getInt("s.penaltiesFor"),
                            rs.getInt("s.penaltiesAgainst"), rs.getInt("s.possessionPercentage"), 0, rs.getInt("s.shots"),
                            rs.getInt("s.shotsOnTarget"), rs.getInt("s.opponentShots"), rs.getInt("s.opponentShotsOnTarget"),
                            rs.getInt("s.opponentDivision"), rs.getString("s.gameComments"));


                    if (StringUtils.isBlank(myTeamName)) {
                        TeamDao teamDao = new TeamDao();

                        myTeamName = teamDao.getTeamName(FIFAConstants.defaultCountry, searchResults.getMyTeamId());
                        myLogoImage = teamDao.getTeamLogo(FIFAConstants.defaultCountry, searchResults.getMyTeamId());

                        searchResults.setMyTeamName(myTeamName);
                        searchResults.setMyLogoImage(myLogoImage);
                    }

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
        String sql = searchSelect +
                getWhereClause(teamName, playerName, homeAway, fromDate, toDate, gameType, division, searchCriteria, possessionPercentage, matchAbandoned, extraTime,
                        countryName, false) + " " + orderBy;

        return preparedStatement.executeQuery(sql);
    }

    private String getWhereClause(String teamName, String playerName, String homeAway, Date fromDate, Date toDate, String gameType, int division, StatsBean.SearchCriteriaEnum searchCriteria, int possessionPercentage, boolean matchAbandoned, boolean extraTime, String countryName, boolean versionSearch) {
        String whereClause = " WHERE ";
        boolean andNeeded = false;

        if (!StringUtils.isBlank(teamName)) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "t.teamName = '" + teamName + "' ";
            andNeeded = true;
        }

        if (!StringUtils.isBlank(playerName)) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.playerName = '" + playerName + "' ";
            andNeeded = true;
        }

        if (!StringUtils.isBlank(homeAway) && (homeAway.equalsIgnoreCase("H") || homeAway.equalsIgnoreCase("A"))) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.homeAway = '" + homeAway + "' ";
            andNeeded = true;
        }

        if (!StringUtils.isBlank(gameType) && (
                gameType.equalsIgnoreCase("C") || gameType.equalsIgnoreCase("S") || gameType
                        .equalsIgnoreCase("F"))) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.gameType = '" + gameType + "' ";
            andNeeded = true;
        }

        if (division > 0 && gameType.equalsIgnoreCase("S")) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.division = " + division + " ";
            andNeeded = true;
        }

        if (possessionPercentage > 0) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.possessionPercentage " + searchCriteria.getLabel() + " " + possessionPercentage + " ";
            andNeeded = true;
        }

        if (extraTime) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.extraTime = '1' ";
            andNeeded = true;
        }

        if (matchAbandoned) {
            whereClause = andNeeded(whereClause, andNeeded);
            whereClause = whereClause + "s.matchAbandoned = '1' ";
            andNeeded = true;
        }

        if (fromDate != null) {
            whereClause = andNeeded(whereClause, andNeeded);
            String fromDateStr = formatDate(fromDate);
            fromDateStr = fromDateStr + " 00:00:00";
            whereClause = whereClause + "s.gameDateTime >= '" + fromDateStr + "' ";
            andNeeded = true;
        }

        if (toDate != null) {
            whereClause = andNeeded(whereClause, andNeeded);
            String toDateStr = formatDate(toDate);
            toDateStr = toDateStr + " 23:59:59";
            whereClause = whereClause + "s.gameDateTime <= '" + toDateStr + "' ";
            andNeeded = true;
        }
        if (!StringUtils.isBlank(countryName)) {

            CountryDao countryDao = new CountryDao();
            String countryId = countryDao.getCountryId(countryName);
            if (!StringUtils.isBlank(countryId)) {
                whereClause = andNeeded(whereClause, andNeeded);
                whereClause = whereClause + "s.countryId = '" + countryId + "' ";
                andNeeded = true;
            }
        }
        if (whereClause.equals(" WHERE ")) {
            whereClause = "";
        }

        if (versionSearch) {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            whereClause = whereClause + propertiesUtilities.addVersionWhere(!andNeeded);
        }

        if (whereClause.equals(" WHERE ") || whereClause.equals("WHERE ")) {
            whereClause = " ";
        }
        return whereClause;
    }


    private String andNeeded(String whereClause, boolean andNeeded) {
        if (andNeeded) {
            whereClause = whereClause + "AND ";
        }
        return whereClause;
    }

    private String formatDate(Date inputDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(inputDate);
    }
}
