package fifa.utilities;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class PropertiesUtilities
        implements FIFAConstants {
    private static PropertiesUtilities instance = null;


    public static PropertiesUtilities getInstance() {
        if (instance == null) {
            instance = new PropertiesUtilities();
            autoBuildFacts();
        }
        return instance;
    }

    private static void autoBuildFacts() {
//        int delay = 5000;
//        int interval = 900000;
//        Timer timer = new Timer();
//        FacesContext context = FacesContext.getCurrentInstance();
//        final VersionBean versionBean = context.getApplication().evaluateExpressionGet(context, "#{versionBean}", VersionBean.class);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                versionBean.buildAllFacts(false);
//            }
//        }, delay, interval);
    }

    public String getProperty(String propsFile, String propertyKey) {
        PropertiesConfiguration config = null;
        String propertyValue = null;
        Object propertyValueObject = null;
        ArrayList<String> propertyValueList = null;
        try {
            config = new PropertiesConfiguration(propsFile);
            propertyValueObject = config.getProperty(propertyKey);

            if (propertyValueObject != null) {
                if (propertyValueObject instanceof ArrayList) {
                    propertyValueList = (ArrayList) config.getProperty(propertyKey);
                    for (int x = 0; x <= propertyValueList.size() - 1; x++) {
                        if (x == 0) {
                            propertyValue = propertyValueList.get(x);
                            propertyValue = propertyValue + ", ";
                        } else {
                            propertyValue = propertyValue + propertyValueList.get(x);
                            if (x < propertyValueList.size() - 1) {
                                propertyValue = propertyValue + ", ";
                            }
                        }
                    }
                } else {

                    propertyValue = (String) propertyValueObject;
                }

            }
        } catch (ConfigurationException e) {
            System.err.println("Error occurred getting property " + propertyKey + " from file " + propsFile + ". " +
                    e.getLocalizedMessage());
            System.err.println(e.getLocalizedMessage());
        }

        return propertyValue;
    }

    public void setMessageProperty(String propsFile, String propertyKey, String propertyValue) {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(propsFile);
            config.setProperty(propertyKey, propertyValue);
            config.save();
        } catch (ConfigurationException e) {
            System.err.println("Error writing to properties file " + propsFile + " with key " + propertyKey);
            System.err.println(e.getLocalizedMessage());
        }
    }

    public String addVersionWhere(boolean addWhere) {
        String messageResources = getMessageResource();

        String whereClause = " ";
        String versionId = getProperty(messageResources, "defaultVersion");

        if (!StringUtils.isBlank(versionId) && versionId.startsWith("FIFA")) {
            if (addWhere) {
                whereClause = " WHERE ";
            } else {
                whereClause = " AND ";
            }
            whereClause = whereClause + "s.versionId = '" + versionId + "' ";
        }
        return whereClause;
    }

    public String getMessageResource() {
        String messageResource = null;
        String hostName = getHostName();
        if (hostName.equalsIgnoreCase("Win10x64Dev")) {
            messageResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/messageresources.properties";
        } else {
            messageResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/messageresources.properties";
        }

        return messageResource;
    }

    public String getFactResource() {
        String factResource = null;
        String hostName = getHostName();
        if (hostName.equalsIgnoreCase("Win10x64Dev")) {
            factResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/factresources.properties";
        } else {
            factResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/factresources.properties";
        }

        return factResource;
    }

    public String getDatabaseResource() {
        String databaseResource, hostName = getHostName();
        if (hostName.equalsIgnoreCase("Win10x64Dev")) {
            databaseResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/database.properties";
        } else {
            databaseResource = "C:/Users/Nick/FIFA_JSF/WEB-INF/classes/resources/database.properties";
        }


        return databaseResource;
    }

    String getHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.err.println("Hostname not found: " + e.getLocalizedMessage());
            System.err.println(e.getLocalizedMessage());
        }
        return hostName;
    }

    public String getDatabaseEnvironment() {
        String hostName = getHostName();
        String dbEnv = "";

        if (!hostName.equalsIgnoreCase("Win10x64Dev")) {
            dbEnv = getProperty(getDatabaseResource(), "environment");
        }

        return dbEnv.toUpperCase();
    }
}
