// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CountryDao.java

package fifa.dao;

import com.mysql.jdbc.StringUtils;
import fifa.jsf.CountryBean;
import fifa.utilities.*;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class CountryDao
    implements FIFAConstants
{

    public CountryDao()
    {
    }

    public List getCountriesEdit()
    {
        List countriesEdit;
        Connection conn;
        JDBCConnect jdbcConnect;
        countriesEdit = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            int restrictRows = getRestrictedRows();
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String sql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countrySelect");
            if(StringUtils.isEmptyOrWhitespaceOnly(sql))
                throw new IOException("SQL Select statement is null.");
            if(restrictRows > 1)
                sql = (new StringBuilder(String.valueOf(sql))).append(" LIMIT ").append(restrictRows).append(";").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(";").toString();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            CountryBean country;
            for(rs = preparedStatement.executeQuery(); rs.next(); countriesEdit.add(country))
            {
                country = new CountryBean();
                country.setCountryId(rs.getString("countryId"));
                country.setCountryName(rs.getString("countryName"));
                country.setFlagImage(rs.getString("flagImage"));
                country.setCountryComments(rs.getString("countryComments"));
            }

            rs.close();
        }
        break MISSING_BLOCK_LABEL_373;
        SQLException se;
        se;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_383;
        Exception e;
        e;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_383;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return countriesEdit;
    }

    public String getCountryName(String countryId)
    {
        String countryName;
        Connection conn;
        JDBCConnect jdbcConnect;
        countryName = null;
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryName2Select"));
            preparedStatement.setString(1, countryId);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next();)
                countryName = rs.getString("countryName");

            rs.close();
        }
        break MISSING_BLOCK_LABEL_224;
        SQLException se;
        se;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception e;
        e;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return countryName;
    }

    private int getRestrictedRows()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        int restrictRows = 0;
        if(session.getAttribute("countriesRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("countriesRestrictRows")).intValue();
        return restrictRows;
    }

    public List getCountryNames()
    {
        List countries;
        Connection conn;
        JDBCConnect jdbcConnect;
        countries = new ArrayList();
        conn = null;
        PreparedStatement preparedStatement = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn = jdbcConnect.getConnection();
        if(conn != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryNameSelect"));
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next(); countries.add(rs.getString("countryName")));
            rs.close();
        }
        break MISSING_BLOCK_LABEL_224;
        SQLException se;
        se;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(se.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception e;
        e;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(e.getLocalizedMessage());
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        break MISSING_BLOCK_LABEL_234;
        Exception exception;
        exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        throw exception;
        if(conn != null)
            jdbcConnect.closeConnection(conn);
        return countries;
    }

    public String getCountryId(String countryName)
    {
        String countryId;
        Connection conn1;
        JDBCConnect jdbcConnect;
        countryId = null;
        conn1 = null;
        jdbcConnect = null;
        jdbcConnect = new JDBCConnect();
        conn1 = jdbcConnect.getConnection();
        if(conn1 != null)
        {
            PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
            String preparedSql = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryIdSelect");
            if(StringUtils.isEmptyOrWhitespaceOnly(preparedSql))
                throw new IOException("SQL Select statement is null.");
            PreparedStatement preparedStatement = conn1.prepareStatement(preparedSql);
            preparedStatement.setString(1, countryName);
            ResultSet rs;
            for(rs = preparedStatement.executeQuery(); rs.next();)
                countryId = rs.getString("countryId");

            rs.close();
        } else
        {
            throw new IOException("Connection is null.");
        }
        break MISSING_BLOCK_LABEL_254;
        SQLException se;
        se;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(se.getLocalizedMessage());
        if(conn1 != null)
            jdbcConnect.closeConnection(conn1);
        break MISSING_BLOCK_LABEL_264;
        Exception e;
        e;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("addCountryId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!"));
        System.err.println(e.getLocalizedMessage());
        if(conn1 != null)
            jdbcConnect.closeConnection(conn1);
        break MISSING_BLOCK_LABEL_264;
        Exception exception;
        exception;
        if(conn1 != null)
            jdbcConnect.closeConnection(conn1);
        throw exception;
        if(conn1 != null)
            jdbcConnect.closeConnection(conn1);
        return countryId;
    }

    public FacesMessage addCountry(String countryId, String countryName, String countryComments, String flagImage)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryInsert"));
                preparedStatement.setString(1, countryId);
                preparedStatement.setString(2, countryName);
                if(StringUtils.isEmptyOrWhitespaceOnly(countryComments))
                    countryComments = null;
                preparedStatement.setString(3, countryComments);
                preparedStatement.setString(4, flagImage);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            if(se.getMessage().startsWith("Duplicate entry"))
            {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Country ID already exists", "Check to see if this country already exists");
            } else
            {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!");
                System.err.println(se.getLocalizedMessage());
            }
        }
        return message;
    }

    public FacesMessage updateCountry(String countryId, String countryName, String countryComments, String flagImage)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryUpdate"));
                preparedStatement.setString(1, countryName);
                if(StringUtils.isEmptyOrWhitespaceOnly(countryComments))
                    countryComments = null;
                preparedStatement.setString(2, countryComments);
                preparedStatement.setString(3, flagImage);
                preparedStatement.setString(4, countryId);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!");
            System.err.println(se.getLocalizedMessage());
        }
        return message;
    }

    public FacesMessage deleteCountry(String countryId)
    {
        Connection conn1 = null;
        PreparedStatement preparedStatement = null;
        JDBCConnect jdbcConnect = null;
        FacesMessage message = null;
        try
        {
            jdbcConnect = new JDBCConnect();
            conn1 = jdbcConnect.getConnection();
            if(conn1 != null)
            {
                PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
                preparedStatement = conn1.prepareStatement(propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "sql.countryDelete"));
                preparedStatement.setString(1, countryId);
                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException se)
        {
            if(se.getMessage().startsWith("Cannot delete"))
            {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Country ID still in use", "Check to see where this country is still being used");
            } else
            {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred! Check the logs!", "Something terrible has happened!");
                System.err.println(se.getLocalizedMessage());
            }
        }
        return message;
    }

    public UIComponent getAddbutton()
    {
        return addCountryId;
    }

    public void setAddbutton(UIComponent addbutton)
    {
        addCountryId = addbutton;
    }

    private UIComponent addCountryId;
    private final String SELECT_COUNTRY = "sql.countrySelect";
    private final String SELECT_COUNTRY_NAME = "sql.countryNameSelect";
    private final String SELECT_COUNTRY_ID = "sql.countryIdSelect";
    private final String SELECT_COUNTRY_INSERT = "sql.countryInsert";
    private final String SELECT_COUNTRY_UPDATE = "sql.countryUpdate";
    private final String SELECT_COUNTRY_DELETE = "sql.countryDelete";
}
