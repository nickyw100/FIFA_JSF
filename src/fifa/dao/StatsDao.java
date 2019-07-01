package fifa.dao;

import fifa.jsf.*;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatsDao implements fifa.utilities.FIFAConstants {
    private final String STATS_EDIT_SELECT = "sql.statsEditSelect";
    private final String foreignKeyException = "Cannot add or update a child row: a foreign key constraint fails";

    public StatsDao() {
    }

    public List<StatsBean> getStatsEdit() {
        List<StatsBean> statsEdit = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                int restrictRows = getRestrictedRows();

                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.statsEditSelect");

                if (StringUtils.isBlank(sql)) {
                    throw new IOException("SQL Select statement is null.");
                }

                if (restrictRows > 1) {
                    sql = sql + " LIMIT " + restrictRows + ";";
                } else {
                    sql = sql + ";";
                }

                preparedStatement = conn.prepareStatement(sql);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    StatsBean stats = new StatsBean();
                    loadStatsValues(rs, stats);
                    statsEdit.add(stats);
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
        return statsEdit;
    }

    private void loadStatsValues(ResultSet rs, StatsBean stats) throws SQLException {
        stats.setVersionId(rs.getString("versionId"));
        stats.setTeamId(rs.getString("teamId"));
        stats.setCountryId(rs.getString("countryId"));
        stats.setPlayerName(rs.getString("playerName"));
        stats.setDivision(rs.getInt("division"));
        stats.setGameType(StatsBean.GameTypeEnum.findByValue(rs.getString("gameType")));
        stats.setHomeAway(StatsBean.HomeAwayEnum.findByValue(rs.getString("homeAway")));
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

    private int getRestrictedRows() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        int restrictRows = 0;

        if (session.getAttribute("statsRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("statsRestrictRows")).intValue();
        }
        return restrictRows;
    }


    public List<LastSixBean> getLastSix(String competition) {
        List<LastSixBean> results = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();

            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                String last6Select = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.last6Select");
                String orderBy = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.last6OrderBy");

                String sql = last6Select + getWhereClause(competition, propertiesUtilities) + " " + orderBy;

                preparedStatement = conn.prepareStatement(sql);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    prepareLast6ResultDetails(results, rs);
                }
                rs.close();
            }
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (conn != null) {
                jdbcConnect.closeConnection(conn);
            }
        }
        if (results.size() < 6) {
            int y = 6 - results.size();
            LastSixBean lastSixBean = new LastSixBean();
            lastSixBean.setWinLossDraw("N");
            for (int x = 0; x < y; x++) {
                results.add(lastSixBean);
            }
        }

        return results;
    }


    public List<LastResultBean> getLastResult() {
        List<LastResultBean> results = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();

            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.lastResultSelect");

                sql = sql + propertiesUtilities.addVersionWhere(true);

                sql = sql + propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.lastResultOrderBy");

                preparedStatement = conn.prepareStatement(sql);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    prepareLastResultDetails(results, rs);
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


    private void prepareLast6ResultDetails(List<LastSixBean> results, ResultSet rs) {
        try {
        LastSixBean lastSixBean = new LastSixBean(rs.getString("s.versionId"), rs.getString("t.teamName"), rs.getString("s.playerName"),
                rs.getTimestamp("s.gameDateTime"), rs.getInt("s.goalsFor"), rs.getInt("s.goalsAgainst"),
                rs.getBoolean("s.extraTime"), rs.getInt("s.penaltiesFor"), rs.getInt("s.penaltiesAgainst"),
                rs.getInt("s.possessionPercentage"), 0,
                rs.getInt("s.shots"), rs.getInt("s.shotsOnTarget"), rs.getInt("s.opponentShots"),
                rs.getInt("s.opponentShotsOnTarget"),0, rs.getString("s.gameComments"));

        results.add(lastSixBean);
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
        }
    }


    private void prepareLastResultDetails(List<LastResultBean> results, ResultSet rs) {
        try {
            SearchResultsBean searchResultsBean = new SearchResultsBean(rs.getString("s.versionId"), rs.getString("s.myTeamId"), rs.getString("s.myTeamName"),
                    rs.getString("s.homeTeamName"), rs.getString("s.awayTeamName"), rs.getString("p.playerComments"),
                    StatsBean.GameTypeEnum.findByValue(rs.getString("s.gameType")), rs.getString("s.countryId"), rs.getString("s.countryName"),
                    rs.getTimestamp("s.gameDateTime"), StatsBean.HomeAwayEnum.findByValue(rs.getString("s.homeAway")),
                    rs.getInt("s.division"), rs.getBoolean("s.matchAbandoned"), rs.getString("t.logoImage"),
                    null, rs.getString("c.flagImage"), rs.getString("s.playerName"), rs.getInt("s.goalsFor"),rs.getInt("s.goalsAgainst"),
                    rs.getBoolean("s.extraTime"),
                    rs.getInt("s.penaltiesFor"), rs.getInt("s.penaltiesAgainst"), rs.getInt("s.possessionPercentage"),
                   0, rs.getInt("s.shots"), rs.getInt("s.shotsOnTarget"), rs.getInt("s.opponentShots"), rs.getInt("s.opponentShotsOnTarget"),
                    0, rs.getString("s.gameComments"), );

            LastResultBean lastResultBean = new LastResultBean(
                    searchResultsBean, null);


//            opponentDivision = rs.getInt("s.opponentDivision");

            loadLastResultBean(lastResultBean, goalsFor, goalsAgainst, penaltiesFor, penaltiesAgainst, possessionPercentage, shots,
                    shotsOnTarget, opponentShots, opponentShotsOnTarget, opponentDivision, division, teamName, playerName,
                    playerComments, gameComments, gameDateTime, myTeamName, myTeamId, myLogoImage, homeAway, gameType, logoImage,
                    flagImage, countryId, countryName, versionId, matchAbandoned, extraTime);
            results.add(lastResultBean);
        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
        }

    }

    private void loadLastResultBean(LastResultBean lastResultBean, int goalsFor, int goalsAgainst, int penaltiesFor, int penaltiesAgainst, int possessionPercentage, int shots, int shotsOnTarget, int opponentShots, int opponentShotsOnTarget, int opponentDivision, int division, String teamName, String playerName, String playerComments, String gameComments, Date gameDateTime, String myTeamName, String myTeamId, String myLogoImage, StatsBean.HomeAwayEnum homeAway, StatsBean.GameTypeEnum gameType, String logoImage, String flagImage, String countryId, String countryName, String versionId, boolean matchAbandoned, boolean extraTime) {


        if (StringUtils.isBlank(myTeamName)) {
            TeamDao teamDao = new TeamDao();

            myTeamName = teamDao.getTeamName(FIFAConstants.defaultCountry, lastResultBean.getMyTeamId());
            myLogoImage = teamDao.getTeamLogo(FIFAConstants.defaultCountry, lastResultBean.getMyTeamId());
        }

        lastResultBean.setMyTeamName(myTeamName);
        lastResultBean.setMyLogoImage(myLogoImage);


        if (lastResultBean.getHomeAway().equals(StatsBean.HomeAwayEnum.Home)) {
            lastResultBean.setAwayTeamName(lastResultBean.getTeamName());
            lastResultBean.setHomeTeamName(myTeamName);
            lastResultBean.setHomeTeamLogo(myLogoImage);
            lastResultBean.setAwayTeamLogo(lastResultBean.getLogoImage());
        } else {
            lastResultBean.setHomeTeamName(lastResultBean.getTeamName());
            lastResultBean.setAwayTeamName(myTeamName);
            lastResultBean.setHomeTeamLogo(lastResultBean.getLogoImage());
            lastResultBean.setAwayTeamLogo(myLogoImage);
        }
    }


    private String getWhereClause(String competition, PropertiesUtilities propertiesUtilities) {
        String whereClause = null;

        if (!competition.equalsIgnoreCase("A")) {
            whereClause = " WHERE s.gameType = '" + competition + "'";
            whereClause = whereClause + propertiesUtilities.addVersionWhere(false);
        } else {
            whereClause = propertiesUtilities.addVersionWhere(true);
        }

        return whereClause;
    }


    public void addStat(StatsBean statsBean) {
        TeamDao teamDao = new TeamDao();
        TeamBean teamBean = teamDao.getTeamByName(statsBean.getTeamName());
        if (teamBean != null) {
            statsBean.setCountryId(teamBean.getCountryId());
            statsBean.setTeamId(teamBean.getTeamId());
        }


        if ((statsBean.getPenaltiesAgainst() > 0) || (statsBean.getPenaltiesFor() > 0)) {
            statsBean.setExtraTime(true);
        }

        if (statsBean.getGameType().equals(StatsBean.GameTypeEnum.Friendly)) {
            statsBean.setOpponentDivision(0);
            statsBean.setDivision(0);
        } else if (statsBean.getGameType().equals(StatsBean.GameTypeEnum.Cup)) {

            statsBean.setDivision(0);
        }


        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.statInsert"));

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
                if (StringUtils.isBlank(statsBean.getGameComments())) {
                    statsBean.setGameComments(null);
                }

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
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (se.getMessage().startsWith("Duplicate entry")) {

                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Result already exists",
                        "Check to see if this result already exists"));
            } else if (se.getMessage().startsWith("Cannot add or update a child row: a foreign key constraint fails")) {

                if (se.getMessage().contains("teamId1-FK")) {
                    fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Can't find the team ID and/or country ID!", "Enter a valid Team ID and Country ID"));
                }
            } else {
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                        "Something terrible has happened!"));
            }

            System.err.println(se.getLocalizedMessage());
        }
    }

    public void updateStat(StatsBean statsBean) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                if (statsBean.getGameType().equals(StatsBean.GameTypeEnum.Friendly)) {
                    statsBean.setOpponentDivision(0);
                    statsBean.setDivision(0);
                } else if (statsBean.getGameType().equals(StatsBean.GameTypeEnum.Cup)) {
                    statsBean.setDivision(0);
                }


                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.statUpdate"));

                preparedStatement.setString(1, statsBean.getGameType().getValue());

                preparedStatement.setString(2, statsBean.getHomeAway().getValue());
                preparedStatement.setInt(3, statsBean.getDivision());
                if (StringUtils.isBlank(statsBean.getGameComments())) {
                    statsBean.setGameComments(null);
                }
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
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));

            System.err.println(se.getLocalizedMessage());
        }
    }

    public void deleteStat(StatsBean statsbean) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.statDelete"));

                preparedStatement.setString(1, statsbean.getVersionId());
                preparedStatement.setString(2, statsbean.getTeamId());
                preparedStatement.setString(3, statsbean.getCountryId());
                preparedStatement.setString(4, statsbean.getPlayerName());
                Timestamp sqlTimestamp = new Timestamp(statsbean.getGameDateTime().getTime());
                preparedStatement.setTimestamp(5, sqlTimestamp);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (se.getMessage().startsWith("Cannot delete")) {
                fc.addMessage(
                        "addTeamId",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete this row: " + se.getLocalizedMessage(), se
                                .getMessage()));
            } else {
                fc.addMessage("addTeamId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                        "Something terrible has happened!"));
            }
            System.err.println(se.getLocalizedMessage());
        }
    }
}
