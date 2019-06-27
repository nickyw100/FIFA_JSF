// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JDBCConnect.java

package fifa.utilities;

import java.io.PrintStream;
import java.sql.*;

// Referenced classes of package fifa.utilities:
//            FIFAConstants, PropertiesUtilities

public class JDBCConnect
    implements FIFAConstants
{

    public JDBCConnect()
    {
    }

    public Connection getConnection()
    {
        Connection conn = null;
        PropertiesUtilities propertiesUtilities = null;
        try
        {
            propertiesUtilities = PropertiesUtilities.getInstance();
            String userName = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), "user");
            String password = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), "password");
            String urlKey = "localhostDbURL";
            urlKey = getDevelopmentUrlKey(propertiesUtilities, urlKey);
            String url = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), urlKey);
            String driver = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), "driver");
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
        }
        catch(Exception e)
        {
            System.err.println("Cannot connect to database server");
            System.err.println((new StringBuilder("Working Directory = ")).append(System.getProperty("user.dir")).toString());
            if(propertiesUtilities != null)
                System.err.println((new StringBuilder("Looking for Database resources at ")).append(propertiesUtilities.getDatabaseResource()).toString());
        }
        return conn;
    }

    private String getDevelopmentUrlKey(PropertiesUtilities propertiesUtilities, String urlKey)
    {
        if(propertiesUtilities.getHostName().equalsIgnoreCase("nwilsonvu1"))
            urlKey = "localhostDbURL";
        else
        if(propertiesUtilities.getHostName().equalsIgnoreCase("Ubuntu1404VirtualBox") || propertiesUtilities.getHostName().equalsIgnoreCase("Ubuntu1404VirtualBox"))
        {
            String env = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), "environment");
            if(env.equalsIgnoreCase("prod"))
                urlKey = "prodDbURL";
            else
            if(env.equalsIgnoreCase("qa"))
                urlKey = "qaDbURL";
            else
            if(env.equalsIgnoreCase("devServer"))
                urlKey = "devDbURL";
            else
                urlKey = "localhostDbURL";
        }
        return urlKey;
    }

    public ResultSet executeQuery(Connection conn, PreparedStatement preparedStatement)
        throws SQLException
    {
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public void closeConnection(Connection conn)
    {
        try
        {
            conn.close();
        }
        catch(Exception e)
        {
            System.err.println((new StringBuilder("Error terminating Database connection ")).append(e.getMessage()).toString());
        }
    }
}
