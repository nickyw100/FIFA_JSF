package fifa.jsf;

import fifa.dao.CountryDao;
import fifa.dao.CountrySingleton;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@SessionScoped
public class CountryBean
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String countryId;
    private String countryName;
    private String countryComments;
    private String flagImage;
    private List<String> countries;
    private List<CountryBean> countriesEdit;

    public CountryBean(String countryId, String countryName, String countryComments, String flagImage) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryComments = countryComments;
        this.flagImage = flagImage;
    }


    public CountryBean() {
    }


    public String getCountryId() {
        return this.countryId;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }


    public String getCountryName() {
        return this.countryName;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public String getCountryComments() {
        return this.countryComments;
    }


    public void setCountryComments(String countryComments) {
        this.countryComments = countryComments;
    }


    public String getFlagImage() {
        return this.flagImage;
    }


    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
    }


    public List<String> completeCountry(String countryPrefix) {
        CountrySingleton countrySingleton = CountrySingleton.getInstance();
        List<String> teams = countrySingleton.getCountries();
        List<String> matches = new ArrayList<>();

        for (String possibleCountry : teams) {

            if (possibleCountry.toUpperCase().contains(countryPrefix.toUpperCase())) {
                matches.add(possibleCountry);
            }
        }
        return matches;
    }


    public List<String> getCountries() {
        return this.countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<CountryBean> getCountriesEdit() {
        CountryDao countryDao = new CountryDao();
        this.countriesEdit = countryDao.getCountriesEdit();
        return this.countriesEdit;
    }

    public void setCountriesEdit(List<CountryBean> countriesEdit) {
        this.countriesEdit = countriesEdit;
    }
}
