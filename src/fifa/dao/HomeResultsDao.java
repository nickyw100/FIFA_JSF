// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeResultsDao.java

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

// Referenced classes of package fifa.dao:
//            OverallResultsDao

public class HomeResultsDao extends OverallResultsDao
{

    public HomeResultsDao()
    {
    }

    public List getHomeResultsList(String versionId, String gameType)
    {
        List results;
        Connection conn;
        JDBCConnect jdbcConnect;
        int wins;
        int draws;
        int losses;
        results = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        wins = 0;
        draws = 0;
        losses = 0;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        try {
        if(conn != null)
        {
            String sql = null;

            if((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) && (StringUtils.isEmpty(gameType) || StringUtils.equalsIgnoreCase(gameType, "A")))
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.homeSelect"));
            else
            if(StringUtils.isNotEmpty(versionId) && !versionId.equalsIgnoreCase("ALL") && StringUtils.isNotEmpty(gameType) && StringUtils.equalsIgnoreCase(gameType, "A"))
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.homeSelect2");
                emptySQLCheck(propertiesUtilities, sql, "sql.homeSelect2");
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, versionId);
            } else
            if((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) && StringUtils.isNotEmpty(gameType) && !StringUtils.equalsIgnoreCase(gameType, "A"))
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.homeSelect4");
                emptySQLCheck(propertiesUtilities, sql, "sql.homeSelect4");
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, gameType);
            } else
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.homeSelect3");
                emptySQLCheck(propertiesUtilities, sql, "sql.homeSelect3");
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, versionId);
                preparedStatement.setString(2, gameType);
            }
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next();)
            {
                int goalsFor = rs.getInt("goalsFor");
                int goalsAgainst = rs.getInt("goalsAgainst");
                int penaltiesFor = rs.getInt("penaltiesFor");
                int penaltiesAgainst = rs.getInt("penaltiesAgainst");
                if(goalsFor > goalsAgainst)
                    wins++;
                else
                if(goalsFor < goalsAgainst)
                    losses++;
                else
                if(penaltiesFor > penaltiesAgainst)
                    wins++;
                else
                if(penaltiesFor < penaltiesAgainst)
                    losses++;
                else
                    draws++;
            }

            rs.close();
        } } catch (SQLException se) {
            ;
            System.err.println(se.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        } catch (Exception e) {
            ;
            System.err.println(e.getLocalizedMessage());
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }

        if(conn != null)
            jdbcConnect.closeConnection(conn);
        results.add(Integer.valueOf(wins));
        results.add(Integer.valueOf(draws));
        results.add(Integer.valueOf(losses));
        return results;
    }

    private void emptySQLCheck(PropertiesUtilities propertiesUtilities, String sql, String message)
    {
        if(StringUtils.isEmpty(sql))
            System.err.println((new StringBuilder("SELECT statement not found, in the resource ")).append(propertiesUtilities.getMessageResource()).append(" for the property ").append(message).toString());
    }

    private final String SELECT_HOME = "sql.homeSelect";
    private final String SELECT_HOME2 = "sql.homeSelect2";
    private final String SELECT_HOME3 = "sql.homeSelect3";
    private final String SELECT_HOME4 = "sql.homeSelect4";
}
