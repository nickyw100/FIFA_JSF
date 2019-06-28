package fifa.utilities;

import java.sql.*;

public class JDBCConnect
        implements FIFAConstants {
    public Connection getConnection() {
        Connection conn = null;
        PropertiesUtilities propertiesUtilities = null;

        try {
            propertiesUtilities = PropertiesUtilities.getInstance();

            String userName = propertiesUtilities.getProperty(
                    propertiesUtilities.getDatabaseResource(), "user");
            String password = propertiesUtilities.getProperty(
                    propertiesUtilities.getDatabaseResource(), "password");

            String urlKey = "prodDbURL";

            urlKey = getDevelopmentUrlKey(propertiesUtilities, urlKey);

            String url = propertiesUtilities.getProperty(
                    propertiesUtilities.getDatabaseResource(), urlKey);
            String driver = propertiesUtilities.getProperty(
                    propertiesUtilities.getDatabaseResource(), "driver");
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            System.err.println("Cannot connect to database server");
            System.err.println("Working Directory = " +
                    System.getProperty("user.dir"));
            if (propertiesUtilities != null) {
                System.err.println("Looking for Database resources at " +
                        propertiesUtilities.getDatabaseResource());
            }
        }

        return conn;
    }

    private String getDevelopmentUrlKey(PropertiesUtilities propertiesUtilities, String urlKey) {
        if (propertiesUtilities.getHostName().equalsIgnoreCase("Win10x64Dev")) {
            urlKey = "localhostDbURL";
        } else {
            String env = propertiesUtilities.getProperty(propertiesUtilities.getDatabaseResource(), "environment");
            if (env.equalsIgnoreCase("prod")) {
                urlKey = "prodDbURL";
            } else if (env.equalsIgnoreCase("qa")) {
                urlKey = "qaDbURL";
            } else if (env.equalsIgnoreCase("devServer")) {
                urlKey = "devDbURL";
            } else {
                urlKey = "localhostDbURL";
            }
        }
        return urlKey;
    }


    public ResultSet executeQuery(Connection conn, PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println("Error terminating Database connection " +
                    e.getMessage());
        }
    }
}
