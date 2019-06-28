package fifa.utilities;

import java.math.BigDecimal;

  public interface FIFAConstants
{
          String newLine = System.getProperty("line.separator");
          BigDecimal BIG_DECIMAL_ONE_HUNDRED = new BigDecimal(100);
          String defaultVersion = "defaultVersion";
          String latestVersion = "latestVersion";
          String earliestVersion = "FIFA12";
          String homePage = "index.jsf?faces-redirect=true";
          String terribleMessage = "Something terrible has happened!";
          String errorMessage = "An error occurred! Check the logs!";
          String nullSQL = "SQL Select statement is null.";
          String productionEnvironment = "prod";
          String qaEnvironment = "qa";
          String devServerEnvironment = "devServer";
          String productionServer = "SERVER2012";
          String workDevPC = "nwilsonvu1";
          String homeDevPC = "Ubuntu1404VirtualBox";
          String homeDevVBox = "Ubuntu1404VirtualBox";
          String Cup = "C";
          String Friendly = "F";
          String Season = "S";
          String All = "A";
          String Home = "H";
          String Away = "A";
          String countryTemplate = "editCountry_template.jsf";
          String playerTemplate = "editPlayer_template.jsf";
          String teamTemplate = "editTeam_template.jsf";
          String statsTemplate = "editStats_template.jsf";
          String playersRestrictRows = "playersRestrictRows";
          String countriesRestrictRows = "countriesRestrictRows";
          String statsRestrictRows = "statsRestrictRows";
          String teamsRestrictRows = "teamsRestrictRows";
          String lastPlayerName = "lastPlayerName";
          String defaultCountry = "ENG";
          String productionPath = "../webapps/FIFA_JSF/WEB-INF/classes/resources/";
          String devWorkPath = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/";
          String devHomePath = "/home/nick/workspace/FIFA_JSF/build/classes/resources/";
          String factResources = "factresources.properties";
          String messageResources = "messageresources.properties";
          String databaseResources = "database.properties";
          String productionFactResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/factresources.properties";
          String devWorkFactResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/factresources.properties";
          String devHomeFactResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/factresources.properties";
          String productionMessageResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/messageresources.properties";
          String devWorkMessageResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/messageresources.properties";
          String devHomeMessageResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/messageresources.properties";
          String productionDatabaseResource = "../webapps/FIFA_JSF/WEB-INF/classes/resources/database.properties";
          String devWorkDatabaseResource = "/home/nwilson/Documents/FIFA_JSF/build/classes/resources/database.properties";
          String devHomeDatabaseResource = "/home/nick/workspace/FIFA_JSF/build/classes/resources/database.properties";
}
