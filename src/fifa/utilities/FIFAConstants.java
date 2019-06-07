// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FIFAConstants.java

package fifa.utilities;

import java.math.BigDecimal;

public interface FIFAConstants
{

    public static final String newLine = System.getProperty("line.separator");
    public static final BigDecimal BIG_DECIMAL_ONE_HUNDRED = new BigDecimal(100);
    public static final String defaultVersion = "defaultVersion";
    public static final String latestVersion = "latestVersion";
    public static final String earliestVersion = "FIFA12";
    public static final String homePage = "index.jsf?faces-redirect=true";
    public static final String terribleMessage = "Something terrible has happened!";
    public static final String errorMessage = "An error occurred! Check the logs!";
    public static final String nullSQL = "SQL Select statement is null.";
    public static final String productionEnvironment = "prod";
    public static final String qaEnvironment = "qa";
    public static final String devServerEnvironment = "devServer";
    public static final String productionServer = "Win10ProX64Dev";
    public static final String homeDevPC = "Ubuntu1404VirtualBox";
    public static final String homeDevVBox = "Ubuntu1404VirtualBox";
    public static final String Cup = "C";
    public static final String Friendly = "F";
    public static final String Season = "S";
    public static final String All = "A";
    public static final String Home = "H";
    public static final String Away = "A";
    public static final String countryTemplate = "editCountry_template.jsf";
    public static final String playerTemplate = "editPlayer_template.jsf";
    public static final String teamTemplate = "editTeam_template.jsf";
    public static final String statsTemplate = "editStats_template.jsf";
    public static final String playersRestrictRows = "playersRestrictRows";
    public static final String countriesRestrictRows = "countriesRestrictRows";
    public static final String statsRestrictRows = "statsRestrictRows";
    public static final String teamsRestrictRows = "teamsRestrictRows";
    public static final String lastPlayerName = "lastPlayerName";
    public static final String defaultCountry = "ENG";
    public static final String productionPath = "../webapps/FIFA_JSF/WEB-INF/classes/resources/";
    public static final String devWorkPath = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/";
    public static final String devHomePath = "/home/nick/workspace/FIFA_JSF/build/classes/resources/";
    public static final String factResources = "factresources.properties";
    public static final String messageResources = "messageresources.properties";
    public static final String databaseResources = "database.properties";
    public static final String productionFactResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/factresources.properties";
    public static final String devWorkFactResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/factresources.properties";
    public static final String devHomeFactResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/factresources.properties";
    public static final String productionMessageResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/messageresources.properties";
    public static final String devWorkMessageResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/messageresources.properties";
    public static final String devHomeMessageResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/messageresources.properties";
    public static final String productionDatabaseResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/database.properties";
    public static final String devWorkDatabaseResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/database.properties";
    public static final String devHomeDatabaseResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/database.properties";

}
