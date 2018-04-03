// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CountrySingleton.java

package fifa.dao;

import java.util.List;

// Referenced classes of package fifa.dao:
//            CountryDao

public class CountrySingleton
{

    protected CountrySingleton()
    {
    }

    public static CountrySingleton getInstance()
    {
        if(instance == null)
            synchronized(fifa/dao/CountrySingleton)
            {
                if(instance == null)
                    instance = new CountrySingleton();
                countries = getCountriesFromDao();
            }
        return instance;
    }

    public List getcountries()
    {
        return countries;
    }

    public static List getCountriesFromDao()
    {
        CountryDao countryDao = new CountryDao();
        countries = countryDao.getCountryNames();
        return countries;
    }

    private static volatile CountrySingleton instance = null;
    private static List countries;

}
