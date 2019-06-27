package fifa.dao;

import java.util.List;

public class CountrySingleton
{
    private static List<String> countries;
    private static CountrySingleton instance;

    public static CountrySingleton getInstance() {
        if (instance == null)
            synchronized (CountrySingleton.class) {
                if (instance == null)
                    instance = new CountrySingleton();
                countries = getCountriesFromDao();
            }
        return instance;
    }


    public List<String> getCountries() { return countries; }


    public static List<String> getCountriesFromDao() {
      CountryDao  countryDao = new CountryDao();
        countries = countryDao.getCountryNames();
        return countries;
    }
}
