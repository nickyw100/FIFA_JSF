package fifa.dao;

import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AwayResultsDao
        extends OverallResultsDao {
    private final String SELECT_AWAY = "sql.awaySelect";
    private final String SELECT_AWAY2 = "sql.awaySelect2";
    private final String SELECT_AWAY3 = "sql.awaySelect3";
    private final String SELECT_AWAY4 = "sql.awaySelect4";

    public List<Integer> getAwayResultsList(String versionId, String gameType) {
        List<Integer> results = new ArrayList<Integer>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        int wins = 0, draws = 0, losses = 0;

        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();

            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

            if (conn != null) {
                String sql = null;


                if ((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) && (StringUtils.isEmpty(gameType) ||
                        StringUtils.equalsIgnoreCase(gameType, "A"))) {
                    preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                            "sql.awaySelect"));
                } else if (StringUtils.isNotEmpty(versionId) && !versionId.equalsIgnoreCase("ALL") &&
                        StringUtils.isNotEmpty(gameType) && StringUtils.equalsIgnoreCase(gameType, "A")) {

                    sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect2");
                    emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect2");
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, versionId);
                } else if ((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) &&
                        StringUtils.isNotEmpty(gameType) &&
                        !StringUtils.equalsIgnoreCase(gameType, "A")) {

                    sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect4");
                    emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect4");
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, gameType);
                } else {
                    sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect3");
                    emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect3");
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, versionId);
                    preparedStatement.setString(2, gameType);
                }


                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    int goalsFor = rs.getInt("goalsFor");
                    int goalsAgainst = rs.getInt("goalsAgainst");
                    int penaltiesFor = rs.getInt("penaltiesFor");
                    int penaltiesAgainst = rs.getInt("penaltiesAgainst");

                    if (goalsFor > goalsAgainst) {
                        wins++;
                        continue;
                    }
                    if (goalsFor < goalsAgainst) {
                        losses++;
                        continue;
                    }
                    if (penaltiesFor > penaltiesAgainst) {
                        wins++;
                        continue;
                    }
                    if (penaltiesFor < penaltiesAgainst) {
                        losses++;
                        continue;
                    }
                    draws++;
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
        results.add(Integer.valueOf(wins));
        results.add(Integer.valueOf(draws));
        results.add(Integer.valueOf(losses));
        return results;
    }

    private void emptySQLCheck(PropertiesUtilities propertiesUtilities, String sql, String message) {
        if (StringUtils.isEmpty(sql)) {
            System.err.println("SELECT statement not found, in the resource " + propertiesUtilities.getMessageResource() +
                    " for the property " + message);
        }
    }
}
