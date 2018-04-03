// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertiesUtilities.java

package fifa.utilities;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.VersionBean;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

// Referenced classes of package fifa.utilities:
//            FIFAConstants

public class PropertiesUtilities
    implements FIFAConstants
{

    protected PropertiesUtilities()
    {
    }

    public static PropertiesUtilities getInstance()
    {
        if(instance == null)
        {
            instance = new PropertiesUtilities();
            autoBuildFacts();
        }
        return instance;
    }

    public String getProperty(String propsFile, String propertyKey)
    {
        PropertiesConfiguration config = null;
        String propertyValue = null;
        Object propertyValueObject = null;
        ArrayList propertyValueList = null;
        try
        {
            config = new PropertiesConfiguration(propsFile);
            propertyValueObject = config.getProperty(propertyKey);
            if(propertyValueObject != null)
                if(propertyValueObject instanceof ArrayList)
                {
                    propertyValueList = (ArrayList)config.getProperty(propertyKey);
                    for(int x = 0; x <= propertyValueList.size() - 1; x++)
                        if(x == 0)
                        {
                            propertyValue = (String)propertyValueList.get(x);
                            propertyValue = (new StringBuilder(String.valueOf(propertyValue))).append(", ").toString();
                        } else
                        {
                            propertyValue = (new StringBuilder(String.valueOf(propertyValue))).append((String)propertyValueList.get(x)).toString();
                            if(x < propertyValueList.size() - 1)
                                propertyValue = (new StringBuilder(String.valueOf(propertyValue))).append(", ").toString();
                        }

                } else
                {
                    propertyValue = (String)propertyValueObject;
                }
        }
        catch(ConfigurationException e)
        {
            System.err.println((new StringBuilder("Error occured getting property ")).append(propertyKey).append(" from file ").append(propsFile).append(". ").append(e.getLocalizedMessage()).toString());
            System.err.println(e.getLocalizedMessage());
        }
        return propertyValue;
    }

    public void setMessageProperty(String propsFile, String propertyKey, String propertyValue)
    {
        PropertiesConfiguration config = null;
        try
        {
            config = new PropertiesConfiguration(propsFile);
            config.setProperty(propertyKey, propertyValue);
            config.save();
        }
        catch(ConfigurationException e)
        {
            System.err.println((new StringBuilder("Error writing to properties file ")).append(propsFile).append(" with key ").append(propertyKey).toString());
            System.err.println(e.getLocalizedMessage());
        }
    }

    public String addVersionWhere(boolean addWhere)
    {
        String messageResources = getMessageResource();
        String whereClause = " ";
        String versionId = getProperty(messageResources, "defaultVersion");
        if(!StringUtils.isNullOrEmpty(versionId) && versionId.startsWith("FIFA"))
        {
            if(addWhere)
                whereClause = " WHERE ";
            else
                whereClause = " AND ";
            whereClause = (new StringBuilder(String.valueOf(whereClause))).append("s.versionId = '").append(versionId).append("' ").toString();
        }
        return whereClause;
    }

    public String getMessageResource()
    {
        String messageResource = null;
        String hostName = getHostName();
        if(hostName.equalsIgnoreCase("SERVER2012"))
            messageResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/messageresources.properties";
        else
        if(hostName.equalsIgnoreCase("nwilsonvu1"))
            messageResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/messageresources.properties";
        else
            messageResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/messageresources.properties";
        return messageResource;
    }

    public String getFactResource()
    {
        String factResource = null;
        String hostName = getHostName();
        if(hostName.equalsIgnoreCase("SERVER2012"))
            factResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/factresources.properties";
        else
        if(hostName.equalsIgnoreCase("nwilsonvu1"))
            factResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/factresources.properties";
        else
            factResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/factresources.properties";
        return factResource;
    }

    public String getDatabaseResource()
    {
        String hostName = getHostName();
        String databaseResource;
        if(hostName.equalsIgnoreCase("SERVER2012"))
            databaseResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/database.properties";
        else
        if(hostName.equalsIgnoreCase("nwilsonvu1"))
            databaseResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/database.properties";
        else
            databaseResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/database.properties";
        return databaseResource;
    }

    String getHostName()
    {
        String hostName = null;
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch(UnknownHostException e)
        {
            System.err.println((new StringBuilder("Hostname not found: ")).append(e.getLocalizedMessage()).toString());
            System.err.println(e.getLocalizedMessage());
        }
        return hostName;
    }

    public String getDatabaseEnvironment()
    {
        String hostName = getHostName();
        String dbEnv = "";
        if(!hostName.equalsIgnoreCase("SERVER2012"))
            dbEnv = getProperty(getDatabaseResource(), "environment");
        return dbEnv.toUpperCase();
    }

    private static void autoBuildFacts()
    {
        int delay = 5000;
        int interval = 0xdbba0;
        Timer timer = new Timer();
        FacesContext context = FacesContext.getCurrentInstance();
        final VersionBean versionBean = (VersionBean)context.getApplication().evaluateExpressionGet(context, "#{versionBean}", fifa/jsf/VersionBean);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run()
            {
                versionBean.buildAllFacts(false);
            }

            private final VersionBean val$versionBean;

            
            {
                versionBean = versionbean;
                super();
            }
        }
, delay, interval);
    }

    private static PropertiesUtilities instance = null;

}
