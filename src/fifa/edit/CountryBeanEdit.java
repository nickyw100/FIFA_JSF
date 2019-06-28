package fifa.edit;

import fifa.dao.CountryDao;
import fifa.jsf.CountryBean;
import fifa.utilities.FIFAConstants;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;


@ManagedBean
@ViewScoped
public class CountryBeanEdit
        implements Serializable, FIFAConstants {
    private static final long serialVersionUID = 1L;
    private List<CountryBean> countryList;
    private DataModel<CountryBean> model;
    private CountryBean countryBean = new CountryBean();
    private boolean edit;
    private Integer restrictRows;
    private List<CountryBean> filteredCountries;

    @PostConstruct
    public void init() {
        int restrictRows = 0;
        restrictRows = getCountryRestrictRows(restrictRows);
        setRestrictRows(Integer.valueOf(restrictRows));
        CountryDao countryDao = new CountryDao();
        this.countryList = countryDao.getCountriesEdit();
    }


    private int getCountryRestrictRows(int restrictRows) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("countriesRestrictRows") != null) {
            restrictRows = ((Integer) session.getAttribute("countriesRestrictRows")).intValue();
        }
        return restrictRows;
    }


    public String add() {
        CountryDao countryDao = new CountryDao();
        FacesMessage message = countryDao.addCountry(this.countryBean.getCountryId(), this.countryBean.getCountryName(), this.countryBean.getCountryComments(),
                this.countryBean.getFlagImage());

        if (message != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else {
            refreshPage();
        }
        return null;
    }


    public String editCountry() {
        this.countryBean = this.model.getRowData();
        setEdit(true);
        return null;
    }


    public void editCountry(CountryBean countryBean) {
        this.countryBean = countryBean;
        this.edit = true;
    }


    public String save() {
        CountryDao countryDao = new CountryDao();
        FacesMessage message = countryDao.updateCountry(this.countryBean.getCountryId(), this.countryBean.getCountryName(),
                this.countryBean.getCountryComments(), this.countryBean.getFlagImage());

        if (message != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else {
            this.countryBean = new CountryBean();
            setEdit(false);
            refreshPage();
        }
        return null;
    }


    public String cancel() {
        resetPlaceHolder();
        refreshPage();
        return null;
    }

    public String delete() {
        CountryDao countryDao = new CountryDao();
        this.countryBean = this.model.getRowData();
        FacesMessage message = countryDao.deleteCountry(this.countryBean.getCountryId());

        if (message != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addCountryId", message);
        } else {
            this.countryBean = new CountryBean();
            setEdit(false);
            refreshPage();
        }
        return null;
    }


    public List<CountryBean> getCountryList() {
        return this.countryList;
    }


    public DataModel<CountryBean> getModel() {
        if (this.model == null) {
            this.model = new ListDataModel(this.countryList);
        }

        return this.model;
    }


    public CountryBean getCountryBean() {
        return this.countryBean;
    }


    public boolean isEdit() {
        return this.edit;
    }


    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    public List<CountryBean> getFilteredCountries() {
        return this.filteredCountries;
    }


    public void setFilteredCountries(List<CountryBean> filteredCountries) {
        this.filteredCountries = filteredCountries;
    }

    private void resetPlaceHolder() {
        this.countryBean = new CountryBean();
        setEdit(false);
    }

    public Integer getRestrictRows() {
        return this.restrictRows;
    }


    public void setRestrictRows(Integer restrictRows) {
        this.restrictRows = restrictRows;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("countriesRestrictRows", restrictRows);
    }

    public String refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> messages = fc.getMessages();
        if (!messages.hasNext()) {

            String url = "editCountry_template.jsf";
            ExternalContext ec = fc.getExternalContext();
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }
        return null;
    }
}
