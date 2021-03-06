// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AwayResultsDao.java

package fifa.dao;

import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

// Referenced classes of package fifa.dao:
//            OverallResultsDao

public class AwayResultsDao extends OverallResultsDao
{

    public AwayResultsDao()
    {
    }

    public List getAwayResultsList(String versionId, String gameType)
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
        if(conn != null)
        {
            String sql = null;
            PreparedStatement preparedStatement;
            if((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) && (StringUtils.isEmpty(gameType) || StringUtils.equalsIgnoreCase(gameType, "A")))
                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect"));
            else
            if(StringUtils.isNotEmpty(versionId) && !versionId.equalsIgnoreCase("ALL") && StringUtils.isNotEmpty(gameType) && StringUtils.equalsIgnoreCase(gameType, "A"))
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect2");
                emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect2");
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, versionId);
            } else
            if((StringUtils.isEmpty(versionId) || versionId.equalsIgnoreCase("ALL")) && StringUtils.isNotEmpty(gameType) && !StringUtils.equalsIgnoreCase(gameType, "A"))
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect4");
                emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect4");
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, gameType);
            } else
            {
                sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.awaySelect3");
                emptySQLCheck(propertiesUtilities, sql, "sql.awaySelect3");
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
        }
        break MISSING_BLOCK_LABEL_526;
        SQLException se;
        se;
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_538;
        Exception e;
        e;
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_538;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
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

    private final String SELECT_AWAY = "sql.awaySelect";
    private final String SELECT_AWAY2 = "sql.awaySelect2";
    private final String SELECT_AWAY3 = "sql.awaySelect3";
    private final String SELECT_AWAY4 = "sql.awaySelect4";
}
