// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CountryBean.java

package fifa.jsf;

import fifa.dao.CountryDao;
import fifa.dao.CountrySingleton;
import java.io.Serializable;
import java.util.*;

public class CountryBean
    implements Serializable
{

    public CountryBean(String countryId, String countryName, String countryComments, String flagImage)
    {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryComments = countryComments;
        this.flagImage = flagImage;
    }

    public CountryBean()
    {
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getCountryComments()
    {
        return countryComments;
    }

    public void setCountryComments(String countryComments)
    {
        this.countryComments = countryComments;
    }

    public String getFlagImage()
    {
        return flagImage;
    }

    public void setFlagImage(String flagImage)
    {
        this.flagImage = flagImage;
    }

    public List completeCountry(String countryPrefix)
    {
        CountrySingleton countrySingleton = CountrySingleton.getInstance();
        List teams = countrySingleton.getcountries();
        List matches = new ArrayList();
        for(Iterator iterator = teams.iterator(); iterator.hasNext();)
        {
            String possibleCountry = (String)iterator.next();
            if(possibleCountry.toUpperCase().contains(countryPrefix.toUpperCase()))
                matches.add(possibleCountry);
        }

        return matches;
    }

    public List getCountries()
    {
        return countries;
    }

    public List getCountriesEdit()
    {
        CountryDao countryDao = new CountryDao();
        countriesEdit = countryDao.getCountriesEdit();
        return countriesEdit;
    }

    public void setCountries(List countries)
    {
        this.countries = countries;
    }

    public void setCountriesEdit(List countriesEdit)
    {
        this.countriesEdit = countriesEdit;
    }

    private static final long serialVersionUID = 1L;
    private String countryId;
    private String countryName;
    private String countryComments;
    private String flagImage;
    private List countries;
    private List countriesEdit;
}
