// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CountryBeanEdit.java

package fifa.edit;

import fifa.dao.CountryDao;
import fifa.jsf.CountryBean;
import fifa.utilities.FIFAConstants;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class CountryBeanEdit
    implements Serializable, FIFAConstants
{

    public CountryBeanEdit()
    {
        countryBean = new CountryBean();
    }

    public void init()
    {
        int restrictRows = 0;
        restrictRows = getCountryRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        CountryDao countryDao = new CountryDao();
        countryList = countryDao.getCountriesEdit();
    }

    private int getCountryRestrictRows(int restrictRows)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        if(session.getAttribute("countriesRestrictRows") != null)
            restrictRows = ((Integer)session.getAttribute("countriesRestrictRows")).intValue();
        return restrictRows;
    }

    public String add()
    {
        CountryDao countryDao = new CountryDao();
        FacesMessage message = countryDao.addCountry(countryBean.getCountryId(), countryBean.getCountryName(), countryBean.getCountryComments(), countryBean.getFlagImage());
        if(message != null)
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else
        {
            refreshPage();
        }
        return null;
    }

    public String editCountry()
    {
        countryBean = (CountryBean)model.getRowData();
        setEdit(true);
        return null;
    }

    public void editCountry(CountryBean countryBean)
    {
        this.countryBean = countryBean;
        edit = true;
    }

    public String save()
    {
        CountryDao countryDao = new CountryDao();
        FacesMessage message = countryDao.updateCountry(countryBean.getCountryId(), countryBean.getCountryName(), countryBean.getCountryComments(), countryBean.getFlagImage());
        if(message != null)
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else
        {
            countryBean = new CountryBean();
            setEdit(false);
            refreshPage();
        }
        return null;
    }

    public String cancel()
    {
        resetPlaceHolder();
        refreshPage();
        return null;
    }

    public String delete()
    {
        CountryDao countryDao = new CountryDao();
        countryBean = (CountryBean)model.getRowData();
        FacesMessage message = countryDao.deleteCountry(countryBean.getCountryId());
        if(message != null)
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else
        {
            countryBean = new CountryBean();
            setEdit(false);
            refreshPage();
        }
        return null;
    }

    public List getCountryList()
    {
        return countryList;
    }

    public DataModel getModel()
    {
        if(model == null)
            model = new ListDataModel(countryList);
        return model;
    }

    public CountryBean getCountryBean()
    {
        return countryBean;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
    }

    public List getFilteredCountries()
    {
        return filteredCountries;
    }

    public void setFilteredCountries(List filteredCountries)
    {
        this.filteredCountries = filteredCountries;
    }

    private void resetPlaceHolder()
    {
        countryBean = new CountryBean();
        setEdit(false);
    }

    public Integer getRestrictRows()
    {
        return restrictRows;
    }

    public void setRestrictRows(Integer restrictRows)
    {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("countriesRestrictRows", restrictRows);
    }

    public String refreshPage()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator messages = fc.getMessages();
        if(!messages.hasNext())
        {
            String url = "editCountry_template.jsf";
            ExternalContext ec = fc.getExternalContext();
            try
            {
                ec.redirect(url);
            }
            catch(IOException ex)
            {
                System.err.println(ex.getLocalizedMessage());
            }
        }
        return null;
    }

    private static final long serialVersionUID = 1L;
    private List countryList;
    private transient DataModel model;
    private CountryBean countryBean;
    private boolean edit;
    private Integer restrictRows;
    private List filteredCountries;
}
