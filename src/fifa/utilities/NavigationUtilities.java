// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NavigationUtilities.java

package fifa.utilities;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Calendar;

// Referenced classes of package fifa.utilities:
//            FIFAConstants

public class NavigationUtilities
    implements FIFAConstants
{

    protected NavigationUtilities()
    {
    }

    public static NavigationUtilities getInstance()
    {
        if(instance == null)
            instance = new NavigationUtilities();
        return instance;
    }

    public void goHome()
    {
        String url = "index.jsf?faces-redirect=true";
        FacesContext fc = FacesContext.getCurrentInstance();
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

    public int getCurrentYear()
    {
        return Calendar.getInstance().get(1);
    }

    public void refreshPage(String url)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
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

    private static NavigationUtilities instance = null;

}
