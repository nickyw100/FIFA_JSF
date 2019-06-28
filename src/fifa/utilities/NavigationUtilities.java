package fifa.utilities;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Calendar;

public class NavigationUtilities
        implements FIFAConstants {
    private static NavigationUtilities instance = null;


    public static NavigationUtilities getInstance() {
        if (instance == null) {
            instance = new NavigationUtilities();
        }
        return instance;
    }

    public void goHome() {
        String url = "index.jsf?faces-redirect=true";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }


    public int getCurrentYear() {
        return Calendar.getInstance().get(1);
    }


    public void refreshPage(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
