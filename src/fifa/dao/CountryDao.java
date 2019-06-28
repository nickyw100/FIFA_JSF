package fifa.dao;

import fifa.jsf.CountryBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.JDBCConnect;
import fifa.utilities.PropertiesUtilities;
import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDao
        implements FIFAConstants {
    private final String SELECT_COUNTRY = "sql.countrySelect";
    private final String SELECT_COUNTRY_NAME = "sql.countryNameSelect";
    private final String SELECT_COUNTRY_ID = "sql.countryIdSelect";
    private final String SELECT_COUNTRY_INSERT = "sql.countryInsert";
    private final String SELECT_COUNTRY_UPDATE = "sql.countryUpdate";
    private final String SELECT_COUNTRY_DELETE = "sql.countryDelete";
    private UIComponent addCountryId;

    public CountryDao() {
    }

    public List<CountryBean> getCountriesEdit() {
        List<CountryBean> countriesEdit = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                int restrictRows = getRestrictedRows();

                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();


                String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countrySelect");

                if (StringUtils.isBlank(sql)) {
                    throw new IOException(
                            "SQL Select statement is null.");
                }

                if (restrictRows > 1) {
                    sql = sql + " LIMIT " + restrictRows + ";";
                } else {
                    sql = sql + ";";
                }


                preparedStatement = conn.prepareStatement(sql);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    CountryBean country = new CountryBean();
                    country.setCountryId(rs.getString("countryId"));
                    country.setCountryName(rs.getString("countryName"));
                    country.setFlagImage(rs.getString("flagImage"));
                    country.setCountryComments(rs.getString("countryComments"));
                    countriesEdit.add(country);
                }

                rs.close();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }
        return countriesEdit;
    }


    public String getCountryName(String countryId) {
        String countryName = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();

            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.countryName2Select"));

                preparedStatement.setString(1, countryId);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    countryName = rs.getString("countryName");
                }
                rs.close();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }
        return countryName;
    }

    private int getRestrictedRows() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        int restrictRows = 0;

        if (session.getAttribute("countriesRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("countriesRestrictRows")).intValue();
        }
        return restrictRows;
    }


    public List<String> getCountryNames() {
        List<String> countries = new ArrayList();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn = jdbcConnect.getConnection();
            if (conn != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.countryNameSelect"));

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    countries.add(rs.getString("countryName"));
                }
                rs.close();
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (conn != null)
                jdbcConnect.closeConnection(conn);
        }
        return countries;
    }


    public String getCountryId(String countryName) {
        String countryId = null;
        Connection conn1 = null;
        JDBCConnect jdbcConnect = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                String preparedSql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryIdSelect");
                if (StringUtils.isBlank(preparedSql)) {
                    throw new IOException("SQL Select statement is null.");
                }
                PreparedStatement preparedStatement = conn1.prepareStatement(preparedSql);

                preparedStatement.setString(1, countryName);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    countryId = rs.getString("countryId");
                }
                rs.close();
            } else {
                throw new IOException(
                        "Connection is null.");
            }
        } catch (SQLException se) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(se.getLocalizedMessage());
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!"));
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (conn1 != null) {
                jdbcConnect.closeConnection(conn1);
            }
        }
        if (conn1 != null) {
            jdbcConnect.closeConnection(conn1);
        }
        return countryId;
    }


    public FacesMessage addCountry(String countryId, String countryName, String countryComments, String flagImage) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.countryInsert"));

                preparedStatement.setString(1, countryId);
                preparedStatement.setString(2, countryName);
                if (StringUtils.isBlank(countryComments)) {
                    countryComments = null;
                }
                preparedStatement.setString(3, countryComments);
                preparedStatement.setString(4, flagImage);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException se) {
            if (se.getMessage().startsWith("Duplicate entry")) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Country ID already exists", "Check to see if this country already exists");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                        "Something terrible has happened!");
                System.err.println(se.getLocalizedMessage());
            }
        }
        return message;
    }


    public FacesMessage updateCountry(String countryId, String countryName, String countryComments, String flagImage) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;
        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.countryUpdate"));

                preparedStatement.setString(1, countryName);
                if (StringUtils.isBlank(countryComments)) {
                    countryComments = null;
                }
                preparedStatement.setString(2, countryComments);
                preparedStatement.setString(3, flagImage);
                preparedStatement.setString(4, countryId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException se) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                    "Something terrible has happened!");
            System.err.println(se.getLocalizedMessage());
        }
        return message;
    }


    public FacesMessage deleteCountry(String countryId) {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;

        try {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();

            if (conn1 != null) {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();

                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                        "sql.countryDelete"));

                preparedStatement.setString(1, countryId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException se) {
            if (se.getMessage().startsWith("Cannot delete")) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Country ID still in use", "Check to see where this country is still being used");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!",
                        "Something terrible has happened!");
                System.err.println(se.getLocalizedMessage());
            }
        }
        return message;
    }

    public UIComponent getAddbutton() {
        return addCountryId;
    }

    public void setAddbutton(UIComponent addbutton) {
        addCountryId = addbutton;
    }
}
